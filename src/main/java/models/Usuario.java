package models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private Calendario calendarioPersonal;

    public Usuario() {
        this.calendarioPersonal = new Calendario();
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.calendarioPersonal = new Calendario();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Calendario getCalendarioPersonal() {
        return calendarioPersonal;
    }

    public void setCalendarioPersonal(Calendario calendarioPersonal) {
        this.calendarioPersonal = calendarioPersonal;
    }

    // Métodos para gestionar tareas directamente desde el usuario

    public void agregarTarea(Tarea tarea) {
        this.calendarioPersonal.getTareas().add(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        this.calendarioPersonal.getTareas().remove(tarea);
    }

    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> tareasPendientes = new ArrayList<>();

        for (Tarea tarea : this.calendarioPersonal.getTareas()) {
            if (!tarea.isCompletada()) {
                tareasPendientes.add(tarea);
            }
        }

        return tareasPendientes;
    }

    public List<Tarea> obtenerTareasPorPrioridad(TipoPrioridad prioridad) {
        List<Tarea> tareasFiltradas = new ArrayList<>();

        for (Tarea tarea : this.calendarioPersonal.getTareas()) {
            if (tarea.getPrioridad() == prioridad) {
                tareasFiltradas.add(tarea);
            }
        }

        return tareasFiltradas;
    }
}