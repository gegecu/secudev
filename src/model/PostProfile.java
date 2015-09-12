package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect("login");
			return;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}catch(NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect("login_landing");
		}
		
		if(id == 0 || !(user.isAdmin() || user.getInfo("username").equals(PostDAO.getPost(id).getInfo("username")))) {
			response.sendRedirect("login_landing");
			return;
		}
		
		else {
			if(request.getParameter("type").equals("edit"))
				editPost(request, response, session, id);
			else if(request.getParameter("type").equals("delete")) {
				PostDAO.delete(id);
				response.sendRedirect("login_landing");
			}
		}
	}
	
	private void editPost(HttpServletRequest request, HttpServletResponse response, HttpSession session, int id) throws ServletException,  IOException {
		
		String prompt = "";
		boolean invalid = false;

		String postData = request.getParameter("post");
		
		if(postData.length() == 0) {
			prompt += "Post is empty. ";
			invalid = true;
		}
				
		if(invalid) {
			session.setAttribute(this.prompt, prompt);
			session.setAttribute(this.status, false);
			//LogDAO.addLog(prompt + "-Public Registration", IPChecker.getClientIpAddress(request));
			response.sendRedirect("post_profile?id=" + id);
		} else {
			Post post = new Post();
			post.setId(id);
			post.setInfo("post", postData);
					
			if(PostDAO.editPost(post)) {
				session.setAttribute(this.status, true);
			} else {
				session.setAttribute(this.status, false);
			}
					
			response.sendRedirect("post_profile?id=" + id);
			}
	}

}
