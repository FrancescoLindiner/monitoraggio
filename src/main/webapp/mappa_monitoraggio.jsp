<%@ page import="com.progetto.monitoraggio.MappaMonitoraggio" %>
<%@ page import="com.progetto.monitoraggio.Impianti" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Coordinate</title>
    <link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />
    <script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>
</head>
<body>
<h1>Mappa di Monitoraggio</h1>

<div id="map" style="width: 100vw; height: 90vh;"></div>

<script>
    var map = L.map('map').setView([38.1462, 13.3312], 12); // posizione iniziale della mappa
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);

    var active_icon = L.icon({
        iconUrl: 'images/active_icon.png',
        iconSize: [60, 60],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32]
    });

    var disactive_icon = L.icon({
        iconUrl: 'images/disactive_icon.png',
        iconSize: [60, 60],
        iconAnchor: [16, 32],
        popupAnchor: [0, -32]
    });

    <%
        List<Impianti> impianti = MappaMonitoraggio.getInfoImpianti(); // prende le info di tutit gli impianti
        List<String> segnalazioniAttive = MappaMonitoraggio.getSegnalazioniAttiveNegliUltimi2Minuti(); // prende gli impianti attivi negli ultimi 2 min
        List<Map<String, Object>> timestamp = MappaMonitoraggio.getUltimaSegnalazione(); // ritorna l'ultima segnalazione di tutti gli impianti
    %>

    <%
        int j = 0; // variabile ausialiaria per i timestamp

        for (int i = 0; i < impianti.size(); i++) {
            Impianti imp = impianti.get(i);
            boolean isActive = segnalazioniAttive.contains(imp.getId());
            String markerIcon = isActive ? "active_icon" : "disactive_icon";
            String popupContent;

            if (j < timestamp.size() && timestamp.get(j).containsKey(imp.getId())) {
                popupContent = "ID: " + imp.getId() + "<br>" + imp.getDescrizione() + "<br>" + "Ultima segnalazione inviata: " + timestamp.get(j).get(imp.getId());
                j++;
            } else {
                popupContent = "ID: " + imp.getId() + "<br>" + imp.getDescrizione() + "<br>" + "Nessuna segnalazione inviata";
            }
    %>
    var marker = L.marker([<%= imp.getLatitudine() %>, <%= imp.getLongitudine() %>], {icon: <%= markerIcon %>}).addTo(map); // aggiunge il marker alla mappa
    marker.bindPopup('<%= popupContent %>'); // aggiunge il popup

    marker.on('mouseover', function (e) {
        this.openPopup();
    });
    marker.on('mouseout', function (e) {
        this.closePopup();
    });
    <%
        }
    %>
</script>
</body>
</html>
