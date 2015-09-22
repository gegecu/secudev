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

	public static boolean addPost(Post post) {
		String query = "INSERT INTO `secudev`.`post` (`post`, `username`) VALUES (?, ?)";
		Connection connection = null;
		PreparedStatement stmt = null;
		
		System.out.println(query);
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, post.getInfo("post"));
			stmt.setString(2, post.getInfo("username"));

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
						" `user` ON `post`.username = `user`.username" + " ORDER BY `post`.postdate DESC, `post`.postid DESC LIMIT " + offset + ", " + noOfRecords;
		
		System.out.println(query);
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			
			connection  = getConnection();
			stmt  = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Post post = new Post();
				post.setId(rs.getInt(1));
				post.setInfo("post", rs.getString(2));
				post.setInfo("username", rs.getString(3));
				post.setInfo("postdate", rs.getString(4));
				post.setInfo("editdate", rs.getString(5));
				post.setInfo("firstname", rs.getString(6));
				post.setInfo("userjoin", rs.getString(7));
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
	
	public static Post getPost(int id) {
		
		//List<Post> posts = new ArrayList<Post>();
		Post post = null;
		
		String query = " SELECT `post`.postid, `post`.post, `post`.username, `post`.postdate, `post`.editeddate, `user`.firstname, `user`.joindate FROM `post` INNER JOIN" +
						" `user` ON `post`.username = `user`.username WHERE `post`.postid = " + id;
		
		System.out.println(query);
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			
			connection  = getConnection();
			stmt  = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
				post = new Post();
				post.setId(rs.getInt(1));
				post.setInfo("post", rs.getString(2));
				post.setInfo("username", rs.getString(3));
				post.setInfo("postdate", rs.getString(4));
				post.setInfo("editdate", rs.getString(5));
				post.setInfo("firstname", rs.getString(6));
				post.setInfo("userjoin", rs.getString(7));
				
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
		
		return post;
		
	}
	
	public static boolean delete(int id) {
		String query = "DELETE FROM `post` WHERE `post`.postid = ?";
		
		boolean result = true;
		
		Connection connection = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			connection  = getConnection();
			connection.setAutoCommit(false);
			stmt  = connection.prepareStatement(query);
			stmt.setInt(1, id);

			stmt.executeUpdate();

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
				if(stmt2 != null)
					stmt2.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
		
	}
	
	public static boolean editPost(Post post) {
		
		boolean result = true;
		String query1 = "";
		
		query1 = "UPDATE `post` SET `post`.`post` = ?, `post`.editeddate = ? WHERE `post`.postid = ?";
		
		
		System.out.println(query1);
		
		
		Connection connection = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try {
			connection  = getConnection();
			connection.setAutoCommit(false);
			stmt  = connection.prepareStatement(query1);
			stmt.setString(1, post.getInfo("post"));
			stmt.setDate(2, new Date(Calendar.getInstance().getTime().getTime()));
			stmt.setInt(3, post.getId());

			stmt.executeUpdate();

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
				if(stmt2 != null)
					stmt2.close();
				if(connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
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
