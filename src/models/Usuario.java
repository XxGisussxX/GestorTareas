package models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String nombre;
    private List<Tarea> tareas;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public String getNombre() {
        return nombre;
    }
}
