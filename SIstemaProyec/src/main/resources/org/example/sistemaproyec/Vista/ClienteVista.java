package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoClienteUtil;

import java.io.IOException;

public class ClienteVista {

    @FXML
    private ListView<Cliente> clientesListView;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField direccionTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private TextField emailTextField;

    private ObservableList<Cliente> clientes;

    public ClienteVista() {
        clientes = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Cargar clientes desde el archivo
        cargarClientes();

        // Vincular la lista con la vista
        clientesListView.setItems(clientes);
    }

    private void cargarClientes() {
        try {
            clientes.addAll(ArchivoClienteUtil.cargarClientes("C:/Users/jenrr/Documents/tareasJAVA/SistemaDeGestion/clientes.txt"));
        } catch (IOException e) {
            mostrarAlerta("Error al cargar clientes", "No se pudo cargar los clientes desde el archivo.");
        }
    }

    @FXML
    public void agregarCliente() {
        String nombre = nombreTextField.getText();
        String direccion = direccionTextField.getText();
        String telefono = telefonoTextField.getText();
        String email = emailTextField.getText();

        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Todos los campos deben estar completos.");
            return;
        }

        // Verificar si el cliente es frecuente (por ejemplo, si tiene al menos 3 compras)
        boolean clienteFrecuente = verificarClienteFrecuente(email);

        Cliente cliente = new Cliente(nombre, direccion, telefono, email, clienteFrecuente);
        clientes.add(cliente);

        // Guardar clientes en archivo
        guardarClientes();

        // Limpiar los campos
        nombreTextField.clear();
        direccionTextField.clear();
        telefonoTextField.clear();
        emailTextField.clear();
    }

    private boolean verificarClienteFrecuente(String email) {
        // Lógica simple para determinar si un cliente es frecuente
        // Puede basarse en el historial de compras, el tiempo como cliente, etc.
        return email.endsWith("@cliente.com"); // Ejemplo simple
    }

    private void guardarClientes() {
        try {
            ArchivoClienteUtil.guardarClientes("C:/Users/jenrr/Documents/tareasJAVA/SistemaDeGestion/clientes.txt", clientes);
        } catch (IOException e) {
            mostrarAlerta("Error al guardar clientes", "No se pudo guardar los clientes en el archivo.");
        }
    }

    @FXML
    public void eliminarCliente() {
        Cliente clienteSeleccionado = clientesListView.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clientes.remove(clienteSeleccionado);
            guardarClientes();
        }
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
