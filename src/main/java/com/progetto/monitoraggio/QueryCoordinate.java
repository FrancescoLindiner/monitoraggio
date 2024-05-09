package com.progetto.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "queryServlet", value = "/coordinate")
public class QueryCoordinate extends HttpServlet {
    public static List<Coordinate> query(){
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        List<Coordinate> coordinateList = new ArrayList<>();
        try {
            connection = DBConnectionDatasource.getConnection();
            String query = "select latitudine, longitudine from Impianti";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                coordinateList.add(new Coordinate(latitudine, longitudine));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return coordinateList;
    }
}
