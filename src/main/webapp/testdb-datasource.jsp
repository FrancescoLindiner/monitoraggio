<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%
    Context ctx = null;
    DataSource ds = null;
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Test DB</title>
</head>
<body>
<%
    ctx = new InitialContext();
    ds = (DataSource) ctx.lookup("java:comp/env/jdbc/es7");
    connection = ds.getConnection();
    statement = connection.createStatement();
    rs = statement.executeQuery("SELECT * FROM student WHERE lastname='verdi'");
    while (rs.next()) {
%>

<%= rs.getString("id") + ") "%>
<%= rs.getString("firstname") + ", "%>
<%= rs.getString("lastname") + "<br>"%>

<%
    }
    rs.close();
    statement.close();
    connection.close();
%>
</body>
</html>