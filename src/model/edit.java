package model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utility.DateParser;
import utility.IPChecker;
import database.LogDAO;
import database.UserDAO;

/**
 * Servlet implementation class edit
 */
@WebServlet("/edit")
public class edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		if(user == null) {
			LogDAO.addLog("Invading Edit Page", IPChecker.getClientIpAddress(request));
			response.sendRedirect("login");
		} else {
			request.setAttribute("user", user);
			
			request.setAttribute(this.prompt, session.getAttribute(this.prompt));
			session.removeAttribute(this.prompt);
			
			request.setAttribute(this.status, session.getAttribute(this.status));
			session.removeAttribute(this.status);
			
			request.getRequestDispatcher("edit.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		//User user = (User)session.getAttribute("user");
		
		if(session.getAttribute("user") != null) {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String sex = request.getParameter("sex");
			String salutation = request.getParameter("salutations");
			String birthday = DateParser.parseDateForDatabase(request.getParameter("birthday"));
			String description = request.getParameter("description");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String access = request.getParameter("access");
			String prompt = "";
			boolean invalid = false;
			
			if(firstname.length()==0){
				prompt += "First name is empty. ";
				invalid = true;
			} else if(firstname.length() > 50) {
				prompt += "First name is over 50 characters. ";
				invalid = true;
			}
			else if(!(firstname.matches("^[A-Za-z\\s]{1,}[A-Za-z\\s]{0,}$"))) {
				prompt += "First name contains special character. ";
				invalid = true;
			} 
			
			
			if(lastname.length()==0){
				prompt += "Last name is empty. ";
				invalid = true;
			} else if(lastname.length() > 50) {
				prompt += "Last name is over 50 characters. ";
				invalid = true;
			} else if(!(lastname.matches("^[A-Za-z\\s]{1,}[A-Za-z\\s]{0,}$"))) {
				prompt += "Last name contains special character. ";
				invalid = true;
			} 
			
			if(sex == null || sex.length() == 0){
				prompt += "Gender is empty. ";
				invalid = true;
			} else if(!(sex.equals("Male") || sex.equals("Female"))) {
				prompt += "Invalid gender. ";
				invalid = true;
			}
			
			if(salutation == null || salutation.length() == 0){
				prompt += "Salutation is empty. ";
				invalid = true;
			}
			
			if(!(sex.equals("Male") && (salutation.equals("Mr") || salutation.equals("Sir") || salutation.equals("Senior") || salutation.equals("Count"))
					|| (sex.equals("Female") && (salutation.equals("Miss") || salutation.equals("Ms") || salutation.equals("Mrs") || salutation.equals("Madame")
							|| salutation.equals("Majesty") || salutation.equals("Seniora"))))) {
				prompt += "Salutation does not match gender. ";
				invalid = true;
			}
			
			LocalDate birthdate = null;
			if(birthday != null) {
				birthdate = DateParser.parseStringToDate(birthday);
			}
			
			if(birthdate == null) {
				prompt += "No birthday chosen. ";
				invalid = true;
			} else if (birthdate.isAfter(LocalDate.now())) {
				prompt += "Birthday is invalid. ";
				invalid = true;
			} else if (Period.between(birthdate, LocalDate.now()).getYears() <= 18) {
				prompt += "Birthday is less than or equal to 18. ";
				invalid = true;
			} 
			
			
			if(!username.equals(((User)session.getAttribute("user")).getInfo("username"))) {
				prompt += "Username cannot be edited. ";
				invalid = true;
			}
			
			
			if(password.length()==0) {
				prompt += "Password is empty. ";
				invalid = true;
			} else if(password.length() > 50) {
				prompt += "Password is over 50 characters. ";
				invalid = true;
			}
			
			if(((User)session.getAttribute("user")).isAdmin() && (access == null || access.length() == 0)) {
				prompt += "No access type. ";
				invalid = true;
			} else if(!((User)session.getAttribute("user")).isAdmin() && access != null) {
				prompt += "Not authorized to set access type. ";
				invalid = true;
			}
			else if(!(access.equals("Admin") || access.equals("User"))) {
				prompt += "Unknown access type. ";
				invalid = true;
			}
			
			
			if(invalid) {
				session.setAttribute(this.prompt, prompt);
				session.setAttribute(this.status, false);
				LogDAO.addLog(prompt + "-Edit", IPChecker.getClientIpAddress(request));
				response.sendRedirect("edit");
			} else {
				User user = new User();
				user.setInfo("firstname", firstname);
				user.setInfo("lastname", lastname);
				user.setInfo("sex", sex);
				user.setInfo("salutation", salutation);
				user.setInfo("birthday", birthdate.toString());
				user.setInfo("description", description);
				user.setInfo("username", username);
				user.setInfo("password", password);
				
				if(access.equals("User")) {
					user.setAdmin(false);
				} else if(access.equals("Admin")) {
					user.setAdmin(true);
				}
				
				if(UserDAO.edit(user)) {
					user.setInfo("birthday", DateParser.parseDateForDisplay(birthdate.toString()));
					session.setAttribute("user", user);
					session.setAttribute(this.status, true);
				} else {
					session.setAttribute(this.status, false);
				}
				
				response.sendRedirect("edit");
			}
		}
		else {
			LogDAO.addLog("Invading Edit Page", IPChecker.getClientIpAddress(request));
			response.sendRedirect("login");
		}
	}

}
