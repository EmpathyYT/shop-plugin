package plugin.q;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLStuff {
    private String host = "jdbc:sqlite::resource:shop.db";
    private String port = "3306";
    private String database = "shop";
    private String username = "root";
    private String password = "";

    private Connection connection;
    
    public boolean isConnected() {
        return (connection != null);
    }
    
    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:sqlite:shop.db");

        }
    }
    public void disconnect() throws ClassNotFoundException, SQLException {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public Connection getConnection() {
        return  connection;
    }
}
