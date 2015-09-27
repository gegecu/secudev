package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Post;
import model.User;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import utility.IPChecker;
import database.LogDAO;
import database.PostDAO;

/**
 * Servlet implementation class SubmitPost
 */
@WebServlet("/submit_post")
public class SubmitPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("user");

		String prompt = "";
		boolean invalid = false;
		
		
		if(loginUser == null) {
			//LogDAO.addLog("Invading login landing page", IPChecker.getClientIpAddress(request));
			response.sendRedirect("login");
		}
		
		else {
			String post = Jsoup.clean(request.getParameter("post"), Whitelist.relaxed());
			//String post = processor.process(request.getParameter("post"));
			
			if(post.length() == 0) {
				prompt = "Post is empty. ";
				invalid = true;
			} else if (post.length() > 65000) {
				prompt = "Post is too long. ";
				invalid = true;
			}
			
			if(invalid) {
				session.setAttribute(this.prompt, prompt);
				session.setAttribute(this.status, false);
				//LogDAO.addLog(prompt + "-Post", IPChecker.getClientIpAddress(request));
			} 
			else {
				Post p = new Post();
				p.setInfo("post", post);
				p.setInfo("username", loginUser.getInfo("username"));
				if(PostDAO.addPost(p)) {
					session.setAttribute(this.status, true);
				} 
				else {
					session.setAttribute(this.status, false);
				}
			}		
			response.sendRedirect("login_landing");
		} 

	}

}
