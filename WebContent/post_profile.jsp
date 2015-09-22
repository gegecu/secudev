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
	div.scrollable {
	    width: 100%;
	    height: 150px;
	    margin: 0;
	    padding: 0;
	    overflow: auto;
	    border: 1px solid black;
	}
	
	textarea { 
		width: 100%;
		height: 150px;
	}
</style>

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
	<div class="scrollable">
		${post.getInfo("post")} <br>
	</div>
	
	<div>
		<form action="delete_post?id=${post.getId()}" method="POST">
			<button type=submit>delete</button>
		</form>
	</div>
	
	<br>
	<div>
		<form action="edit_post?id=${post.getId()}" method="POST">
			Post: <br>
			<textarea id="post" name="post" type="text" rows="4" cols="50" required></textarea><br>
			<button type=submit>submit</button>
		</form>
	</div>
	
	<a href=login_landing>back</a>
</body>
</html>