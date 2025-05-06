package com.gestortareas.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Usuario {
    private String nombre;
    private ObservableList<Tarea> tareas;
    private Calendario calendario;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.tareas = FXCollections.observableArrayList();
        this.calendario = new Calendario();
    }

    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        calendario.agregarTarea(tarea);
    }

    public ObservableList<Tarea> getTareas() {
        return tareas;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Calendario getCalendario() {
        return calendario;
    }
}