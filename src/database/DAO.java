package database;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public abstract class DAO {

	protected Connection connection;
	protected PreparedStatement stmt;
	
	protected static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}
	
}
