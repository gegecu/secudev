package model;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.UserDAO;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/user_profile")
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User user = (User) request.getSession().getAttribute("user");
		
		if(user != null) {
			String username = request.getParameter("username");
			
			
			System.out.println(username);
			
			if(username.equals(user.getInfo("username"))){
				
				response.sendRedirect("login_landing");
				
			}else {
				
				request.setAttribute("user", UserDAO.getUser(username));
				request.getRequestDispatcher("user_profile.jsp").forward(request, response);
					
			}
		}
		else {
			response.sendRedirect("login");
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
