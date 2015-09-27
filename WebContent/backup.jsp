<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Date"
    import="java.util.List"
    import="java.io.File"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
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
		}
		.second_col {
			border: 1px solid #000;
		}
	</style>

	<div>
		<form action="download_backup" method="POST">
			<table class="table">
				<tr>
					<td class="first_col">File</td>
					<td class="second_col">Date Modified</td>
				</tr>
				
				<%
				List<File> files = (List<File>)request.getAttribute("files");
				for(int i = 0 ; i < files.size(); i++) { %>
					<tr>
						<td class="first_col">
							<input type="checkbox" name="files" value="<%=files.get(i).getName() %>"><%=files.get(i).getName() %><br>
						</td>
						<td class="second_col">
							<%=new Date(files.get(i).lastModified())%>
						</td>
					</tr>
				<%}%>
	
			</table>
			<button type="submit">download</button>
		</form>
	</div>
</body>
</html>