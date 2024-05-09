<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.progetto.monitoraggio.QueryCoordinate" %>
<%
    QueryCoordinate.query();
%>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello !" %>
</h1>
<br/>
<a href="insert.html">Insert</a>
<a href="delete.html">Delete</a>
<a href="query.html">Query</a>
</body>
</html>