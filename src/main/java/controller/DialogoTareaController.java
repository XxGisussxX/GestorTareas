package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Calendario;
import models.Tarea;
import models.TipoPrioridad;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class  DialogoTareaController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> comboHoraInicio;

    @FXML
    private ComboBox<String> comboHoraFin;

    @FXML
    private ComboBox<TipoPrioridad> comboPrioridad;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private Calendario calendario;
    private Tarea tareaActual;
    private boolean esNuevaTarea = true;

    @FXML
    public void initialize() {
        // Inicializar las opciones de horas (9:00 a 18:00)
        String[] horas = new String[10];
        for (int i = 0; i < 10; i++) {
            int hora = i + 9;
            horas[i] = String.format("%02d:00", hora);
        }
        comboHoraInicio.setItems(FXCollections.observableArrayList(horas));
        comboHoraFin.setItems(FXCollections.observableArrayList(horas));

        // Inicializar las opciones de prioridad
        comboPrioridad.setItems(FXCollections.observableArrayList(TipoPrioridad.values()));
        comboPrioridad.setCellFactory(param -> new ListCell<TipoPrioridad>() {
            @Override
            protected void updateItem(TipoPrioridad item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                    switch (item) {
                        case IMPORTANTE -> setStyle("-fx-text-fill: green;");
                        case PENDIENTE -> setStyle("-fx-text-fill: orange;");
                        case URGENTE -> setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        // Configurar los botones
        btnGuardar.setOnAction(event -> guardarTarea());
        btnCancelar.setOnAction(event -> cerrarDialogo());

        // Valores por defecto
        datePicker.setValue(LocalDate.now());
        comboHoraInicio.setValue("09:00");
        comboHoraFin.setValue("10:00");
        comboPrioridad.setValue(TipoPrioridad.PENDIENTE);
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public void editarTarea(Tarea tarea) {
        this.tareaActual = tarea;
        this.esNuevaTarea = false;

        // Cargar los datos de la tarea en los controles
        txtNombre.setText(tarea.getNombre());
        txtDescripcion.setText(tarea.getDescripcion());
        datePicker.setValue(tarea.getFecha().toLocalDate());

        // Formatear las horas para mostrarlas en el combo
        String horaInicio = String.format("%02d:00", tarea.getHoraInicio().toLocalTime().getHour());
        comboHoraInicio.setValue(horaInicio);

        if (tarea.getHoraFin() != null) {
            String horaFin = String.format("%02d:00", tarea.getHoraFin().toLocalTime().getHour());
            comboHoraFin.setValue(horaFin);
        }

        comboPrioridad.setValue(tarea.getPrioridad());
    }

    private void guardarTarea() {
        // Validar los datos
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Error", "El nombre de la tarea no puede estar vacío");
            return;
        }

        if (datePicker.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una fecha");
            return;
        }

        // Crear o actualizar la tarea
        if (esNuevaTarea) {
            tareaActual = new Tarea();
        }

        // Actualizar los datos de la tarea
        tareaActual.setNombre(txtNombre.getText().trim());
        tareaActual.setDescripcion(txtDescripcion.getText().trim());
        tareaActual.setFecha(Date.valueOf(datePicker.getValue()));

        // Parsear la hora de inicio
        String horaInicioStr = comboHoraInicio.getValue();
        int horaInicio = Integer.parseInt(horaInicioStr.split(":")[0]);
        tareaActual.setHoraInicio(Time.valueOf(LocalTime.of(horaInicio, 0, 0)));

        // Parsear la hora de fin
        String horaFinStr = comboHoraFin.getValue();
        int horaFin = Integer.parseInt(horaFinStr.split(":")[0]);
        tareaActual.setHoraFin(Time.valueOf(LocalTime.of(horaFin, 0, 0)));

        tareaActual.setPrioridad(comboPrioridad.getValue());

        // Guardar la tarea en el calendario
        if (esNuevaTarea) {
            calendario.getTareas().add(tareaActual);
        } else {
            // La tarea ya existe en la lista, solo se actualizaron sus propiedades
            // Notificar cambios para actualizar la vista
            int indice = calendario.getTareas().indexOf(tareaActual);
            calendario.getTareas().set(indice, tareaActual);
        }

        // Cerrar el diálogo
        cerrarDialogo();
    }

    private void cerrarDialogo() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}