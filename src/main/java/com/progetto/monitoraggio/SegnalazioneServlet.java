package com.progetto.monitoraggio;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

@WebServlet(name = "segnalazione", value = "/segnalazione")
public class SegnalazioneServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(SegnalazioneServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Il client invia i dati JSON nel corpo della richiesta, non in un parametro.
        // Quindi request.getParameter() sarà sempre nullo.

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        // Parsa i dati JSON
        String jsonInput = requestBody.toString();
        JSONObject jsonObject = new JSONObject(jsonInput);

        // Estrai i valori dall'oggetto JSON
        String idImpianto = jsonObject.getString("idImpianto");
        String idPalinsesto = jsonObject.getString("idPalinsesto");
        String idCartellone = jsonObject.getString("idCartellone");
        int durata = jsonObject.getInt("durata");

        insertSegnalazioneDB(idImpianto, idPalinsesto, idCartellone, durata);
    }

    private void insertSegnalazioneDB(String idImpianto, String idPalinsesto, String idCartellone, int durata) {
        Connection connection;
        PreparedStatement statement;

        try {
            connection = DBConnectionDatasource.getConnection();
            String insert = "INSERT INTO Segnalazioni(idImpianto, idPalinsesto, idCartellone, durata, timestamp) VALUES (?, ?, ?, ?, NOW());";
            statement = connection.prepareStatement(insert);

            statement.setString(1, idImpianto);
            statement.setString(2, idPalinsesto);
            statement.setString(3, idCartellone);
            statement.setInt(4, durata);

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
    }
}