package utilidades;

import Entrada.Reserva;

import java.util.HashMap;
import java.util.Map;

public class MaskDiasSemana {
    private static final Map<String, Map<String, String>> dayMap = new HashMap<>();

    static {
        //Mascara de dias de la semana, segun enunciado
        Map<String, String> espanolIngles = new HashMap<>();
        espanolIngles.put("L", "Monday");
        espanolIngles.put("M", "Tuesday");
        espanolIngles.put("C", "Wednesday");
        espanolIngles.put("J", "Thursday");
        espanolIngles.put("V", "Friday");
        espanolIngles.put("S", "Saturday");
        espanolIngles.put("D", "Sunday");

        dayMap.put("español", espanolIngles);
    }

    //Clase para convertir de inglés a español el manejo de datos
    public static String convertirDiaDeLaSemana(Character diaLetra) {
        Map<String, String> idiomaMap = dayMap.get("español"); // Asumimos que siempre estamos convirtiendo de español a inglés
        if (idiomaMap!= null && idiomaMap.containsKey(diaLetra.toString())) {
            return idiomaMap.get(diaLetra.toString()).toLowerCase();
        }
        return null;
    }

    public static String getDiaDeLaSemana(Character diaLetra, String idiomaEntrada, String idiomaSalida) {
        Map<String, String> idiomaMap = dayMap.get(idiomaEntrada.toLowerCase());
        if (idiomaMap!= null && idiomaMap.containsKey(diaLetra.toString())) {
            return idiomaMap.get(diaLetra.toString()).toLowerCase();
        }
        return null;
    }

    public static String getDiasDeLaSemanaEnIngles(Reserva request) {
        StringBuilder diasEnIngles = new StringBuilder();
        for (char diaLetra : request.getDaysMask().toCharArray()) {
            String diaEnIngles = MaskDiasSemana.convertirDiaDeLaSemana(diaLetra);
            if (diaEnIngles!= null) {
                diasEnIngles.append(diaEnIngles).append(", ");
            }
        }
        // Eliminar la última coma y espacio agregados
        if (!diasEnIngles.isEmpty()) {
            diasEnIngles.deleteCharAt(diasEnIngles.length() - 1); // Elimina la última coma
            diasEnIngles.deleteCharAt(diasEnIngles.length() - 1); // Elimina el último espacio
        }
        return diasEnIngles.toString();
    }


}