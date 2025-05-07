package controller;

import models.Calendario;
import models.Tarea;
import models.TipoPrioridad;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController {

    @FXML
    private Button btnAgregarTarea;

    @FXML
    private ListView<Tarea> listViewTareas;

    @FXML
    private GridPane gridCalendario;

    @FXML
    private Label statusLabel;

    private Calendario calendario;
    private Map<Integer, Pane> celdasCalendario;

    public void initialize() {
        calendario = new Calendario();
        celdasCalendario = new HashMap<>();

        // Configurar el evento del botón para agregar tareas
        btnAgregarTarea.setOnAction(event -> abrirDialogoTarea(null));

        // Configurar la celda personalizada para el ListView
        configurarListView();

        // Inicializar el grid del calendario
        inicializarGridCalendario();

        // Listener para actualizar el calendario cuando cambian las tareas
        calendario.getTareas().addListener((ListChangeListener<Tarea>) change -> {
            actualizarCalendario();
            actualizarStatusLabel();
        });

        // Agregar algunas tareas de ejemplo
        agregarTareasEjemplo();
    }

    private void configurarListView() {
        listViewTareas.setItems(calendario.getTareas());
        listViewTareas.setCellFactory(new Callback<ListView<Tarea>, ListCell<Tarea>>() {
            @Override
            public ListCell<Tarea> call(ListView<Tarea> param) {
                return new ListCell<Tarea>() {
                    @Override
                    protected void updateItem(Tarea tarea, boolean empty) {
                        super.updateItem(tarea, empty);

                        if (empty || tarea == null) {
                            setText(null);
                            setGraphic(null);
                            setStyle("");
                        } else {
                            try {
                                // Corregir la ruta del archivo FXML
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TareaCell.fxml"));
                                Parent cellRoot = loader.load();

                                TareaCellController controller = loader.getController();
                                controller.setTarea(tarea);
                                controller.setMainController(MainController.this);

                                // Aplicar estilo según prioridad
                                String estiloprioridad = "";
                                switch (tarea.getPrioridad()) {
                                    case IMPORTANTE -> estiloprioridad = "prioridad-importante";
                                    case PENDIENTE -> estiloprioridad = "prioridad-pendiente";
                                    case URGENTE -> estiloprioridad = "prioridad-urgente";
                                }

                                cellRoot.getStyleClass().add(estiloprioridad);

                                setGraphic(cellRoot);
                            } catch (IOException e) {
                                setText(tarea.getNombre());
                                e.printStackTrace();
                            }
                        }
                    }
                };
            }
        });
    }

    private void inicializarGridCalendario() {
        // Crear celdas para cada día y hora (5 días x 6 horas)
        for (int columna = 0; columna < 5; columna++) {
            for (int fila = 1; fila < 7; fila++) {
                Pane celda = new Pane();
                celda.getStyleClass().add("celda-calendario");

                // Guardar la referencia a la celda
                int indice = columna * 10 + fila;
                celdasCalendario.put(indice, celda);

                // Añadir a la cuadrícula (ajustar los índices según tu FXML)
                gridCalendario.add(celda, columna, fila);
            }
        }

        // Agregar etiquetas para los días
        String[] diasSemana = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        for (int i = 0; i < diasSemana.length; i++) {
            Label lblDia = new Label(diasSemana[i]);
            lblDia.getStyleClass().add("etiqueta-dia");
            gridCalendario.add(lblDia, i + 1, 0);
        }

        // Agregar etiquetas para las horas
        String[] horas = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00"};
        for (int i = 0; i < horas.length; i++) {
            Label lblHora = new Label(horas[i]);
            lblHora.getStyleClass().add("etiqueta-hora");
            gridCalendario.add(lblHora, 0, i + 1);
        }
    }

    private void actualizarCalendario() {
        // Limpiar todas las celdas primero
        for (Pane celda : celdasCalendario.values()) {
            celda.getChildren().clear();
            celda.getStyleClass().removeAll("tiene-tarea");
        }

        // Colocar las tareas en el calendario
        LocalDate hoy = LocalDate.now();
        LocalDate inicioDeSemana = hoy.with(DayOfWeek.MONDAY);

        for (Tarea tarea : calendario.getTareas()) {
            // Convertir Date SQL a LocalDate
            LocalDate fechaTarea = tarea.getFecha().toLocalDate();

            // Calcular el día de la semana (0 = Lunes, 4 = Viernes)
            int diaSemana = fechaTarea.getDayOfWeek().getValue() - 1;

            // Verificar si la tarea está en la semana actual
            if (diaSemana >= 0 && diaSemana < 5) {
                // Convertir la hora de inicio de la tarea a un índice de fila
                int horaInicio = tarea.getHoraInicio().toLocalTime().getHour();
                int horaFin = tarea.getHoraFin() != null ?
                        tarea.getHoraFin().toLocalTime().getHour() :
                        horaInicio + 1; // Si no hay hora fin, asumimos una hora de duración

                // Mapear las horas a índices de fila (9:00 = 1, 10:00 = 2, etc.)
                int filaInicio = horaInicio - 8; // Mapear 9 -> 1, 10 -> 2, etc.
                int filaFin = horaFin - 8;

                // Limitamos al rango visible en el calendario
                filaInicio = Math.max(1, Math.min(6, filaInicio));
                filaFin = Math.max(1, Math.min(6, filaFin));

                // Crear una etiqueta para la tarea
                Label lblTarea = new Label(tarea.getNombre());

                // Aplicar estilo según prioridad
                switch (tarea.getPrioridad()) {
                    case IMPORTANTE -> lblTarea.getStyleClass().add("tarea-importante");
                    case PENDIENTE -> lblTarea.getStyleClass().add("tarea-pendiente");
                    case URGENTE -> lblTarea.getStyleClass().add("tarea-urgente");
                }

                // Añadir etiqueta a la celda de inicio
                int indiceInicio = diaSemana * 10 + filaInicio;
                Pane celdaInicio = celdasCalendario.get(indiceInicio);
                if (celdaInicio != null) {
                    celdaInicio.getChildren().add(lblTarea);
                    celdaInicio.getStyleClass().add("tiene-tarea");

                    // Si la tarea abarca múltiples horas, marcar las celdas adicionales
                    for (int fila = filaInicio + 1; fila <= filaFin; fila++) {
                        int indice = diaSemana * 10 + fila;
                        Pane celda = celdasCalendario.get(indice);
                        if (celda != null) {
                            celda.getStyleClass().add("tiene-tarea-continuacion");
                        }
                    }
                }
            }
        }
    }

    private void actualizarStatusLabel() {
        int totalTareas = calendario.getTareas().size();
        int tareasCompletadas = 0;

        for (Tarea tarea : calendario.getTareas()) {
            if (tarea.isCompletada()) {
                tareasCompletadas++;
            }
        }

        statusLabel.setText(String.format("Tareas: %d completadas de %d", tareasCompletadas, totalTareas));
    }

    public void agregarTareasEjemplo() {
        // Crear algunas tareas de ejemplo
        LocalDate hoy = LocalDate.now();

        Tarea tarea1 = new Tarea();
        tarea1.setNombre("Reunión de equipo");
        tarea1.setDescripcion("Discutir avances del proyecto");
        tarea1.setFecha(Date.valueOf(hoy));
        tarea1.setHoraInicio(Time.valueOf("10:00:00"));
        tarea1.setHoraFin(Time.valueOf("11:00:00"));
        tarea1.setPrioridad(TipoPrioridad.IMPORTANTE);

        Tarea tarea2 = new Tarea();
        tarea2.setNombre("Entrega de informe");
        tarea2.setDescripcion("Preparar informe de avance");
        tarea2.setFecha(Date.valueOf(hoy.plusDays(1)));
        tarea2.setHoraInicio(Time.valueOf("14:00:00"));
        tarea2.setHoraFin(Time.valueOf("15:00:00"));
        tarea2.setPrioridad(TipoPrioridad.URGENTE);

        calendario.getTareas().addAll(tarea1, tarea2);
    }

    public void abrirDialogoTarea(Tarea tareaExistente) {
        try {
            // Usando el nombre correcto del archivo FXML según el PDF
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DialogoTarea.fxml"));
            Parent root = loader.load();

            // Usando el nombre correcto del controlador según el PDF
            DialogoTareaController controller = loader.getController();
            controller.setCalendario(calendario);

            if (tareaExistente != null) {
                controller.editarTarea(tareaExistente);
            }

            Stage stage = new Stage();
            stage.setTitle(tareaExistente == null ? "Nueva Tarea" : "Editar Tarea");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error al abrir el diálogo: " + e.getMessage());
        }
    }

    public void editarTarea(Tarea tarea) {
        abrirDialogoTarea(tarea);
    }

    public void eliminarTarea(Tarea tarea) {
        calendario.getTareas().remove(tarea);
    }

    public void marcarComoCompletada(Tarea tarea) {
        tarea.setCompletada(true);
        actualizarCalendario();
        actualizarStatusLabel();
    }
}