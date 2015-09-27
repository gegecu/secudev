package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Post;
import model.User;
import utility.IPChecker;
import utility.QueryBuilder;
import database.LogDAO;
import database.PostDAO;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search_post")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("user");
		
		if(loginUser == null) {
			response.sendRedirect("login");
			//LogDAO.addLog("Invading login landing page", IPChecker.getClientIpAddress(request));
		}
		else {

			List<String> value = new ArrayList();
			List<String> filter = new ArrayList();
			List<String> condition = new ArrayList();
			List<String> dateOption = new ArrayList();
			
			try {
				value.add("%" + request.getParameter("post") + "%");
				value.addAll(Arrays.asList(request.getParameterValues("value")));
				filter.addAll(Arrays.asList(request.getParameterValues("filter")));
				condition.addAll(Arrays.asList(request.getParameterValues("condition")));
				dateOption.addAll(Arrays.asList(request.getParameterValues("date_option")));
			} catch(NullPointerException e) {
				//e.printStackTrace();
			}
			
			int j = 0;
			if(filter != null && condition != null) {
				for(int i = 0, k = 1; i < filter.size(); i++, k++) {
					System.out.println(filter.get(i));
					if(filter.get(i).equals("Post")) {
						value.set(k, "%" + value.get(k) + "%");
					}
					else if(filter.get(i).equals("Postdate")) {
						if(dateOption != null) {
							if(dateOption.get(j).equals("Between")) {
								k++;
							}
							else {
								//query += condition.get(i) + " `post`.`" + filter.get(i) + "` " + dateOption.get(j) + " ? ";
							}
						j++;
						}
					}
				}
			}
			
			String conditionQuery = QueryBuilder.buildQuery(filter, condition, dateOption);
			
			System.out.println(conditionQuery);
			
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
		                       recordsPerPage, value, conditionQuery);

		    int noOfRecords = PostDAO.getNoOfRecords(value, conditionQuery);
		    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		        
		    if(noOfPages<page)
		    	page = noOfPages;
		        
		    request.setAttribute("posts", posts);
		    request.setAttribute("noOfPages", noOfPages);
		    request.setAttribute("currentPage", page);
		    request.setAttribute("search_query", request.getQueryString());

			
			request.getRequestDispatcher("search_post.jsp").forward(request, response);
		}
	
	        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String[] value = request.getParameterValues("value");
//		String[] filter = request.getParameterValues("filter");
//		String[] condition = request.getParameterValues("condition");
//		
//		if(value != null && filter != null) {
//			String query = "";
//			boolean passed = false;
//			if(value.length == 1 &&  filter.length == 1  && condition == null) {
//				query = QueryBuilder.buildQuery(filter);
//				passed = true;
//			}
//			
//			else if((value.length == filter.length) && (value.length == (condition.length + 1))) {
//				
//				query = QueryBuilder.buildQuery(filter, condition);
//				passed = true;
//			}
//			
//			if(passed){
//				int page = 1;
//				int recordsPerPage = 10;
//				if(request.getParameter("page") != null){
//					try{
//						page = Integer.parseInt(request.getParameter("page"));
//					}catch(NumberFormatException e){
//							
//					}
//				}
//
//				if(page<=0)
//					page = 1;
//			        
//				List<Post> posts = PostDAO.getPosts((page-1)*recordsPerPage,
//			                       recordsPerPage, value, query);
//
//			    int noOfRecords = PostDAO.getNoOfRecords(value, query);
//			    int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
//			        
//			    if(noOfPages<page)
//			    	page = noOfPages;
//			        
//			    request.setAttribute("posts", posts);
//			    request.setAttribute("noOfPages", noOfPages);
//			    request.setAttribute("currentPage", page);
//			    request.setAttribute("search_query", request.getQueryString());
//			}
//		}

	}

}
