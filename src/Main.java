import models.Tarea;
import models.Usuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario("Jesús");

        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\nElige una opción:");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Mostrar tareas");
            System.out.println("3. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre de la tarea: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la descripción: ");
                    String descripcion = scanner.nextLine();
                    usuario.agregarTarea(new Tarea(nombre, descripcion));
                    System.out.println("✅ Tarea agregada correctamente.");
                    break;
                case 2:
                    System.out.println("\n📋 Lista de tareas:");
                    for (Tarea tarea : usuario.getTareas()) {
                        System.out.println("- " + tarea.getNombre() + ": " + (tarea.isCompletada() ? "✔ Completada" : "❌ Pendiente"));
                    }
                    break;
                case 3:
                    ejecutando = false;
                    System.out.println("👋 Saliendo...");
                    break;
                default:
                    System.out.println("⚠ Opción no válida.");
            }
        }

        scanner.close();
    }
}
