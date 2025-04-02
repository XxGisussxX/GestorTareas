package models;

    public class Tarea {
        private String nombre;
        private String descripcion;
        private boolean completada;

        public Tarea(String nombre, String descripcion) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.completada = false;
        }

        public void marcarComoCompletada() {
            this.completada = true;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public boolean isCompletada() {
            return completada;
        }
    }


