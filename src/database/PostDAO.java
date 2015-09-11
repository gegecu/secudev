package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Constant;
import model.Post;
import model.User;

public class PostDAO extends DAO{

	public static boolean addPost(String post, User user) {
		String query = "INSERT INTO `secudev`.`post` (`post`, `username`, `postdate`) VALUES (?, ?, ?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		
		System.out.println(query);
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, post);
			stmt.setString(2, user.getInfo("username"));
			stmt.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));

			stmt.executeUpdate();
			
			connection.commit();
			
		} catch (SQLException e) {
		   e.printStackTrace();
		   return false;
		} catch (ClassNotFoundException e) {
		   e.printStackTrace();
		   return false;
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
		return true;
	}
	
	public static List<Post> getPosts(int offset, int noOfRecords) {
		List<Post> posts = new ArrayList<Post>();
		
		String query = " SELECT SQL_CALC_FOUND_ROWS `post`.postid, `post`.post, `post`.username, `post`.postdate, `post`.editeddate, `user`.firstname, `user`.joindate FROM `post` INNER JOIN" +
						" `user` ON `post`.username = `user`.username LIMIT " + offset + ", " + noOfRecords;
		
		System.out.println(query);
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			
			connection  = getConnection();
			stmt  = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Post post = new Post();
				post.setPostid(rs.getInt(1));
				post.setPost(rs.getString(2));
				post.setUsername(rs.getString(3));
				post.setDate(rs.getDate(4));
				post.setEditDate(rs.getDate(5));
				post.setFirstname(rs.getString(6));
				post.setUserJoin(rs.getDate(7));
				
				posts.add(post);
			}
			
			rs.close();
			
			rs = stmt.executeQuery("SELECT FOUND_ROWS()");
            if(rs.next())
                noOfRecords = rs.getInt(1);
			
			
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
		
		return posts;
	}
	
	public static int getNoOfRecords() {
		//List<Post> posts = new ArrayList<Post>();
		
		String query = " SELECT COUNT(*) FROM `post`";
		
		System.out.println(query);
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			
			connection  = getConnection();
			stmt  = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
            if(rs.next())
                return rs.getInt(1);
			
			
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
		
		return 0;
    }
}
