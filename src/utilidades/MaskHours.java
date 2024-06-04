package utilidades;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MaskHours {

    private final LocalTime startTime;
    private final LocalTime endTime;

    public MaskHours(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    public static List<MaskHours> parseHourlyMask(String mask) {
        List<MaskHours> timeSlots = new ArrayList<>();
        String[] segments = mask.split("_");

        for (String segment : segments) {
            String[] rangeParts = segment.split("-");
            LocalTime startTime = LocalTime.of(Integer.parseInt(rangeParts[0]), 0);
            LocalTime endTime = LocalTime.of(Integer.parseInt(rangeParts[1]), 0);

            timeSlots.add(new MaskHours(startTime, endTime));
        }

        return timeSlots;
    }

    public static List<MaskHours> combineTimeSlots(List<MaskHours> timeSlots) {
        List<MaskHours> combinedSlots = new ArrayList<>();
        MaskHours currentSlot = timeSlots.get(0);

        for (int i = 1; i < timeSlots.size(); i++) {
            MaskHours nextSlot = timeSlots.get(i);
            if (nextSlot.getStartTime().isAfter(currentSlot.getEndTime())) {
                // Si la próxima franja horaria comienza después de la actual,
                // significa que no se solapan y podemos agregar la actual al resultado.
                combinedSlots.add(currentSlot);
                currentSlot = nextSlot;
            } else if (nextSlot.getStartTime().equals(currentSlot.getEndTime())) {
                // Si la próxima franja horaria comienza justo después de la actual,
                // significa que se solapan y debemos combinarlas.
                currentSlot = new MaskHours(currentSlot.getStartTime(), nextSlot.getEndTime());
            } else {
                // Si la próxima franja horaria se solapa con la actual pero no comienza justo después,
                // debemos combinarlas en una sola franja.
                currentSlot = new MaskHours(currentSlot.getStartTime(), nextSlot.getEndTime());
            }
        }

        // Agregar la última franja horaria al resultado
        combinedSlots.add(currentSlot);

        return combinedSlots;
    }

    //Deshuso para este proyecto temporalmente
    public static boolean hasOverlap(List<MaskHours> timeSlots1, List<MaskHours> timeSlots2) {
        for (MaskHours slot1 : timeSlots1) {
            for (MaskHours slot2 : timeSlots2) {
                if (!slot1.getEndTime().isBefore(slot2.getStartTime()) &&!slot2.getEndTime().isBefore(slot1.getStartTime())) {
                    return true;
                }
            }
        }
        return false;
    }


}