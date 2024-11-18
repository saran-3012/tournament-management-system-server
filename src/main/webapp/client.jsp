<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Client</title>
</head>
<body>
	<%
	    
		request.getRequestDispatcher("/index.html").forward(request, response);
	%>
</body>
</html>