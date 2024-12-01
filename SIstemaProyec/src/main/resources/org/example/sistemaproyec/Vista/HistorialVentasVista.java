package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HistorialVentasVista {

    @FXML
    private TableView<String> tablaVentas;
    @FXML
    private TableColumn<String, String> colFecha;
    @FXML
    private TableColumn<String, String> colCliente;
    @FXML
    private TableColumn<String, String> colTotal;
    @FXML
    private TextField campoCliente;
    @FXML
    private Button buscarButton;

    private ObservableList<String> ventas;

    public HistorialVentasVista() {
        ventas = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        cargarTodasLasVentas();
        tablaVentas.setItems(ventas);
    }

    @FXML
    private void buscarVentasPorCliente() {
        String cliente = campoCliente.getText().trim();
        if (cliente.isEmpty()) {
            mostrarAlerta("Error", "Por favor, ingresa el nombre del cliente.");
            return;
        }

        cargarVentasPorCliente(cliente);
    }

    private void cargarVentasPorCliente(String cliente) {
        ventas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("historial_ventas.txt"))) {
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

    public void cargarTodasLasVentas() {
        ventas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("historial_ventas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(";");
                String ventaTexto = "Fecha: " + partes[0] + ", Cliente: " + partes[1] + ", Total: $" + partes[2] + ", Productos: " + partes[3];
                ventas.add(ventaTexto);
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el historial de ventas.");
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
