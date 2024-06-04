package Salida;

public class Incidencias { private String idReserva;
    private String nombreEvento;
    private String fechaInicio;
    private String fechaFin;
    private String sala;

    public Incidencias(String idReserva, String nombreEvento, String fechaInicio, String fechaFin, String sala) {
        this.idReserva = idReserva;
        this.nombreEvento = nombreEvento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sala = sala;
    }

    public String getIdReserva() {
        return idReserva;
    }

    //Deshuso
    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    //Deshuso
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    //Deshuso
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    //Deshuso
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getSala() {
        return sala;
    }

    //Deshuso
    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Conflicto detectado: " + nombreEvento + ", Fecha Inicio: " + fechaInicio + ", Fecha Fin: " + fechaFin + ", Sala: " + sala;
    }
}