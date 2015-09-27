<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<% if(request.getSession().getAttribute("user") != null) {
		response.sendRedirect("login_landing");
	}
	%>

	<div>
		<h1>
			<c:if test="${status==false}">
					Failed. <br>
			</c:if>
		</h1>
	</div>
	
	<div>
		<form action="login" method="POST">
			Username: <input id="username" name="username" type="text" required><br>
			Password: <input id="password" name="password" type="password" required><br> 
			<button type="submit">submit</button>
		</form>
	</div>
</body>
</html>