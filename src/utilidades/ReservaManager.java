package utilidades;

import Salida.Incidencias;
import Entrada.Reserva;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaManager { private static final List<Incidencias> incidenciasList = new ArrayList<>();

    public void addIncidencia(Incidencias incidencia) {
        incidenciasList.add(incidencia);
    }

    public static List<Incidencias> getIncidencias() {
        return incidenciasList;
    }

    public static void validarYProcesarReservas(List<Reserva> solicitudes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < solicitudes.size(); i++) {
            Reserva currentRequest = solicitudes.get(i);

            System.out.println("\n\nProcesando reserva: " + currentRequest.getNameEvent());

            try {
                Date dateStart = dateFormat.parse(currentRequest.getDateStart());
                Date dateEnd = dateFormat.parse(currentRequest.getDateEnd());

                System.out.println("Fecha de inicio: " + currentRequest.getDateStart());
                System.out.println("Fecha de fin: " + currentRequest.getDateEnd());

                // Obtener y imprimir los horarios específicos de la reunión
                List<MaskHours> timeSlots = MaskHours.parseHourlyMask(currentRequest.getHoursMask());
                System.out.println("Horario:");
                for (MaskHours slot : timeSlots) {
                    System.out.println("Inicio: " + slot.getStartTime() + ", Fin: " + slot.getEndTime());
                }

                // Ignorar las reservas marcadas como "Cerrado" al buscar conflictos
                if ("Cerrado".equalsIgnoreCase(currentRequest.getNameEvent())) {
                    System.out.println("Reserva marcada como 'Cerrado'");
                    continue; // Saltar el resto del bucle para esta reserva
                }

                // Verificar que la fecha de fin sea posterior a la fecha de inicio
                if (dateEnd.before(dateStart)) {
                    System.out.println("Reserva no válida: " + currentRequest.getNameEvent());
                    continue;
                }

                // Verificar si hay otra reunión con la misma fecha y horario en la misma sala
                for (int j = i + 1; j < solicitudes.size(); j++) {
                    Reserva otherRequest = solicitudes.get(j);
                    try {
                        Date otherDateStart = dateFormat.parse(otherRequest.getDateStart());
                        Date otherDateEnd = dateFormat.parse(otherRequest.getDateEnd());

                        // Verificar si las fechas de inicio y fin se solapan
                        if ((dateFormat.parse(currentRequest.getDateStart()).before(otherDateEnd) || dateFormat.parse(currentRequest.getDateStart()).equals(otherDateEnd)) &&
                                (dateFormat.parse(currentRequest.getDateEnd()).after(otherDateStart) || dateFormat.parse(currentRequest.getDateEnd()).equals(otherDateStart)) &&
                                currentRequest.getNameRoom().equals(otherRequest.getNameRoom())) {
                            System.out.println("Incidencia: Otra reunión con la misma fecha y horario en la misma sala: " +
                                    otherRequest.getNameEvent());
                            // Crear una instancia de Incidencias y agregarla a la lista de incidencias
                            // Usando el índice de la solicitud como parte del identificador
                            incidenciasList.add(new Incidencias(Integer.toString(i) + "-" + j, currentRequest.getNameEvent(), currentRequest.getDateStart(), currentRequest.getDateEnd(), currentRequest.getNameRoom()));
                        }
                    } catch (ParseException e) {
                        System.out.println("Reserva no válida: " + otherRequest.getNameEvent());
                    }
                }

                System.out.println("Máscara de días de la semana:");
                // Aquí asumimos que tienes una lógica para convertir y mostrar la máscara de días de la semana
                System.out.println(MaskDiasSemana.getDiasDeLaSemanaEnIngles(currentRequest)); // Mostrar la máscara de días de la semana

                System.out.println("Reserva válida: " + currentRequest.getNameEvent());
            } catch (ParseException e) {
                System.out.println("Reserva no válida: " + currentRequest.getNameEvent());
            }
        }
    }
}