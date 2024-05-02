package com.progetto.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

@WebServlet(name = "queryServlet", value = "/query")
public class Query extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection;
        PreparedStatement statement;
        ResultSet rs;

        try {
            connection = DBConnectionDatasource.getConnection();
            String query = "select * from student where id=?";
            int id = Integer.parseInt(request.getParameter("queryid"));

            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            while (rs.next()) {
                out.println(rs.getInt("id") + ": ");
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