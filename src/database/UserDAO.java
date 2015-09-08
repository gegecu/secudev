package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import model.Constant;
import model.User;

public class UserDAO extends DAO{

	public boolean register(User user) {
		
		boolean result = true;
		String query1 = "";
		String query2 = "";

		query1 = "INSERT INTO " + Constant.UserTable.table + " ( " + Constant.UserTable.firstname + ", " +
				Constant.UserTable.lastname + ", " + Constant.UserTable.sex + ", " + Constant.UserTable.description + ", " + Constant.UserTable.salutation +
				", " + Constant.UserTable.birthday + ", " + Constant.UserTable.username + ", " + Constant.UserTable.password 
				+ ") values ( ?, ?, (SELECT " + Constant.SexTable.sexid + " FROM " + Constant.SexTable.table + " WHERE " + Constant.SexTable.name
				+ " = ? )" + ", ?," + "(SELECT " + Constant.SalutationTable.salutationid + " FROM " + Constant.SalutationTable.table + " WHERE " + Constant.SalutationTable.name
				+ " = ? )" + ", ?, ?, sha2(?,256) )";
		
		
		System.out.println(query1);
		
		if(user.isAdmin()) {
			query2 = " INSERT INTO " + Constant.AdminTable.table + " ( " + Constant.AdminTable.username + " ) values (?);";
		} else {
			
		}
		
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(query1);
			
			stmt.setString(1, user.getInfo("firstname"));
			stmt.setString(2, user.getInfo("lastname"));
			stmt.setString(3, user.getInfo("sex"));
			stmt.setString(4, user.getInfo("description"));
			stmt.setString(5, user.getInfo("salutation"));
			stmt.setString(6, user.getInfo("birthday"));
			stmt.setString(7, user.getInfo("username"));
			stmt.setString(8, user.getInfo("password"));

			stmt.executeUpdate();
			
			if(!query2.isEmpty()) {
			
			stmt = connection.prepareStatement(query2);
			
			stmt.setString(1, user.getInfo("username"));

			stmt.executeUpdate();
			}
			
			connection.commit();
			
		} catch (SQLException e) {
		   e.printStackTrace();
		   result = false;
		} catch (ClassNotFoundException e) {
		   e.printStackTrace();
		   result = false;
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
		
		return result;		
	}
	
	public User login(String username, String password) {
		
		User user = null;

		
		String query = "SELECT " + Constant.UserTable.firstname + ", " + Constant.UserTable.lastname + ", " + Constant.SexTable.name + 
					   ", " + Constant.UserTable.description + ", " + Constant.SalutationTable.name + ", DATE_FORMAT(" + Constant.UserTable.birthday + ",'%m/%d/%Y'), " + 
					   Constant.UserTable.username + ", SHA2(" + Constant.UserTable.password + ", 256), " + "CASE WHEN " + Constant.AdminTable.username + " IS NULL THEN FALSE " + 
					   " ELSE TRUE END AS admin FROM " + Constant.UserTable.table + " LEFT JOIN " + Constant.AdminTable.table + " ON " + Constant.UserTable.username + " = " + Constant.AdminTable.username +
					   " INNER JOIN " + Constant.SalutationTable.table + " ON " + Constant.UserTable.salutation + " = " + Constant.SalutationTable.salutationid + " INNER JOIN " +
					   Constant.SexTable.table + " ON " + Constant.UserTable.sex + " = " + Constant.SexTable.sexid + " WHERE " + Constant.UserTable.username + " = " + "?" + " AND " +
					   Constant.UserTable.password + " = " + "SHA2(" + "?" + ", 256)";
		
		System.out.println(query);
		try {
			
			connection = getConnection();
			stmt = connection.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				user = new User();
				user.setInfo("firstname", rs.getString(1));
				user.setInfo("lastname", rs.getString(2));
				user.setInfo("sex", rs.getString(3));
				user.setInfo("description", rs.getString(4));
				user.setInfo("salutation", rs.getString(5));
				user.setInfo("birthday", rs.getString(6));
				user.setInfo("username", rs.getString(7));
				user.setInfo("password", rs.getString(8));
				
				if(rs.getInt(9) == 1) {
					user.setAdmin(true);
				} else {
					user.setAdmin(false);
				}
			
				rs.close();
			}
			
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
		
		return user;
	}
	
	public List<User> getAllUser() {
		
		List<User> users = new ArrayList<User>();
		
		String query = "SELECT " + Constant.UserTable.firstname + ", " + Constant.UserTable.lastname + ", " + Constant.SexTable.name + 
				   ", " + Constant.UserTable.description + ", " + Constant.SalutationTable.name + ", DATE_FORMAT(" + Constant.UserTable.birthday + ",'%m/%d/%Y'), " + 
				   Constant.UserTable.username + ", SHA2(" + Constant.UserTable.password + ", 256), " + "CASE WHEN " + Constant.AdminTable.username + " IS NULL THEN FALSE " + 
				   " ELSE TRUE END AS admin FROM " + Constant.UserTable.table + " LEFT JOIN " + Constant.AdminTable.table + " ON " + Constant.UserTable.username + " = " + Constant.AdminTable.username +
				   " INNER JOIN " + Constant.SalutationTable.table + " ON " + Constant.UserTable.salutation + " = " + Constant.SalutationTable.salutationid + " INNER JOIN " +
				   Constant.SexTable.table + " ON " + Constant.UserTable.sex + " = " + Constant.SexTable.sexid;
		
		System.out.println(query);
		try {
			
			connection = getConnection();
			stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				User user = new User();
				user.setInfo("firstname", rs.getString(1));
				user.setInfo("lastname", rs.getString(2));
				user.setInfo("sex", rs.getString(3));
				user.setInfo("description", rs.getString(4));
				user.setInfo("salutation", rs.getString(5));
				user.setInfo("birthday", rs.getString(6));
				user.setInfo("username", rs.getString(7));
				user.setInfo("password", rs.getString(8));
				
				if(rs.getInt(9) == 1) {
					user.setAdmin(true);
				} else {
					user.setAdmin(false);
				}
				
				users.add(user);
			}
			
			rs.close();
			
			
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
		
		return users;
	}
}
