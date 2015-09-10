<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		Firstname: ${user.getInfo("firstname")} <br>
		Lastname: ${user.getInfo("lastname")} <br>
		Gender: ${user.getInfo("sex")} <br>
		Salutation: ${user.getInfo("salutation")} <br>
		Birthdate: ${user.getInfo("birthday")} <br>
		Username: ${user.getInfo("username")} <br>
		About Me: ${user.getInfo("description")} <br>
	</div>
	
	<c:if test="${user.isAdmin()==true}">
		<a href=admin_registration>admin registration</a>
	</c:if>
	
	<a href=edit>edit profile</a>

	
	<c:if test="${user!=null}">
		<form action="logout" method="GET">
		<button type=submit>logout</button>
		</form>
	</c:if>
</body>
</html>