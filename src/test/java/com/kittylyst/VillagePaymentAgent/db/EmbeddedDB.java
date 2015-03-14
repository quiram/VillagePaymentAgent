package com.kittylyst.VillagePaymentAgent.db;

import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmbeddedDB {

    private final EmbeddedDBConfig config;
    private final DataSource ds;
    public final static String DEFAULT_CONNECTION_NAME = "DEFAULT";
    private final Map<String, ConnectionService> connections = new HashMap<String, ConnectionService>();

    public EmbeddedDB(EmbeddedDBConfig config) {
        this.config = config;
        String url = EmbeddedDBUtils.getConnectionSettings(config);
        ds = JdbcConnectionPool.create(url, config.getUserName(), config.getPassword());
    }

    public String getName() {
        return config.getdBName();
    }

    public String getSchemaName() {
        return config.getSchemaName();
    }

    public void createConnection(String name) throws SQLException {
        ConnectionService result = connections.get(name);
        if(result == null) {
            result = new ConnectionService(ds.getConnection());
            connections.put(name, result);
        }
    }

    public void destroyConnection(String name) {
        ConnectionService connectionService = connections.get(name);
        if(connectionService != null) {
            connectionService.close();
        }
    }

    public ResultSet executeQuery(String queryStr) throws SQLException {
        return executeQuery(DEFAULT_CONNECTION_NAME, queryStr);
    }

    public ResultSet executeQuery(String connectionName, String queryStr) throws SQLException {
        ConnectionService connection = connections.get(connectionName);
        return connection.executeQuery(queryStr);
    }

    private Connection createDbConnection() throws SQLException {
        return ds.getConnection();
    }

    public void close() {
        for(Map.Entry<String, ConnectionService> c : connections.entrySet()) {
            c.getValue().close();
            connections.remove(c.getKey());
        }
    }

    public static void main(String[] a) throws Exception {

        EmbeddedDBConfig dbConfig = new EmbeddedDBConfig.Builder().
                                    setDBName("test").
                                    setUserName("kj905").
                                    setPassword("kj905").
                                    setSchemaName("KJ905").
                                    setRelPathCreateScript("sql/create.sql").
                                    setRelPathPopulateScript("sql/populate.sql").
                                    setClearDataOnCloseOfLastConnection(false).
                build();

        EmbeddedDB db = new EmbeddedDB(dbConfig);
        db.createConnection(EmbeddedDB.DEFAULT_CONNECTION_NAME);
        String exampleQuery = "SELECT * FROM " + dbConfig.getSchemaName() +".HT_DEVICE";
        ResultSet result = db.executeQuery(EmbeddedDB.DEFAULT_CONNECTION_NAME, exampleQuery);

        while( result.next() )
        {
            String name = result.getString("imei");
            System.out.println( name );
        }
        db.close();
    }
}

class ConnectionService {

    private final Connection connection;
    private final Set<PreparedStatement> statements = new HashSet<PreparedStatement>();

    public ConnectionService(Connection connection) {
        this.connection = connection;
    }

    public ResultSet executeQuery(String queryStr) throws SQLException {
        PreparedStatement ps = getStatement(queryStr);
        statements.add(ps);
        ResultSet result = ps.executeQuery();
        return result;
    }

    public void close() {
        boolean hasClosedStatements = false;
        boolean hasClosedConnection = false;
        try {
            closeStatements();
            hasClosedStatements = true;
        } catch (SQLException e) {
            try {
                closeConnection();
                hasClosedConnection = true;
            } catch (SQLException e1) {
                //TODO log what failed to close
            }
        }
    }

    private  void closeStatements() throws SQLException {
        for(PreparedStatement s : statements) {
            s.close();
        }
    }

    private void closeConnection() throws SQLException {
        if(connection != null) {
            connection.close();
        }
    }

    private PreparedStatement getStatement(String sqlStr) throws SQLException {
        return connection.prepareStatement(sqlStr);
    }
}
