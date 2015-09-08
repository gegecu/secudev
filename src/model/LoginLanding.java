package model;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.LogDAO;
import database.UserDAO;

/**
 * Servlet implementation class LoginLanding
 */
@WebServlet("/login_landing")
public class LoginLanding extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		LogDAO logDAO = new LogDAO();
		String remoteAddress = request.getRemoteAddr();
	    String forwardedFor = request.getHeader("X-Forwarded-For");
	    String realIP = request.getHeader("X-Real-IP"); // This is the header which should be used to check the IP address range.

	    if( realIP == null )
	    	realIP = forwardedFor;
	    if( realIP == null )
	        realIP = remoteAddress;
		
		if(session.getAttribute("user") == null) {
			response.sendRedirect("login");
			logDAO.addLog("Invading login landing page", realIP);
		}
		else {
			UserDAO userDAO = new UserDAO();
			request.setAttribute("users", userDAO.getAllUser());
			request.getRequestDispatcher("login_landing.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
