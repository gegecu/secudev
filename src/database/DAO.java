package database;


import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO {

	protected static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = ConnectionFactory.getInstance().getConnection();
		return con;
	}
	
}
