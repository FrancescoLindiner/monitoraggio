package com.progetto.monitoraggio;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.*;

@WebServlet(name = "queryServlet", value = "/mappa-monitoraggio")
public class MappaMonitoraggio extends HttpServlet {
    public static List<Impianti> getInfoImpianti(){
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        List<Impianti> impianti = new ArrayList<>();
        try {
            connection = DBConnectionDatasource.getConnection();
            String query = "select idImpianto, descrizione, latitudine, longitudine from Impianti";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("idImpianto");
                String descrizione = rs.getString("descrizione");
                float latitudine = rs.getFloat("latitudine");
                float longitudine = rs.getFloat("longitudine");
                impianti.add(new Impianti(id, descrizione, latitudine, longitudine));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return impianti;
    }


    public static List<String> getSegnalazioniAttiveNegliUltimi2Minuti() { // ritorna gli impianti attivi negli ultimi 2 min
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        List<String> segnalazioniAttive = new ArrayList<>();
        try {
            connection = DBConnectionDatasource.getConnection();

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            cal.add(Calendar.MINUTE, -2); // tolgo 2 minuti dalla data corrente
            Date twoMinutesAgo = cal.getTime();

            String query = "SELECT idImpianto FROM Segnalazioni WHERE timestamp >= ?";
            statement = connection.prepareStatement(query);
            statement.setTimestamp(1, new java.sql.Timestamp(twoMinutesAgo.getTime()));
            rs = statement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("idImpianto");
                if(!segnalazioniAttive.contains(id))
                    segnalazioniAttive.add(id);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return segnalazioniAttive;
    }

    public static List<Map<String, Object>> getUltimaSegnalazione() { // ritorna l'ultima segnalazione effettuata
        Connection connection;
        PreparedStatement statement;
        ResultSet rs;
        List<Map<String, Object>> timestampImpianti = new ArrayList<>();
        try {
            connection = DBConnectionDatasource.getConnection();

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            cal.add(Calendar.MINUTE, -2); // tolgo 2 minuti dalla data corrente

            String query = "SELECT idImpianto, MAX(timestamp) AS ultimo_timestamp FROM Segnalazioni GROUP BY idImpianto";

            statement = connection.prepareStatement(query);

            rs = statement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("idImpianto");
                Date timestamp = rs.getTimestamp("ultimo_timestamp");

                Map<String, Object> segnalazione = new HashMap<>();
                segnalazione.put(id, timestamp);

                timestampImpianti.add(segnalazione);
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return timestampImpianti;
    }

}