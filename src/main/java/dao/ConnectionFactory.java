package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getStackTrace());
		}
		
		try {
			return DriverManager.getConnection("jdbc:postgresql://ec2-174-129-223-35.compute-1.amazonaws.com:5432/d9h766n9nkh7s2?user=oafiykexlhfwko&password=-bqJ0lRcSDImZRg0RABJtpVpQB");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw new RuntimeException(ex);
		}
	}
}