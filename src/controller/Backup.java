package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.PostDAO;
import model.User;

/**
 * Servlet implementation class Backup
 */
@WebServlet("/backup")
public class Backup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Backup() {
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
		User loginUser = (User) session.getAttribute("user");

		if(loginUser != null && loginUser.isAdmin()) {
			if(PostDAO.backupPost()) {
				session.setAttribute(this.status, true);
			} else {
				session.setAttribute(this.status, false);
			}
		} else {
			session.setAttribute(this.prompt, "Not allowed");
			session.setAttribute(this.status, false);
		}
		
		
		response.sendRedirect("login_landing");
	}

}
