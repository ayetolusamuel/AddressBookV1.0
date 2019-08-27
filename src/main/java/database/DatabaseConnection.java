package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;

    public Statement getStatement() {
        return statement;
    }
	public Connection getConnection() {
		return connection;
	}

	public void open() {

		String msAccDB = "database//address.accdb";
		String dbURL = "jdbc:ucanaccess://" + msAccDB;
		try {
			connection = DriverManager.getConnection(dbURL
					+ ";jackcessOpener=database.CryptCodecOpener", "", "addressBook$");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try {
				statement = connection.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
			
		
		
		
	}




}
