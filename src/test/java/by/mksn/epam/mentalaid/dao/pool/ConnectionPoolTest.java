package by.mksn.epam.mentalaid.dao.pool;

import by.mksn.epam.mentalaid.dao.manager.DatabaseManager;
import by.mksn.epam.mentalaid.dao.pool.exception.PoolException;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConnectionPoolTest {

    private static final Logger logger = Logger.getLogger(ConnectionPoolTest.class);

    @AfterClass
    public static void tearDown() {
        ConnectionPool poolToTest = ConnectionPool.getInstance();
        poolToTest.releasePool();
    }

    @Test(expected = PoolException.class)
    public void returnExternalConnectionTest() throws PoolException {
        ConnectionPool poolToTest = ConnectionPool.getInstance();
        try {
            int expectedConnectionCount = poolToTest.getAvailableConnectionsCount();
            Connection externalConnection = DriverManager.getConnection(
                    DatabaseManager.getProperty(DatabaseManager.URL),
                    DatabaseManager.getProperty(DatabaseManager.USER),
                    DatabaseManager.getProperty(DatabaseManager.PASSWORD)
            );
            poolToTest.returnConnection(externalConnection);

            assertEquals(expectedConnectionCount, poolToTest.getAvailableConnectionsCount());
        } catch (SQLException e) {
            logger.error("Cannot establish external connection\n", e);
            fail("Cannot establish external connection");
        }
    }

    @Test
    public void closeConnectionOutsideFromPoolTest() {
        ConnectionPool poolToTest = ConnectionPool.getInstance();
        try {
            Connection connection = poolToTest.getConnection();
            connection.close();
        } catch (SQLException e) {
            logger.error("Cannot close connection\n", e);
            fail("Cannot close connection");
        } catch (PoolException e) {
            logger.error("Cannot get connection from pool\n", e);
            fail("Cannot get connection from pool");
        }
    }

    @Test
    public void getAndReturnConnectionTest() {
        ConnectionPool poolToTest = ConnectionPool.getInstance();
        Connection connection = null;
        try {
            connection = poolToTest.getConnection();
        } catch (PoolException e) {
            logger.error("Cannot get connection from pool\n", e);
            fail("Cannot get connection from pool");
        }

        try {
            poolToTest.returnConnection(connection);
        } catch (PoolException e) {
            logger.error("Cannot return connection to pool\n", e);
            fail("Cannot return connection from pool");
        }
    }

}