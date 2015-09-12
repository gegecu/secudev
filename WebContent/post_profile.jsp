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
		<h1>
			<c:choose>
				<c:when test="${status==true}">
					<script>
						alert("Edit Successful");
					</script>
				</c:when>
				<c:when test="${status==false}">
					Edit Failed. <br>
					${prompt}
				</c:when>
			</c:choose>	
		</h1>
	</div>

	Post: <br>
	<div style="border: 1px solid black;">
		${post.getInfo("post")} <br>
	</div>
	
	<div>
		<form action="post_profile?id=${post.getId()}&type=delete" method="POST">
			<button type=submit>delete</button>
		</form>
	</div>
	
	<br>
	<div>
		<form action="post_profile?id=${post.getId()}&type=edit" method="POST">
			Post: <br>
			<textarea id="post" name="post" type="text" rows="4" cols="50" required></textarea><br>
			<button type=submit>submit</button>
		</form>
	</div>
	
	<a href=login_landing>back</a>
</body>
</html>