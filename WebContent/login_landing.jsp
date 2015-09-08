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
		<table>
			<c:forEach var="users" items="${users}" end="0">
     			<tr>
     				<c:forEach var="entry" items="${users.getMap()}">
						<th>${entry.getKey()}</th>
					</c:forEach>
     			</tr>
			</c:forEach>
			<c:forEach var="users" items="${users}">
				<tr>
					<c:forEach var="entry" items="${users.getMap()}">
						<td>${users.getInfo(entry.getKey())}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<c:if test="${user.isAdmin()==true}">
		<a href=admin_registration>Admin Registration</a>
	</c:if>
	
	<c:if test="${user!=null}">
		<form action="logout" method="GET">
		<button type=submit>logout</button>
		</form>
	</c:if>
</body>
</html>