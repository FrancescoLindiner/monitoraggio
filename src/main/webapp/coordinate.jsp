<%@ page import="com.progetto.monitoraggio.QueryCoordinate" %>
<%@ page import="com.progetto.monitoraggio.Coordinate" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Coordinate</title>
</head>
<body>
<h1>Coordinate</h1>
<table>
    <thead>
    <tr>
        <th>Latitudine</th>
        <th>Longitudine</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<Coordinate> coordinateList = QueryCoordinate.query();
        for (Coordinate coordinate : coordinateList) {
    %>
    <tr>
        <td><%= coordinate.getLatitudine() %></td>
        <td><%= coordinate.getLongitudine() %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>
