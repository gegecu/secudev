package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Post;
import model.User;
import database.PostDAO;

/**
 * Servlet implementation class EditPost
 */
@WebServlet("/edit_post")
public class EditPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("login_landing");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("user");
		
		String postData = request.getParameter("post");
		
		if(loginUser == null) {
			response.sendRedirect("login");
		} 
		else { 
		
			int id = 0;
			try {
				id = Integer.parseInt(request.getParameter("id"));
			}catch(NumberFormatException e) {
				response.sendRedirect("login_landing");
				return;
			}
			
			if(id == 0 || !(loginUser.isAdmin() || loginUser.getInfo("username").equals(PostDAO.getPost(id).getInfo("username")))) {
				response.sendRedirect("login_landing");
				return;
			}
		
			String prompt = "";
			boolean invalid = false;

			if(postData.length() == 0) {
				prompt += "Post is empty. ";
				invalid = true;
			} else if(postData.length() > 65000) {
				prompt = "Post is too long. ";
				invalid = true;
			}
				
			if(invalid) {
				session.setAttribute(this.prompt, prompt);
				session.setAttribute(this.status, false);
				//LogDAO.addLog(prompt + "-Public Registration", IPChecker.getClientIpAddress(request));
			} else {
				Post post = new Post();
				post.setId(id);
				post.setInfo("post", postData);
						
				if(PostDAO.editPost(post)) {
					session.setAttribute(this.status, true);
				} else {
					session.setAttribute(this.status, false);
				}	
			}
			
			response.sendRedirect("post_profile?id=" + id);
		}
	}

}
