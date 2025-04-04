import models.Tarea;
import models.Calendario;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calendario calendario = new Calendario();
        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\nElige una opción:");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Buscar tarea por nombre o ID");
            System.out.println("3. Modificar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Mostrar todas las tareas");
            System.out.println("6. Salir");
            System.out.print("Opción: ");

            int opcion = leerEntero(scanner);

            switch (opcion) {
                case 1 -> agregarTarea(calendario, scanner);
                case 2 -> buscarTareas(calendario, scanner);
                case 3 -> modificarTarea(calendario, scanner);
                case 4 -> eliminarTarea(calendario, scanner);
                case 5 -> mostrarTareas(calendario);
                case 6 -> ejecutando = false;
                default -> System.out.println("⚠ Opción no válida.");
            }
        }
    }

    private static void agregarTarea(Calendario calendario, Scanner scanner) {
        System.out.print("Ingrese el nombre de la tarea: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
        Date fecha = Date.valueOf(scanner.nextLine());
        System.out.print("Ingrese la hora de inicio (HH:MM:SS): ");
        Time horaInicio = Time.valueOf(scanner.nextLine());
        System.out.print("Ingrese la hora de fin (HH:MM:SS): ");
        Time horaFin = Time.valueOf(scanner.nextLine());

        Tarea nuevaTarea = new Tarea(nombre, descripcion, fecha, horaInicio, horaFin);
        calendario.agregarTarea(nuevaTarea);
        System.out.println("✅ Tarea agregada exitosamente: " + nuevaTarea.getId() + " - " + nuevaTarea.getNombre());
    }

    private static List<Tarea> buscarTareas(Calendario calendario, Scanner scanner) {
        System.out.print("Digite el nombre o el ID de la tarea que desea buscar: ");
        String busqueda = scanner.nextLine();
        int id = -1;

        try {
            id = Integer.parseInt(busqueda);
        } catch (NumberFormatException e) {
            // No es un número, se trata como un String
        }

        List<Tarea> resultados = calendario.buscarTarea(busqueda, id);

        if (resultados.isEmpty()) {
            System.out.println("⚠ No se encontraron tareas con ese nombre o ID.");
        } else {
            System.out.println("🔍 Tareas encontradas:");
            for (Tarea tarea : resultados) {
                System.out.println("➡ ID: " + tarea.getId() + " | Nombre: " + tarea.getNombre());
            }
        }
        return resultados;
    }

    private static void modificarTarea(Calendario calendario, Scanner scanner) {
        List<Tarea> resultados = buscarTareas(calendario, scanner);
        if (resultados.isEmpty()) return;

        System.out.print("Ingrese el ID de la tarea que desea modificar: ");
        int id = leerEntero(scanner);

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();

        boolean exito = calendario.modificarTarea(id, nuevoNombre, nuevaDescripcion);
        if (exito) {
            System.out.println("✅ Tarea modificada exitosamente.");
        } else {
            System.out.println("⚠ No se encontró una tarea con ese ID.");
        }
    }

    private static void eliminarTarea(Calendario calendario, Scanner scanner) {
        List<Tarea> resultados = buscarTareas(calendario, scanner);
        if (resultados.isEmpty()) return;

        System.out.print("Ingrese el ID de la tarea que desea eliminar: ");
        int id = leerEntero(scanner);

        System.out.print("⚠ ¿Seguro que quieres eliminar esta tarea de manera permanente? (S:1 / N:0): ");
        int confirmacion = leerEntero(scanner);

        if (confirmacion == 1) {
            boolean exito = calendario.eliminarTarea(id);
            if (exito) {
                System.out.println("✅ Tarea eliminada exitosamente.");
            } else {
                System.out.println("⚠ No se encontró una tarea con ese ID.");
            }
        } else {
            System.out.println("❌ Eliminación cancelada.");
        }
    }


    private static void mostrarTareas(Calendario calendario) {
        calendario.mostrarTareas();
    }

    private static int leerEntero(Scanner scanner) {
        while (true) {
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {               //isEmpty=null
                System.out.print("❌ Error: No ingresaste nada.Intenta nuevamente: ");
                continue;
            }
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                // java.lang.NumberFormatException will be thrown if the
                // input string can not be converted to a valid integer
                System.out.println("❌ Error: Debes ingresar un número válido.");
                System.out.print("Opción: ");
            }
        }
    }
}

