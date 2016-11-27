package by.mksn.epam.bidbuy.pool;

import by.mksn.epam.bidbuy.manager.DatabaseManager;
import org.apache.log4j.Logger;

import java.util.Properties;

public class ConnectionPool {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    // non-lazy initialization because webapp cannot work without DB and lazy init is redundant
    private static ConnectionPool instance = new ConnectionPool();

    private final int poolSize;

    private ConnectionPool() {
        poolSize = Integer.parseInt(DatabaseManager.getProperty(DatabaseManager.POOL_SIZE));
        String url = DatabaseManager.getProperty(DatabaseManager.URL);
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

}
