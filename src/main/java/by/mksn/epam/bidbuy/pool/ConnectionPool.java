package by.mksn.epam.bidbuy.pool;

import by.mksn.epam.bidbuy.manager.DatabaseManager;
import by.mksn.epam.bidbuy.pool.exception.FatalPoolException;
import by.mksn.epam.bidbuy.pool.exception.PoolException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Database connection pool of web application
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    // non-lazy initialization because web app cannot work without DB and lazy init is redundant
    private static final ConnectionPool instance = new ConnectionPool();
    /**
     * Maximum amount of connections in pool
     */
    public final int poolSize;
    /**
     * Maximum time in milliseconds which provides to user to take free connection,
     * otherwise {@link PoolException} happens
     */
    public final int pollTimeout;
    private ArrayBlockingQueue<WrapperConnection> connections;
    private volatile boolean isPoolReleased = false;

    private ConnectionPool() {
        try {
            poolSize = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POOL_SIZE));
            pollTimeout = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POLL_TIMEOUT));

            Properties properties = getDatabaseProperties();
            String url = DatabaseManager.getProperty(DatabaseManager.URL);
            connections = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection conn = DriverManager.getConnection(url, properties);
                WrapperConnection connection = new WrapperConnection(conn);
                connections.offer(connection);
            }
            if (connections.size() != poolSize) {
                logger.fatal("Cannot create connection pool: not enough connections created (expected: " +
                        poolSize + ", actual: " + connections.size() + ")");
                throw new FatalPoolException("Not enough connections created (expected: " +
                        poolSize + ", actual: " + connections.size() + ")");
            }
        } catch (SQLException e) {
            logger.fatal("Cannot create connection pool: SQL Exception\n", e);
            throw new FatalPoolException("Cannot create connection pool\n", e);
        }
        logger.debug("Connection pool created successfully");
    }

    /**
     * Provides instance of connection pool
     *
     * @return instance of connection pool
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    private Properties getDatabaseProperties() {
        Properties properties = new Properties();
        properties.put(DatabaseManager.USER, DatabaseManager.getProperty(DatabaseManager.USER));
        properties.put(DatabaseManager.PASSWORD, DatabaseManager.getProperty(DatabaseManager.PASSWORD));
        properties.put(DatabaseManager.AUTORECONNECT, DatabaseManager.getProperty(DatabaseManager.AUTORECONNECT));
        properties.put(DatabaseManager.ENCODING, DatabaseManager.getProperty(DatabaseManager.ENCODING));
        properties.put(DatabaseManager.USE_UNICODE, DatabaseManager.getProperty(DatabaseManager.USE_UNICODE));
        return properties;
    }

    /**
     * Provides main access to database connections
     * @return {@link WrapperConnection} to work with database
     * @throws PoolException if there is no free connections in pool
     * for {@link #pollTimeout} milliseconds or pool is released
     */
    public WrapperConnection pollConnection() throws PoolException {
        if (!isPoolReleased) {
            try {
                WrapperConnection connection = connections.poll(pollTimeout, TimeUnit.MILLISECONDS);
                logger.trace("Connection taken successfully");
                return connection;
            } catch (InterruptedException e) {
                logger.warn("Free connection timeout is over.");
                throw new PoolException("Connection timeout is over.");
            }
        } else {
            logger.warn("Trying to poll from realized pool.");
            throw new PoolException("Pool is already realized.");
        }
    }

    /**
     * Returns connection to pool to reusing it by another user
     * @param connection which will be returned to pool
     * @throws PoolException if cannot return connection to pool
     */
    public void putConnection(WrapperConnection connection) throws PoolException {
        try {
            connections.put(connection);
            logger.trace("Connection returned to pool");
        } catch (InterruptedException e) {
            logger.warn("Cannot return connection to pool\n", e);
            throw new PoolException("Cannot return connection to pool.", e);
        }
    }

    /**
     * Indicates is connection pool is released and all connections are closed
     *
     * @return true if pool is released
     */
    public boolean isPoolReleased() {
        return isPoolReleased;
    }

    /**
     * Releases connection pool and closes all connections
     */
    public void releasePool() {
        isPoolReleased = true;
        try {
            for (WrapperConnection connection : connections) {
                connection.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("Cannot close connection.\n", e);
        }
    }

}
