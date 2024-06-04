package Salida;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import Entrada.Reserva;

public class HTMLGenerator { private Calendar calendar = Calendar.getInstance();
    private int year;
    private int month; // Month is zero-based
    private int weeksInMonth;
    private DateFormatSymbols dfs;
    private String monthName;

    public HTMLGenerator(int year, int month) {
        this.year = year;
        this.month = month - 1; // Adjusting for zero-based month
        this.calendar.set(Calendar.YEAR, this.year);
        this.calendar.set(Calendar.MONTH, this.month);
        this.weeksInMonth = this.calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        this.dfs = new DateFormatSymbols(Locale.ENGLISH);
        this.monthName = dfs.getMonths()[this.month];
    }

    public void generarHTML(String nombreEspacio, List<Reserva> reservas) {
        try {
            // Obtener la ruta del directorio del workspace
            String workspacePath = System.getProperty("user.dir");
            String carpetaSalida = workspacePath + File.separator + "output";
            File directorio = new File(carpetaSalida);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crear el directorio si no existe
            }

            String rutaArchivo = carpetaSalida + File.separator + nombreEspacio + ".html";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
                writer.write("<!DOCTYPE html>\n");
                writer.write("<html>\n");
                writer.write("<head>\n");
                writer.write("<title>" + nombreEspacio.toUpperCase() + "</title>\n");
                writer.write("<style>\n");
                writer.write("body {\n");
                writer.write("    font-family: Verdana;\n");
                writer.write("    font-size: 10pt;\n");
                writer.write("}\n");
                writer.write("table {\n");
                writer.write("    width: 90%;\n");
                writer.write("    border-collapse: collapse;\n");
                writer.write("    margin: 0 auto;\n");
                writer.write("}\n");
                writer.write("th, td {\n");
                writer.write("    border: 1px solid #000;\n");
                writer.write("    padding: 5px;\n");
                writer.write("    text-align: center;\n");
                writer.write("}\n");
                writer.write("th {\n");
                writer.write("    background-color: #D6E7FA;\n");
                writer.write("}\n");
                writer.write("tr:nth-child(even) {\n");
                writer.write("    background-color: #E8F1FE;\n");
                writer.write("}\n");
                writer.write(".closed {\n");
                writer.write("    background-color: #b2aaaa;\n");
                writer.write("}\n");
                writer.write(".free {\n");
                writer.write("    background-color: #DFFAC4;\n");
                writer.write("}\n");
                writer.write(".reserved {\n");
                writer.write("    background-color: #FFCCCB;\n");
                writer.write("}\n");
                writer.write("h1, h2 {\n");
                writer.write("    text-align: center;\n");
                writer.write("}\n");
                writer.write("</style>\n");
                writer.write("</head>\n");
                writer.write("<body>\n");
                writer.write("<h1>" + nombreEspacio.toUpperCase() + "</h1>\n");
                writer.write("<h2>" + monthName + " " + year + "</h2>\n");

                int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

                for (int week = 1; week <= weeksInMonth; week++) {
                    writer.write("<table>\n");
                    writer.write("<thead>\n");
                    writer.write("<tr>\n");
                    writer.write("<th colspan=\"8\">Week " + (currentWeek + week - 1) + "</th>\n");
                    writer.write("</tr>\n");
                    writer.write("<tr>\n");
                    writer.write("<th>Hour</th>\n");
                    writer.write("<th>Monday</th>\n");
                    writer.write("<th>Tuesday</th>\n");
                    writer.write("<th>Wednesday</th>\n");
                    writer.write("<th>Thursday</th>\n");
                    writer.write("<th>Friday</th>\n");
                    writer.write("<th>Saturday</th>\n");
                    writer.write("<th>Sunday</th>\n");
                    writer.write("</tr>\n");
                    writer.write("<tr>\n");
                    writer.write("<td></td>\n"); // Empty cell for hours
                    for (int day = 1; day <= 7; day++) {
                        int dayOfMonth = (week - 1) * 7 + day - getDayOfWeekOfFirstDayOfMonth();
                        if (dayOfMonth <= 0 || dayOfMonth > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            writer.write("<td></td>\n");
                        } else {
                            writer.write("<td>" + dayOfMonth + "</td>\n");
                        }
                    }
                    writer.write("</tr>\n");
                    writer.write("</thead>\n");
                    writer.write("<tbody>\n");

                    for (int i = 0; i < 24; i++) {
                        writer.write("<tr>\n");
                        writer.write("<td>" + i + ":00 - " + (i + 1) + ":00</td>\n");
                        for (int j = 0; j < 7; j++) {
                            if (isClosed(j + 1, i)) {
                                writer.write("<td class=\"closed\">Closed</td>\n");
                            } else {
                                boolean hasReservation = false;
                                for (Reserva reserva : reservas) {
                                    if (isReservaInSlot(reserva, week, j + 1, i)) {
                                        writer.write("<td class=\"reserved\">" + reserva.getNameEvent() + "</td>\n");
                                        hasReservation = true;
                                        break;
                                    }
                                }
                                if (!hasReservation) {
                                    writer.write("<td class=\"free\">Free</td>\n");
                                }
                            }
                        }
                        writer.write("</tr>\n");
                    }

                    writer.write("</tbody>\n");
                    writer.write("</table>\n");
                }

                writer.write("</body>\n");
                writer.write("</html>\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getDayOfWeekOfFirstDayOfMonth() {
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.YEAR, year);
        firstDayOfMonth.set(Calendar.MONTH, month);
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - 2;
    }

    private boolean isReservaInSlot(Reserva reserva, int week, int day, int hour) {
        int dayOfMonth = (week - 1) * 7 + day - getDayOfWeekOfFirstDayOfMonth() + 1;
        if (dayOfMonth > 0 && dayOfMonth <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return reserva.getCoveredDays().contains(day) && reserva.getCoveredHours().contains(hour);
        }
        return false;
    }

    private boolean isClosed(int day, int hour) {
        if (day == 6 || day == 7) { // Sábado o Domingo
            return true;
        }
        if (hour >= 20 && hour <= 23) {
            return true;
        }
        return false;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}