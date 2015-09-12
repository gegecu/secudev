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
		<form action="logout" method="GET">
		<button type=submit>logout</button>
		</form>
	</c:if>
	
	<button onclick="location.href='edit'">edit profile</button>

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
	
	<form action="login_landing" method="POST">
		Post: <br>
		<textarea id="post" name="post" type="text" rows="4" cols="50" required></textarea><br>
		<button type=submit>submit</button>
	</form>
	
	<br>
	<div>
		<table border="1" cellpadding="5" cellspacing="5">
		
			<tr>
				<td>User Info</td>
				<td>User Post</td>
			</tr>
			
			<c:forEach var="post" items="${posts}">
			
			
            <tr>
                <td>
                	<a href="user_profile?username=${post.getInfo("username")}">${post.getInfo("username")}</a><br>
                	<a href="user_profile?username=${post.getInfo("firstname")}">${post.getInfo("firstname")}</a><br>

				</td>
				
				<td>
					${post.getInfo("postdate")} <br>
					<div>
						${post.getInfo("post")} 
					</div>
					<br>
					<c:if test="${post.getInfo(\"editdate\") != null}">
						${post.getInfo("editdate")}
						<br>
					</c:if>
					
					<c:if test="${post.getInfo(\"username\")==sessionScope.user.getInfo(\"username\") || sessionScope.user.isAdmin()}">
						<a href="post_profile?id=${post.getId()}">edit</a><br>
						<a href="post_profile?id=${post.getId()}">delete</a><br>
					</c:if>
					 
				</td>
            </tr>
        	</c:forEach>
		</table>
		
		<%--For displaying Previous link except for the 1st page --%>
		<%-- 
	    <c:if test="${currentPage > 1}">
	        <td><a href="login_landing?page=${currentPage - 1}">Previous</a></td>
	    </c:if>
	 	--%>
	    <%--For displaying Page numbers. 
	    The when condition does not display a link for the current page--%>
	    <table border="1" cellpadding="5" cellspacing="5">
	        <tr>
	            <c:forEach begin="1" end="${noOfPages}" var="i">
	                <c:choose>
	                    <c:when test="${currentPage eq i}">
	                        <td>${i}</td>
	                    </c:when>
	                    <c:otherwise>
	                        <td><a href="login_landing?page=${i}">${i}</a></td>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	        </tr>
	    </table>
	     
	    <%--For displaying Next link --%>
	    <%-- 
	    <c:if test="${currentPage lt noOfPages}">
	        <td><a href="login_landing?page=${currentPage + 1}">Next</a></td>
	    </c:if>
	    --%>
	</div>
	
</body>
</html>