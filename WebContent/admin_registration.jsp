<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
</head>
<body>
	<div>
		<h1>
			<c:choose>
				<c:when test="${status==true}">
					<script>
						alert("Registration Successful");
					</script>
				</c:when>
				<c:when test="${status==false}">
					Registation Failed. <br>
					${prompt}
				</c:when>
			</c:choose>	
		</h1>
	</div>

	<div>
		<form action="admin_registration" method="POST">
		
			<%@ include file="form.jsp"%>
			
			<div>
			Access Type:
				<select id="access" name="access" required>
					<option value="User">User</option>
					<option value="Admin">Admin</option>
				</select>
			</div>
			
			<button type=submit>submit</button>
		</form>
	</div>
	
	<c:if test="${sessionScope.user!=null}">
		<form action="logout" method="POST">
		<button type=submit>logout</button>
		</form>
	</c:if>
	
	<a href=login_landing>back</a>
	
</body>
</html>