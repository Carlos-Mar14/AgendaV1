package Salida;

import Entrada.ConfigManager;
import Entrada.Configuration;
import Entrada.RequestManager;
import Entrada.Reserva;
import utilidades.ReservaManager;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializar la configuración
        Configuration configuration = null;
        try {
            ConfigManager configManager = new ConfigManager("src/Config.txt");
            configuration = configManager.readConfiguration();
            String year = configuration.getYear();
            String month = configuration.getMonth();
            String inputLanguage = configuration.getInputLanguage();
            String outputLanguage = configuration.getOutputLanguage();
            System.out.println("Año: " + year + ", Mes: " + month + ", Idioma de entrada: " + inputLanguage + ", Idioma de salida: " + outputLanguage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inicializar las reservas
        List<Reserva> reservations = List.of();
        String pathToPeticions = "src/Reservas_mayo.txt";
        try {
            RequestManager requestManager = new RequestManager(pathToPeticions);
            reservations = requestManager.readRequests();
            System.out.println("Número total de reservas: " + reservations.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Procesar las reservas
        try {
            ReservaManager.validarYProcesarReservas(reservations);
            System.out.println("\nProcesamiento de reservas completado.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Acceder a las incidencias detectadas después de procesar todas las reservas
        List<Incidencias> incidenciasDetected = ReservaManager.getIncidencias();
        for (Incidencias incidencia : incidenciasDetected) {
            System.out.println(incidencia.toString());
        }

        // Generar el archivo de log de incidencias
        if (!incidenciasDetected.isEmpty()) {
            String rutaArchivo = "src/LogIncidencias.txt"; // Cambia la ruta según tu preferencia
            IncidenciasGenerator incidenciasGenerator = new IncidenciasGenerator();
            incidenciasGenerator.generarIncidencias(rutaArchivo, incidenciasDetected);
            System.out.println("\nArchivo de log de incidencias generado en: " + rutaArchivo);
        } else {
            System.out.println("\nNo se detectaron incidencias. No se generó el archivo de log.");
        }

        // Generar los archivos HTML para cada sala
        if (configuration != null) {
            HTMLGenerator htmlGenerator = new HTMLGenerator(Calendar.getInstance().get(Calendar.YEAR), 5);

            // Filtrar reservas para Sala1
            List<Reserva> reservasSala1 = reservations.stream()
                    .filter(reserva -> "Sala1".equalsIgnoreCase(reserva.getNameRoom()))
                    .toList();
            htmlGenerator.generarHTML("Sala1", reservasSala1);

            // Filtrar reservas para Sala2
            List<Reserva> reservasSala2 = reservations.stream()
                    .filter(reserva -> "Sala2".equalsIgnoreCase(reserva.getNameRoom()))
                    .toList();
            htmlGenerator.generarHTML("Sala2", reservasSala2);
        }
    }
}
