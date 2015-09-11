<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>
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

	<div>
		<form action="edit" method="POST">
			
			Firstname: <input id="firstname" name="firstname" type="text" required><br>
			Lastname: <input id="lastname" name="lastname" type="text" required><br>
			
			<div>
			Gender:
				<select id="sex" name="sex" required>
					<option value="Male">Male</option>
					<option value="Female">Female</option>
				</select>
			</div>
			
			<div>
			Salutation:
				<select id="salutations" name="salutations" required>
				</select>
			</div>
			
			<div>
			Birthdate:
				<input type="date" id="birthday" name="birthday" required> 
			</div>
			
			Username: <input id="username" name="username" type="text" readonly><br>
			Password: <input id="password" name="password" type="password" required><br> 
			About me: <input id="description" name="description" type="text" required> <br>
			
			<div>
				<c:if test="${sessionScope.user.isAdmin()==true}">
					Access Type:
						<select id="access" name="access" required>
							<option value="User">User</option>
							<option value="Admin">Admin</option>
						</select>
				</c:if>
			</div>
			
			<button type=submit>Submit</button>
		</form>
	</div>
	
	 <a href="login_landing">back</a> 
	 
</body>

<script>
	$(document).ready(function() {
		$( "#birthday" ).datepicker();
		
		salutationTrigger();

		$("#sex").change(function() {
			
			salutationTrigger();

		});
		
		init();
	});
	
	function init() {
		
		<%if((User)request.getAttribute("user") != null) {%>
			var firstname = '${sessionScope.user.getInfo("firstname")}';
			$("#firstname").val(firstname);
			
			var lastname = '${sessionScope.user.getInfo("lastname")}';
			$("#lastname").val(lastname);
			
			var sex = '${sessionScope.user.getInfo("sex")}';
			$("#sex").val(sex);
			salutationTrigger();
			
			var salutation = '${sessionScope.user.getInfo("salutation")}';
			$("#salutations").val(salutation);
			
			var birthday = '${sessionScope.user.getInfo("birthday")}';
			$("#birthday").val(birthday);
			
			var username = '${sessionScope.user.getInfo("username")}';
			$("#username").val(username);
			
			var password = '${sessionScope.user.getInfo("password")}';
			$("#password").val(password);
			
			var description = '${sessionScope.user.getInfo("description")}';
			$("#description").val(description);
			
			<%if(((User)request.getAttribute("user")).isAdmin()){%>
				$("#access").val('Admin');
			<%} else {%>
				$("#access").val('User');
			<%}%>
			
		<%}%>
		
	}
	
	function salutationTrigger() {
		
		var $selectDropdown = 
		      $("#salutations")
		        .empty()
		        .html(' ');
	    
	    // clear contents
	    
		
	    var sex = $("#sex").val();
	    
	    
	    var salutationMale = ["Mr", "Sir", "Senior", "Count"];
	    var salutationFemale = ["Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora"];
	    
	    if(sex == "Male") {
	    	for(var i = 0; i < salutationMale.length; i++) {
	    		$("#salutations").append($("<option></option>").attr("value",salutationMale[i]).text(salutationMale[i]));
	    	}
	    } else {
	    	for(var i = 0; i < salutationMale.length; i++) {
	    		$("#salutations").append($("<option></option>").attr("value",salutationFemale[i]).text(salutationFemale[i]));
	    	}
	    }
	    
	    $selectDropdown.trigger('contentChanged');
	}
	

	
</script>
</html>