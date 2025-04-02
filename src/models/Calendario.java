package models;

import java.util.ArrayList;
import java.util.List;

public class Calendario {
    private List<Tarea> tareas;

    public Calendario() {
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public List<Tarea> obtenerTareas() {
        return tareas;
    }
}
