package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class is solely for establishing connection to DB and returning it's object.
 * @author Aleh_Hutyrchyk
 *
 */
public class DBConnection {
	public static Connection getConnectionDatabase() {
		Connection connection = null;
		
		try {
			//load the driver class
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL JGBC Driver Registered!");
			
			//get hold of the DriverManager
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hplus", "root", "password");
		} catch (ClassNotFoundException e){
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}
		
		catch (SQLException e) {
			System.out.println("Connection Failed! Check console output");
			e.printStackTrace();
		}
		
		if(connection != null) {
			System.out.println("Connection made to DB!");
		}
		return connection;
	}

}
