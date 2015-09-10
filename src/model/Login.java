package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.IPChecker;
import database.LogDAO;
import database.UserDAO;


/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
	    
		if(session.getAttribute("user") != null) {
			//request.getSession().invalidate();
			LogDAO.addLog("Invading login page", IPChecker.getClientIpAddress(request));
			response.sendRedirect("login_landing");
		}
		else {
			request.setAttribute(this.status, session.getAttribute(this.status));
			session.removeAttribute(this.status);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();

		if(session.getAttribute("user") == null) {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			User user = UserDAO.login(username, password);
			
			if(user!=null){ // check if account correct
				user.setInfo("password", password);
				session.setAttribute("user", user);
				response.sendRedirect("login_landing");
			}else {
				session.setAttribute(this.status, false);
				LogDAO.addLog("Wrong credentials" + "-Login", IPChecker.getClientIpAddress(request));
				response.sendRedirect("login");
			}

		}
		else {
			response.sendRedirect("login_landing");
		}
	}

}
