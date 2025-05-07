package controller;

import models.Tarea;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TareaCellController {
    
    @FXML
    private Label nombreTarea;
    
    @FXML
    private Button btnEditar;
    
    @FXML
    private Button btnEliminar;
    
    private Tarea tarea;
    private MainController mainController;
    
    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
        nombreTarea.setText(tarea.getNombre());
        
        // Si la tarea está completada, aplicar estilo
        if (tarea.isCompletada()) {
            nombreTarea.getStyleClass().add("tarea-completada");
        }
    }
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private void handleEditar() {
        if (mainController != null) {
            mainController.abrirDialogoTarea(tarea);
        }
    }
    
    @FXML
    private void handleEliminar() {
        // Confirmar eliminación
        javafx.scene.control.Alert confirmacion = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.CONFIRMATION,
            "¿Estás seguro de que deseas eliminar esta tarea?",
            javafx.scene.control.ButtonType.YES,
            javafx.scene.control.ButtonType.NO
        );
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar tarea: " + tarea.getNombre());
        
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == javafx.scene.control.ButtonType.YES && mainController != null) {
                mainController.eliminarTarea(tarea);
            }
        });
    }
}