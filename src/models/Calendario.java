package models;

import java.util.ArrayList;
import java.util.List;

public class Calendario {
    private  List<Tarea> tareas;
    // Builder pattern
    public Calendario() {
        this.tareas = new ArrayList<>();
    }
    // Get elements of the array
    public List<Tarea> getTareas() {
        return tareas;
    }

    // Add homework
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    // Search homework for name or ID
    public List<Tarea> buscarTarea(String nombre, int id) {
        List<Tarea> resultados = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.getNombre().toLowerCase().contains(nombre.toLowerCase()) || tarea.getId() == id) {
                resultados.add(tarea);
            }
        }
        return resultados;
    }

    // Modify homework for name or ID
    public boolean modificarTarea(int id, String nuevoNombre, String nuevaDescripcion) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setNombre(nuevoNombre);
                tarea.setDescripcion(nuevaDescripcion);
                return true;
            }
        }
        return false; // No se encontró la tarea
    }

    // Delete homework for name or ID
    public boolean eliminarTarea(int id) {
        return tareas.removeIf(tarea -> tarea.getId() == id);
    }

    // Show all homeworks
    public void mostrarTareas() {
        for (Tarea tarea : tareas) {
            System.out.println("ID: " + tarea.getId() + " - Nombre: " + tarea.getNombre() + " - Descripción: " + tarea.getDescripcion());
        }
    }
}
