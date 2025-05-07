package models;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calendario {
    private ObservableList<Tarea> tareas;
    
    public Calendario() {
        this.tareas = FXCollections.observableArrayList();
    }
    
    public ObservableList<Tarea> getTareas() {
        return tareas;
    }
    
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
    }
    
    public List<Tarea> buscarTarea(String busqueda, int id) {
        if (busqueda == null && id == -1) {
            return new ArrayList<>(tareas);
        }
        
        return tareas.stream()
                .filter(tarea -> (busqueda != null && tarea.getNombre().toLowerCase().contains(busqueda.toLowerCase())) 
                              || (id != -1 && tarea.getId() == id))
                .collect(Collectors.toList());
    }
    
    public boolean modificarTarea(int id, String nuevoNombre, String nuevaDescripcion, 
                                 Date nuevaFecha, TipoPrioridad nuevaPrioridad) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                tarea.setNombre(nuevoNombre);
                tarea.setDescripcion(nuevaDescripcion);
                if (nuevaFecha != null) {
                    tarea.setFecha(nuevaFecha);
                }
                if (nuevaPrioridad != null) {
                    tarea.setPrioridad(nuevaPrioridad);
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean eliminarTarea(int id) {
        return tareas.removeIf(tarea -> tarea.getId() == id);
    }
    
    public List<Tarea> getTareasPorDia(DayOfWeek dia) {
        return tareas.stream()
                .filter(tarea -> tarea.getDiaSemana() == dia)
                .collect(Collectors.toList());
    }
    
    public List<Tarea> getTareasPorPrioridad(TipoPrioridad prioridad) {
        return tareas.stream()
                .filter(tarea -> tarea.getPrioridad() == prioridad)
                .collect(Collectors.toList());
    }
}