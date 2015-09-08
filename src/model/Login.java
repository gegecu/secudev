package model;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.LogDAO;
import database.UserDAO;


/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String status = "status";
       
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
		LogDAO logDAO = new LogDAO();
		String remoteAddress = request.getRemoteAddr();
	    String forwardedFor = request.getHeader("X-Forwarded-For");
	    String realIP = request.getHeader("X-Real-IP"); // This is the header which should be used to check the IP address range.

	    if( realIP == null )
	    	realIP = forwardedFor;
	    if( realIP == null )
	        realIP = remoteAddress;
	    
		if(session.getAttribute("user") != null) {
			//request.getSession().invalidate();
			logDAO.addLog("Invading login page", realIP);
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
		String remoteAddress = request.getRemoteAddr();
	    String forwardedFor = request.getHeader("X-Forwarded-For");
	    String realIP = request.getHeader("X-Real-IP"); // This is the header which should be used to check the IP address range.

	    if( realIP == null )
	    	realIP = forwardedFor;
	    if( realIP == null )
	        realIP = remoteAddress;

		
		if(session.getAttribute("user") == null) {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			UserDAO userDAO = new UserDAO();
			User user = userDAO.login(username, password);
			
			if(user!=null){ // check if account correct
				session.setAttribute("user", user);
				response.sendRedirect("login_landing");
			}else {
				session.setAttribute(this.status, false);
				LogDAO logDAO = new LogDAO();
				logDAO.addLog("Wrong credentials" + "-Login", realIP);
				response.sendRedirect("login");
			}

		}
		else {
			response.sendRedirect("login_landing");
		}
	}

}
