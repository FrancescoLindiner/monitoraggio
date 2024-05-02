package com.progetto.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "testDBServlet", value = "/testdb")
public class TestDB extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                out.print(rs.getString("id") + ") ");
                out.println(rs.getString("firstname") + ", ");
                out.println(rs.getString("lastname") + "<br>");
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            out.println(exc.getMessage());
        }
    }
}