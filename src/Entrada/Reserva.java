package Entrada;

import utilidades.MaskHours;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Reserva {

    // Atributos
    private String nameEvent;
    private final String nameRoom;
    private final String dateStart;
    private final String dateEnd;
    private final String daysMask;
    private final String hoursMask;
    private List<MaskHours> timeSlots;

    // Constructor
    public Reserva(String nameEvent, String nameRoom, String dateStart, String dateEnd, String daysMask, String hoursMask) {
        this.nameEvent = nameEvent;
        this.nameRoom = nameRoom;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.daysMask = daysMask;
        this.hoursMask = hoursMask;
        this.timeSlots = new ArrayList<>();
    }

    // Getters y setters

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDaysMask() {
        return daysMask;
    }

    public String getHoursMask() {
        return hoursMask;
    }

    // Método para obtener los días cubiertos por la reserva
    public List<Integer> getCoveredDays() {
        List<Integer> coveredDays = new ArrayList<>();
        for (int i = 0; i < daysMask.length(); i++) {
            char dayChar = daysMask.charAt(i);
            switch (dayChar) {
                case 'L':
                    coveredDays.add(1); // Lunes
                    break;
                case 'M':
                    coveredDays.add(2); // Martes
                    break;
                case 'C':
                    coveredDays.add(3); // Miércoles
                    break;
                case 'J':
                    coveredDays.add(4); // Jueves
                    break;
                case 'V':
                    coveredDays.add(5); // Viernes
                    break;
                case 'S':
                    coveredDays.add(6); // Sábado
                    break;
                case 'D':
                    coveredDays.add(7); // Domingo
                    break;
                default:
                    throw new IllegalArgumentException("Caracter de día no válido en daysMask: " + dayChar);
            }
        }
        return coveredDays;
    }


    public List<Integer> getCoveredHours() {
        List<Integer> coveredHours = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            Date startDate = sdf.parse(dateStart);
            Date endDate = sdf.parse(dateEnd);
            calendar.setTime(startDate);

            while (!calendar.getTime().after(endDate)) {
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    // Si es sábado o domingo, se considera como día de cierre
                    String[] intervals = hoursMask.split("_");
                    for (String interval : intervals) {
                        String[] range = interval.split("-");
                        int start = Integer.parseInt(range[0]);
                        int end = Integer.parseInt(range[1]);
                        for (int i = start; i <= end; i++) {
                            coveredHours.add(i);
                        }
                    }
                } else {
                    // Si no es sábado ni domingo, se considera como día laboral
                    String[] intervals = hoursMask.split("_");
                    for (String interval : intervals) {
                        String[] range = interval.split("-");
                        int start = Integer.parseInt(range[0]);
                        int end = Integer.parseInt(range[1]);
                        for (int i = start; i <= end; i++) {
                            coveredHours.add(i);
                        }
                    }
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1); // Avanzar al siguiente día
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return coveredHours;
    }

    //TODO: <<<<<<<<<<<<<<<<<<<<<<<Deshuso??>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public int getWeekOfYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date startDate = sdf.parse(dateStart);
            calendar.setTime(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //TODO: <<<<<<<<<<<<<<<<<<<<<<<Deshuso??>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public int getStartDayOfWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(dateStart);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            return calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //TODO: <<<<<<<<<<<<<<<<<<<<<<<Deshuso??>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public int getDurationInDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date startDate = sdf.parse(dateStart);
            Date endDate = sdf.parse(dateEnd);
            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
            return (int) diffInDays + 1; // Sumamos 1 para incluir el primer día
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Si ocurre algún error, devolvemos -1
        }
    }


    public List<List<MaskHours>> getReservedTimeSlotsPerDay() {
        List<List<MaskHours>> reservedTimeSlotsPerDay = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            Date startDate = sdf.parse(dateStart);
            Date endDate = sdf.parse(dateEnd);
            calendar.setTime(startDate);

            // Inicializar la lista de intervalos de tiempo para cada día
            for (int i = 0; i < 7; i++) {
                reservedTimeSlotsPerDay.add(new ArrayList<>());
            }

            while (!calendar.getTime().after(endDate)) {
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                if (daysMask.contains(getDayOfWeekChar(dayOfWeek))) {
                    String[] intervals = hoursMask.split("_");
                    for (String interval : intervals) {
                        String[] range = interval.split("-");
                        int startHour = Integer.parseInt(range[0].split(":")[0]);
                        int startMinute = Integer.parseInt(range[0].split(":")[1]);
                        int endHour = Integer.parseInt(range[1].split(":")[0]);
                        int endMinute = Integer.parseInt(range[1].split(":")[1]);

                        LocalTime startTime = LocalTime.of(startHour, startMinute);
                        LocalTime endTime = LocalTime.of(endHour, endMinute);

                        MaskHours maskHours = new MaskHours(startTime, endTime);
                        reservedTimeSlotsPerDay.get(dayOfWeek - 1).add(maskHours);
                    }
                }

                calendar.add(Calendar.DAY_OF_MONTH, 1); // Avanzar al siguiente día
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reservedTimeSlotsPerDay;
    }

    private String getDayOfWeekChar(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return "L";
            case Calendar.TUESDAY:
                return "M";
            case Calendar.WEDNESDAY:
                return "C";
            case Calendar.THURSDAY:
                return "J";
            case Calendar.FRIDAY:
                return "V";
            case Calendar.SATURDAY:
                return "S";
            case Calendar.SUNDAY:
                return "D";
            default:
                return "";
        }
    }

}