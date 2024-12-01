package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import main.java.org.example.sistemaproyec.Controlador.MainControlador;
import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoClienteUtil;

import java.io.IOException;
import java.util.List;

public class SeleccionarClienteVista {

    @FXML
    private ListView<Cliente> clientesListView;
    @FXML
    private Button seleccionarButton;

    private ObservableList<Cliente> clientes;

    public SeleccionarClienteVista() {
        clientes = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        cargarClientes();
        clientesListView.setItems(clientes);
    }

    private void cargarClientes() {
        try {
            List<Cliente> clientesCargados = ArchivoClienteUtil.cargarClientes();
            clientes.addAll(clientesCargados);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudieron cargar los clientes.");
        }
    }

    @FXML
    public void seleccionarCliente() {
        Cliente clienteSeleccionado = clientesListView.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            MainControlador.setClienteActual(clienteSeleccionado);
            cerrarVentana();
        } else {
            mostrarAlerta("Selección requerida", "Debe seleccionar un cliente.");
        }
    }

    private void cerrarVentana() {
        Stage stage = (Stage) seleccionarButton.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
