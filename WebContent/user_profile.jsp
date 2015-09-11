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
	<div>
		Firstname: ${user.getInfo("firstname")} <br>
		Lastname: ${user.getInfo("lastname")} <br>
		Gender: ${user.getInfo("sex")} <br>
		Salutation: ${user.getInfo("salutation")} <br>
		Birthdate: ${user.getInfo("birthday")} <br>
		Username: ${user.getInfo("username")} <br>
		About Me: ${user.getInfo("description")} <br>
	</div>
	
	<a href=login_landing>back</a>
</body>
</html>