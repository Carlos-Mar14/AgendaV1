package Salida;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IncidenciasGenerator {
    public void generarIncidencias(String nombreEspacio, List<Incidencias> incidencias) {
        try {
            // Obtener la ruta del directorio del workspace
            String workspacePath = System.getProperty("user.dir");
            String carpetaSalida = workspacePath + File.separator + "output";
            File directorio = new File(carpetaSalida);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear el directorio si no existe
            }
            String rutaArchivo = carpetaSalida + File.separator + "LogIncidencias.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
                writer.write("# Resumen de Incidencias\n");
                for (Incidencias incidencia : incidencias) {
                    writer.write("# ID Reserva: " + incidencia.getIdReserva() + "\n");
                    writer.write("Nombre Evento: " + incidencia.getNombreEvento() + "\n");
                    writer.write("Fecha Inicio: " + incidencia.getFechaInicio() + "\n");
                    writer.write("Fecha Fin: " + incidencia.getFechaFin() + "\n");
                    writer.write("Sala: " + incidencia.getSala() + "\n");
                    writer.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}