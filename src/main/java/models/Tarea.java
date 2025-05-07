package models;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private boolean completada;
    private TipoPrioridad prioridad;

    public Tarea() {
        this.completada = false;
        this.prioridad = TipoPrioridad.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    // Para mantener compatibilidad con el código antiguo
    @Deprecated
    public Time getHora() {
        return horaInicio;
    }

    // Para mantener compatibilidad con el código antiguo
    @Deprecated
    public void setHora(Time hora) {
        this.horaInicio = hora;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public TipoPrioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(TipoPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    // Método que devuelve el día de la semana de la tarea
    public DayOfWeek getDiaSemana() {
        LocalDate localDate = this.fecha.toLocalDate(); // Convertir la fecha a LocalDate
        return localDate.getDayOfWeek(); // Obtener el día de la semana
    }

    @Override
    public String toString() {
        return nombre;
    }
}
