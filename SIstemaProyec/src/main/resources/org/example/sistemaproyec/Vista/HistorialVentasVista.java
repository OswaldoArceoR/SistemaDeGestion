package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.resources.org.example.sistemaproyec.Vista.HistorialVentasVista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HistorialVentasVista {

    @FXML
    private ListView<String> ventasListView;
    @FXML
    private TextField clienteField;
    @FXML
    private Button buscarButton;

    private ObservableList<String> ventas;

    public HistorialVentasVista() {
        ventas = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        ventasListView.setItems(ventas);
    }

    @FXML
    private void buscarVentasPorClientee() {
        String cliente = clienteField.getText().trim();
        if (cliente.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa el nombre del cliente.");
            return;
        }

        cargarVentasPorCliente(cliente);
    }

    private void cargarVentasPorCliente(String cliente) {
        ventas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/jenrr/Documents/tareasJAVA/SistemaDeGestion/historial_ventas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(cliente)) {
                    ventas.add(line);
                }
            }
            if (ventas.isEmpty()) {
                mostrarAlerta("Sin resultados", "No se encontraron ventas para el cliente: " + cliente);
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el historial de ventas.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
