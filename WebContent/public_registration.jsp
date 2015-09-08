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
		<form action="public_registration" method="POST">
			
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
			
			Username: <input id="username" name="username" type="text" required><br>
			Password: <input id="password" name="password" type="password" required><br> 
			About me: <input id="description" name="description" type="text" required> <br>
			
			<button type=submit>Submit</button>
		</form>
	</div>
	
	 <a href="login">login</a> 
	 
</body>

<script>
	$(document).ready(function() {
		$( "#birthday" ).datepicker();
		
		salutationTrigger();

		$("#sex").change(function() {
			
			salutationTrigger();

		});
	});
	
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