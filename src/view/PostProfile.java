package view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Post;
import model.User;
import utility.IPChecker;
import database.DAO;
import database.LogDAO;
import database.PostDAO;

/**
 * Servlet implementation class PostProfile
 */
@WebServlet("/post_profile")
public class PostProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Post post = null;
		User user = (User) session.getAttribute("user");
		if(session.getAttribute("user") != null) {
			try {
				post = PostDAO.getPost(Integer.parseInt(request.getParameter("id")));
			} catch(NumberFormatException e) {
				e.printStackTrace();
				response.sendRedirect("login_landing");
			}
			
			if(post != null) {
				if(post.getInfo("username").equals(user.getInfo("username")) || (user.isAdmin())) {
					
					if(request.getAttribute("type") != null && request.getAttribute("type").equals("delete")) {
						PostDAO.delete(post.getId());
					}
					else {
					request.setAttribute("post", post);
					
					request.setAttribute(this.prompt, session.getAttribute(this.prompt));
					session.removeAttribute(this.prompt);
					request.setAttribute(this.status, session.getAttribute(this.status));
					session.removeAttribute(this.status);
					
					request.getRequestDispatcher("post_profile.jsp").forward(request, response);
					}
				}
				else {
					response.sendRedirect("login_landing");
				}
			} else {
				response.sendRedirect("login_landing");
			}
		} else {
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
