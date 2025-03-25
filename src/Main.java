import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numeroTareas = 0;
        String[] tareas = new String[5]; // Almacenar tareas
        Scanner miLector = new Scanner(System.in);
        boolean ejecutar = true;

        while (ejecutar) {
            System.out.print("Elige una opción: ");
            System.out.println("\n1. Agregar tarea");
            System.out.println("2. Buscar");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Mostrar las tareas");
            System.out.println("6. Salir");
            int opcion = miLector.nextInt();
            miLector.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1: //AGREGAR
                    if (numeroTareas < tareas.length) {
                        System.out.print("Ingrese la nueva tarea: ");
                        tareas[numeroTareas] = miLector.nextLine().trim().toLowerCase();
                        System.out.println("Tarea agregada correctamente.");
                        numeroTareas++;
                    } else {
                        System.out.println("No se pueden agregar más tareas.");
                    }
                    break;
                case 2://BUSCAR
                    System.out.print("Ingresa el id o el nombre de la tarea : ");
                    String busqueda = miLector.nextLine().toLowerCase().trim();
                    buscarTarea(tareas, busqueda);
                    int resultadoBusqueda = buscarTarea(tareas, busqueda);
                    if (resultadoBusqueda != -1) {
                        System.out.println("✅ Tarea encontrada: " + (resultadoBusqueda + 1) + ". " + tareas[resultadoBusqueda]);
                    } else {
                        System.out.println("⚠️ Tarea no encontrada.");
                    }
                    break;
                case 3: // MODIFICAR
                    System.out.print("Ingresa el id o el nombre de la tarea a modificar: ");
                    String entrada = miLector.nextLine().trim().toLowerCase();
                    int index = buscarTarea(tareas, entrada);

                    if (index != -1) {
                        System.out.print("Nueva descripción de la tarea: ");
                        tareas[index] = miLector.nextLine().trim().toUpperCase();
                        System.out.println("Tarea modificada con éxito.");
                    } else {
                        System.out.println("Tarea no encontrada.");
                    }
                    break;

                case 4: // ELIMINAR
                    System.out.print("Ingresa el id o el nombre de la tarea a eliminar: ");
                    entrada = miLector.nextLine().trim().toLowerCase();
                    index = buscarTarea(tareas, entrada);

                    if (index != -1) {
                        for (int i = index; i < numeroTareas - 1; i++) {
                            tareas[i] = tareas[i + 1]; // Desplazar elementos a la izquierda
                        }
                        tareas[numeroTareas - 1] = null; // Vaciar la última posición
                        numeroTareas--;
                        System.out.println("Tarea eliminada con éxito.");
                    } else {
                        System.out.println("Tarea no encontrada.");
                    }
                    break;

                case 5:
                    System.out.println("\n📌 Lista de tareas:");
                    for (int i = 0; i < numeroTareas; i++) {
                        System.out.println(i+1 + ". " + tareas[i]);
                    }
                    break;

                case 6:
                    ejecutar = false;
                    System.out.println("¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    //  Función para buscar tarea por número o nombre
    public static int buscarTarea(String[] tareas, String entrada) {
        try {
            int numero = Integer.parseInt(entrada)-1; // Intentar convertir en número
            if (numero >= 0 && numero < tareas.length && tareas[numero] != null) {
                return numero; // Si es válido, devolver el índice
            }
        } catch (NumberFormatException e) {
            // Si no es número, buscar por nombre
            for (int i = 0; i < tareas.length; i++) {
                if (tareas[i] != null && tareas[i].contains(entrada)) {
                    return i; // Devuelve la posición donde está el nombre
                }
            }
        }
        return -1; // Si no se encuentra, devolver -1
    }
}

