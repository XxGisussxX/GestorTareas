package models;

public enum TipoPrioridad {
    IMPORTANTE("#5CB85C", "Tarea importante"),
    PENDIENTE("#F0AD4E", "Tarea pendiente"),
    URGENTE("#D9534F", "Tarea urgente");
    
    private final String colorHex;
    private final String descripcion;
    
    TipoPrioridad(String colorHex, String descripcion) {
        this.colorHex = colorHex;
        this.descripcion = descripcion;
    }
    
    public String getColorHex() {
        return colorHex;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}