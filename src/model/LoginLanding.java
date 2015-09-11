package model;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import utility.IPChecker;
import database.LogDAO;
import database.PostDAO;

/**
 * Servlet implementation class LoginLanding
 */
@WebServlet("/login_landing")
public class LoginLanding extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginLanding() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") == null) {
			response.sendRedirect("login");
			LogDAO.addLog("Invading login landing page", IPChecker.getClientIpAddress(request));
		}
		else {
		
			int page = 1;
			int recordsPerPage = 10;
			if(request.getParameter("page") != null){
				try{
					page = Integer.parseInt(request.getParameter("page"));
				}catch(NumberFormatException e){
					
				}
			}

			if(page<=0)
				page = 1;
	        
	        
	        List<Post> posts = PostDAO.getPosts((page-1)*recordsPerPage,
	                                 recordsPerPage);
	        int noOfRecords = PostDAO.getNoOfRecords();
	        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	        
	        if(noOfPages<page)
				page = noOfPages;
	        
	        request.setAttribute("posts", posts);
	        request.setAttribute("noOfPages", noOfPages);
	        request.setAttribute("currentPage", page);
			
			request.setAttribute(this.status, session.getAttribute(this.status));
			session.removeAttribute(this.status);
			
	        request.getRequestDispatcher("login_landing.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null) {
			String post = Jsoup.clean(request.getParameter("post"), Whitelist.basicWithImages());
			
			if(PostDAO.addPost(post, (User)session.getAttribute("user"))) {
				session.setAttribute(this.status, true);
			} else {
				session.setAttribute(this.status, false);
			}
			
			response.sendRedirect("login_landing");
			
		} 
		else {
			LogDAO.addLog("Invading login landing page", IPChecker.getClientIpAddress(request));
			response.sendRedirect("login");
		}
		
	}

}
