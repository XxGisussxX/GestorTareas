package models;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Tarea {
    private static int idCounter = 1;
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private boolean completada;
    private TipoPrioridad prioridad;

    public Tarea(String nombre, String descripcion, Date fecha, Time horaInicio, Time horaFin, TipoPrioridad prioridad) {
        this.id = idCounter++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.completada = false;
        this.prioridad = prioridad;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public boolean isCompletada() {
        return completada;
    }
    
    public TipoPrioridad getPrioridad() {
        return prioridad;
    }

    public DayOfWeek getDiaSemana() {
        return fecha.toLocalDate().getDayOfWeek();
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }
    
    public void setPrioridad(TipoPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    public void marcarComoCompletada() {
        this.completada = true;
    }
    
    public void toggleCompletada() {
        this.completada = !this.completada;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}