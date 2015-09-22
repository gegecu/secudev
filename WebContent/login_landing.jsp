<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
	textarea { 
		width: 100%;
		height: 150px;
	}
</style>
<body>
	<div>
		Firstname: ${sessionScope.user.getInfo("firstname")} <br>
		Lastname: ${sessionScope.user.getInfo("lastname")} <br>
		Gender: ${sessionScope.user.getInfo("sex")} <br>
		Salutation: ${sessionScope.user.getInfo("salutation")} <br>
		Birthdate: ${sessionScope.user.getInfo("birthday")} <br>
		Username: ${sessionScope.user.getInfo("username")} <br>
		About Me: ${sessionScope.user.getInfo("description")} <br>
	</div>
	
	<c:if test="${sessionScope.user.isAdmin()==true}">
		<button onclick="location.href='admin_registration'">admin registration</button>
	</c:if>
	
	<c:if test="${sessionScope.user!=null}">
		<form action="logout" method="POST">
		<button type=submit>logout</button>
		</form>
	</c:if>
	
	<button onclick="location.href='edit_profile'">edit profile</button>

	<br>
	<br>
	
	<div>
		<h1>
			<c:choose>
				<c:when test="${status==true}">
					<script>
						alert("Post Successful");
					</script>
				</c:when>
				<c:when test="${status==false}">
					Post Failed. <br>
					${prompt}
				</c:when>
			</c:choose>	
		</h1>
	</div>
	
	
	<form action="submit_post" method="POST">
		Post: <br>
		<textarea id="post" name="post" type="text" required></textarea><br>
		<button type=submit>submit</button>
	</form>
	
	<br>
	<br>
	<%@ include file="message_board.jsp"%>
	
</body>
</html>