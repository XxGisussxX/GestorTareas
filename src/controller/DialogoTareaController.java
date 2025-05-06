package controller;

import com.gestortareas.model.Calendario;
import com.gestortareas.model.Tarea;
import com.gestortareas.model.TipoPrioridad;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogoTareaController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextArea txtDescripcion;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private ComboBox<Integer> comboHoraInicio;
    
    @FXML
    private ComboBox<Integer> comboMinutoInicio;
    
    @FXML
    private ComboBox<Integer> comboHoraFin;
    
    @FXML
    private ComboBox<Integer> comboMinutoFin;
    
    @FXML
    private ComboBox<TipoPrioridad> comboPrioridad;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private Button btnGuardar;
    
    private Calendario calendario;
    private Tarea tareaParaEditar;
    
    @FXML
    public void initialize() {
        // Inicializar ComboBox de horas (0-23)
        ObservableList<Integer> horas = FXCollections.observableArrayList(
                IntStream.rangeClosed(0, 23).boxed().toList()
        );
        comboHoraInicio.setItems(horas);
        comboHoraFin.setItems(horas);
        
        // Inicializar ComboBox de minutos (0, 15, 30, 45)
        ObservableList<Integer> minutos = FXCollections.observableArrayList(0, 15, 30, 45);
        comboMinutoInicio.setItems(minutos);
        comboMinutoFin.setItems(minutos);
        
        // Inicializar ComboBox de prioridades
        comboPrioridad.setItems(FXCollections.observableArrayList(TipoPrioridad.values()));
        comboPrioridad.setCellFactory(lv -> new javafx.scene.control.ListCell<TipoPrioridad>() {
            @Override
            protected void updateItem(TipoPrioridad item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDescripcion());
                }
            }
        });
        
        comboPrioridad.setButtonCell(new javafx.scene.control.ListCell<TipoPrioridad>() {
            @Override
            protected void updateItem(TipoPrioridad item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDescripcion());
                }
            }
        });
        
        // Valores por defecto
        datePicker.setValue(LocalDate.now());
        comboHoraInicio.setValue(9);
        comboMinutoInicio.setValue(0);
        comboHoraFin.setValue(10);
        comboMinutoFin.setValue(0);
        comboPrioridad.setValue(TipoPrioridad.PENDIENTE);
    }
    
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
    
    public void setTareaParaEditar(Tarea tarea) {
        this.tareaParaEditar = tarea;
        
        // Cargar datos de la tarea en el formulario
        txtNombre.setText(tarea.getNombre());
        txtDescripcion.setText(tarea.getDescripcion());
        datePicker.setValue(tarea.getFecha().toLocalDate());
        
        // Hora de inicio
        LocalTime horaInicio = tarea.getHoraInicio().toLocalTime();
        comboHoraInicio.setValue(horaInicio.getHour());
        comboMinutoInicio.setValue(horaInicio.getMinute() - (horaInicio.getMinute() % 15)); // Redondear a múltiplos de 15
        
        // Hora de fin
        LocalTime horaFin = tarea.getHoraFin().toLocalTime();
        comboHoraFin.setValue(horaFin.getHour());
        comboMinutoFin.setValue(horaFin.getMinute() - (horaFin.getMinute() % 15)); // Redondear a múltiplos de 15
        
        // Prioridad
        comboPrioridad.setValue(tarea.getPrioridad());
    }
    
    @FXML
    private void handleCancelar() {
        cerrarDialogo();
    }
    
    @FXML
    private void handleGuardar() {
        // Validar campos
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarMensajeError("El nombre de la tarea es obligatorio");
            return;
        }
        
        // Crear objeto de fecha y hora
        LocalDate fecha = datePicker.getValue();
        LocalTime horaInicio = LocalTime.of(comboHoraInicio.getValue(), comboMinutoInicio.getValue());
        LocalTime horaFin = LocalTime.of(comboHoraFin.getValue(), comboMinutoFin.getValue());
        
        // Validar que la hora de fin sea posterior a la de inicio
        if (horaFin.isBefore(horaInicio) || horaFin.equals(horaInicio)) {
            mostrarMensajeError("La hora de fin debe ser posterior a la hora de inicio");
            return;
        }
        
        // Crear o actualizar tarea
        if (tareaParaEditar == null) {
            // Crear nueva tarea
            Tarea nuevaTarea = new Tarea(
                txtNombre.getText().trim(),
                txtDescripcion.getText().trim(),
                Date.valueOf(fecha),
                Time.valueOf(horaInicio),
                Time.valueOf(horaFin),
                comboPrioridad.getValue()
            );
            calendario.agregarTarea(nuevaTarea);
        } else {
            // Actualizar tarea existente
            tareaParaEditar.setNombre(txtNombre.getText().trim());
            tareaParaEditar.setDescripcion(txtDescripcion.getText().trim());
            tareaParaEditar.setFecha(Date.valueOf(fecha));
            tareaParaEditar.setHoraInicio(Time.valueOf(horaInicio));
            tareaParaEditar.setHoraFin(Time.valueOf(horaFin));
            tareaParaEditar.setPrioridad(comboPrioridad.getValue());
        }
        
        cerrarDialogo();
    }
    
    private void mostrarMensajeError(String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void cerrarDialogo() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}