package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import database.LogDAO;
import database.UserDAO;
import utility.DateParser;
import utility.IPChecker;

/**
 * Servlet implementation class public_registration
 */
@WebServlet("/public_registration")
public class PublicRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String prompt = "prompt";
	private final String status = "status";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublicRegistration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("user");
		
		if(loginUser == null) {
			
			request.setAttribute(this.prompt, session.getAttribute(this.prompt));
			session.removeAttribute(this.prompt);
			
			request.setAttribute(this.status, session.getAttribute(this.status));
			session.removeAttribute(this.status);
		
			request.getRequestDispatcher("public_registration.jsp").forward(request, response);
		} else {
			response.sendRedirect("login_landing");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//ServletContext sc = request.getServletContext();
		
		HttpSession session = request.getSession();
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String sex = request.getParameter("sex");
		String salutation = request.getParameter("salutations");
		String birthday = DateParser.parseDateForDatabase(request.getParameter("birthday"));
		String description = request.getParameter("description");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String prompt = "";
		boolean invalid = false;
		
		//firstname check
		if(firstname.length()==0){
			prompt += "First name is empty. ";
			invalid = true;
		} else if (firstname.length() > 50) {
			prompt += "First name is over 50 characters. ";
			invalid = true;
		} else if(!(firstname.matches("^[A-Za-z\\s]{1,}[A-Za-z\\s]{0,}$"))) {
			prompt += "First name contains special character. ";
			invalid = true;
		} 
		
		//lastname check
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
		
		//gender check
		if(sex == null || sex.length() == 0){
			prompt += "Gender is empty. ";
			invalid = true;
		} else if(!(sex.equals("Male") || sex.equals("Female"))) {
			prompt += "Invalid gender. ";
			invalid = true;
		}
		
		//salutation check
		if(salutation == null || salutation.length() == 0){
			prompt += "Salutation is empty. ";
			invalid = true;
		}
		
		//gender-salutation check
		if(!(sex.equals("Male") && (salutation.equals("Mr") || salutation.equals("Sir") || salutation.equals("Senior") || salutation.equals("Count"))
				|| (sex.equals("Female") && (salutation.equals("Miss") || salutation.equals("Ms") || salutation.equals("Mrs") || salutation.equals("Madame")
						|| salutation.equals("Majesty") || salutation.equals("Seniora"))))) {
			prompt += "Salutation does not match gender. ";
			invalid = true;
		}
		
		//birthday check
		LocalDate birthdate = null;
		if(birthday != null) {
			birthdate = DateParser.parseStringToDate(birthday);
		}
		
		if(birthdate == null) {
			prompt += "No birthday chosen or in wrong format. ";
			invalid = true;
		} else if (birthdate.isAfter(LocalDate.now())) {
			prompt += "Birthday is invalid. ";
			invalid = true;
		} else if (Period.between(birthdate, LocalDate.now()).getYears() <= 18) {
			prompt += "Birthday is less than or equal to 18. ";
			invalid = true;
		} 
		
		//username check
		if(username.length()==0) {
			prompt += "Username is empty. ";
			invalid = true;
		} else if(username.length() > 50) {
			prompt += "Username is over 50 characters. ";
			invalid = true;
		} else if (!(username.matches("^[a-zA-Z0-9_]*$"))) {
			prompt += "Username contains special character. ";
			invalid = true;
		} 
		
		//password check
		if(password.length()==0) {
			prompt += "Password is empty. ";
			invalid = true;
		} else if(password.length() > 50) {
			prompt += "Password is over 50 characters. ";
			invalid = true;
		} else if (!(username.matches("^[^ ]*$"))) {
			prompt += "Password contains spaces. ";
			invalid = true;
		}
		
		//aboutme check
		if(description.length() == 0) {
			prompt += "About me is empty. ";
			invalid = true;
		} else if (description.length() > 100) {
			prompt += "About me is too long. ";
			invalid = true;
		}
		
		if(invalid) {
			session.setAttribute(this.prompt, prompt);
			session.setAttribute(this.status, false);
			//LogDAO.addLog(prompt + "-Public Registration", IPChecker.getClientIpAddress(request));
		}
		else {
			User user = new User();
			user.setInfo("firstname", firstname);
			user.setInfo("lastname", lastname);
			user.setInfo("sex", sex);
			user.setInfo("salutation", salutation);
			user.setInfo("birthday", birthdate.toString());
			user.setInfo("description", Jsoup.clean(description, Whitelist.relaxed()));
			user.setInfo("username", username);
			user.setInfo("password", password);
			user.setAdmin(false);
			
			if(UserDAO.register(user)) {
				session.setAttribute(this.status, true);
			} else {
				session.setAttribute(this.status, false);
			}
		}
		response.sendRedirect("public_registration");
	}

}
