package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class LogDAO extends DAO{
	
	public static void addLog(String description, String ipAddress) {
		String query = "INSERT INTO `secudev`.`log` (`loginfo`, `ipaddress`) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		
		System.out.println(query);
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, description);
			stmt.setString(2, ipAddress);

			stmt.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
		   e.printStackTrace();
		} catch (ClassNotFoundException e) {
		   e.printStackTrace();
		}finally {
			try {
				if(stmt != null)
					stmt.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

}
