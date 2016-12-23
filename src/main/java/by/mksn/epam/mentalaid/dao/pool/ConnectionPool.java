package by.mksn.epam.mentalaid.dao.pool;

import by.mksn.epam.mentalaid.dao.manager.DatabaseManager;
import by.mksn.epam.mentalaid.dao.pool.exception.FatalPoolException;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
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
     * Maximum amount of available Connections in pool
     */
    public final int poolSize;
    /**
     * Maximum time in milliseconds which provides to user to take free connection,
     * otherwise {@link PoolException} happens
     */
    public final int pollTimeout;
    private ArrayBlockingQueue<PooledConnection> availableConnections;
    private PooledConnection[] allConnections;
    private volatile boolean isPoolReleased = false;

    private ConnectionPool() {
        try {
            Class.forName(DatabaseManager.getProperty(DatabaseManager.DRIVER_NAME));

            poolSize = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POOL_SIZE));
            pollTimeout = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POLL_TIMEOUT));

            Properties properties = getDatabaseProperties();
            String url = DatabaseManager.getProperty(DatabaseManager.URL);
            availableConnections = new ArrayBlockingQueue<>(poolSize);
            allConnections = new PooledConnection[poolSize];
            for (int i = 0; i < poolSize; i++) {
                Connection conn = DriverManager.getConnection(url, properties);
                PooledConnection connection = new PooledConnection(conn);
                availableConnections.offer(connection);
                allConnections[i] = connection;
            }
            if (availableConnections.size() != poolSize) {
                logger.fatal("Cannot create connection pool: not enough availableConnections created (expected: " +
                        poolSize + ", actual: " + availableConnections.size() + ")");
                throw new FatalPoolException("Not enough availableConnections created (expected: " +
                        poolSize + ", actual: " + availableConnections.size() + ")");
            }
        } catch (SQLException e) {
            logger.fatal("Cannot create connection pool: SQL Exception\n", e);
            throw new FatalPoolException("Cannot create connection pool\n", e);
        } catch (ClassNotFoundException e) {
            logger.fatal("Cannot create connection pool: failed loading driver\n", e);
            throw new FatalPoolException("Cannot create connection pool\n", e);
        }
        logger.info("Connection pool created successfully");
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
     * Provides main access to database available Connections
     * @return {@link Connection} to work with database
     * @throws PoolException if there is no free availableConnections in pool
     * for {@link #pollTimeout} milliseconds or pool is released
     */
    public Connection getConnection() throws PoolException {
        if (!isPoolReleased) {
            try {
                PooledConnection connection = availableConnections.poll(pollTimeout, TimeUnit.MILLISECONDS);
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
     * Returns connection to pool to reusing it by another user,
     * also reset AutoCommit, Holdability and TransactionIsolation to defaults
     * @param connection which will be returned to pool
     * @throws PoolException if cannot return connection, if connection created outside the pool, or if null passed.
     */
    public void returnConnection(Connection connection) throws PoolException {
        if (connection == null) {
            logger.warn("Someone trying to return null connection.");
            throw new PoolException("Null connection passed.");
        }
        if (connection instanceof PooledConnection) {
            try {
                connection.setAutoCommit(true);
                connection.setHoldability(connection.getMetaData().getResultSetHoldability());
                connection.setTransactionIsolation(connection.getMetaData().getDefaultTransactionIsolation());
                availableConnections.put((PooledConnection) connection);
                logger.trace("Connection returned to pool");
            } catch (InterruptedException e) {
                logger.warn("Cannot return connection to pool\n", e);
                throw new PoolException("Cannot return connection to pool.", e);
            } catch (SQLException e) {
                logger.warn("Cannot reset to connection defaults\n", e);
                throw new PoolException("Cannot reset to connection defaults\n", e);
            }
        } else {
            logger.warn("Someone trying to put connection which was created outside the pool: " + connection);
            throw new PoolException("Cannot put connection which was created outside the pool.");
        }
    }

    /**
     * Indicates is connection pool is released and all availableConnections are closed
     *
     * @return true if pool is released
     */
    public boolean isPoolReleased() {
        return isPoolReleased;
    }

    /**
     * Provides count of available connections which can be picked from this pool now
     *
     * @return available connection count
     */
    public int getAvailableConnectionsCount() {
        return availableConnections.size();
    }

    /**
     * Releases connection pool and closes all available Connections
     */
    public void releasePool() {
        isPoolReleased = true;
        for (PooledConnection connection : allConnections) {
            try {
                connection.closeConnection();
            } catch (SQLException e) {
                logger.error("Cannot close connection.\n", e);
            }
        }
        logger.info("Connection pool successfully released");
    }

}
