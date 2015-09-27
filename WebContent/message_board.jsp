<style>
	div.scrollable {
	    width: 100%;
	    height: 100%;
	    margin: 0;
	    padding: 0;
	    overflow: auto;
	}
	.table {
	  	table-layout: fixed;
	  	border-collapse: collapse;
	  	width: 100%;
	}
	.first_col {
	  	border: 1px solid #000;
	  	width: 150px;
	}
	.second_col {
		border: 1px solid #000;
		width: auto;
	}
</style>

<div>
	<table class="table">
		<tr>
			<td class="first_col">User Info</td>
			<td class="second_col">User Post</td>
		</tr>
			
		<c:forEach var="post" items="${posts}">
			<tr>
				<td class="first_col">
					<a href="user_profile?username=${post.getInfo("username")}">${post.getInfo("username")}</a><br>
                	<a href="user_profile?username=${post.getInfo("firstname")}">${post.getInfo("firstname")}</a><br>
				</td>
				
				<td class="second_col">
					${post.getInfo("postdate")} <br>
					<div class="scrollable">
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

	
</div>