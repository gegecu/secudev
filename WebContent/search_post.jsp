<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<title>Insert title here</title>
</head>
<body>

	<a href="login_landing">back</a> 
	<form action="search_post" method="GET">
		Post: <input type="text" id="post" name="post" required></input><br>
		
		<div id="search_div">
			
		</div>
		
		<button type="submit">submit</button><br>
		
	</form>
	
	<button type="button" id="user_filter">add user filter</button>
	<button type="button" id="post_filter">add post filter</button>
	<button type="button" id="date_filter">add date filter</button>
	
	<br>
	<br>
	
	<%@ include file="message_board.jsp"%>
	
	<table border="1" cellpadding="5" cellspacing="5">
		<tr>
			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td>${i}</td>
					</c:when>
	                <c:otherwise>
						<td><a href="search_post?page=${i}&${search_query}">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</tr>
	</table>
	

</body>

<script>
	$(document).ready(function() {
		trigger();
	});

	function trigger() {
		var userid = 0;
		var postid = 0;
		var dateid = 0;
		$("#user_filter").on("click", function(){
			
			var html = 	"<div id=\"user_div" + userid + "\">" +
							"<input type=\"text\" id=\"user_val" + userid + "\" name=\"value\" required></input>" +
							"<input type=\"text\" id=\"filter_" + userid + "\" name=\"filter\" value=\"Username\" readonly>" + 
							"<select id=\"user_cond" + userid + "\" name=\"condition\"\" required>" +
								"<option value=\"AND\">And</option>" +
								"<option value=\"OR\">Or</option>" +
							"</select>" +
							"<button type=\"button\" class=\"rem\">remove</button>" +
							"</div>";
							
			$("#search_div").append(html);
			
			$(".rem").on("click", function(){
				   $(this).parent().remove();
			});
			
			userid++;
			
		});
		
		$("#post_filter").on("click", function(){
			
			var html = 	"<div id=\"post_div" + postid + "\">" +
							"<input type=\"text\" id=\"post_val" + postid + "\" name=\"value\" required></input>" +
							"<input type=\"text\" id=\"filter_" + postid + "\" name=\"filter\" value=\"Post\" readonly>" + 
							"<select id=\"post_cond" + postid + "\" name=\"condition\"\" required>" +
								"<option value=\"AND\">And</option>" +
								"<option value=\"OR\">Or</option>" +
							"</select>" +
							"<button type=\"button\" class=\"rem\">remove</button>" +
							"</div>";
							
			$("#search_div").append(html);
			
			$(".rem").on("click", function(){
				   $(this).parent().remove();
			});
			
			postid++;
		});
		
		$("#date_filter").on("click", function(){
			
			var html = 	"<div id=\"date_div" + dateid + "\">" +
							"<input type=\"date\" id=\"date_val" + dateid + "\" name=\"value\" required></input>" +
							"<select class=\"date_opt\" id=\"date_opt" + dateid + "\" name=\"date_option\" required>" + 
								"<option value=\">=\">>=</option>" +
								"<option value=\"<=\"><=</option>" +
								"<option value=\"=\">=</option>" +
								"<option value=\"Between\">between</option>" +
							"</select>" +
							"<input type=\"text\" id=\"filter_" + dateid + "\" name=\"filter\" value=\"Postdate\" readonly>" + 
							"<select id=\"date_cond" + dateid + "\" name=\"condition\"\" required>" +
								"<option value=\"AND\">And</option>" +
								"<option value=\"OR\">Or</option>" +
							"</select>" +
							"<button type=\"button\" class=\"rem\">remove</button>" +
							"</div>";
							
			$("#search_div").append(html);
			
			$(".rem").on("click", function(){
				   $(this).parent().remove();
			});
			
			$("#date_val"+dateid).datepicker({
		    	maxDate: new Date()
		    }); 
			
			$("#date_opt"+dateid).on("change", function(){
				if($(this).val() == "Between") {
					var id = parseInt(this.id.substring(this.id.lastIndexOf("t")+1));
					var html = "<input type=\"date\" id=\"date_val" + parseInt(id+1) + "\" name=\"value\" required></input>";
					$(this).after(html);
					
					$("#date_val"+parseInt(id+1)).datepicker({
				    	maxDate: new Date()
				    }); 
					
				} else {
					var id = parseInt(this.id.substring(this.id.lastIndexOf("t")+1));
					alert("#date_val"+ parseInt(id+1));
					$("#date_val"+ parseInt(id+1)).remove();
				}
				
			});
			dateid+=2;
		});
		
	}
	
	
</script>

</html>