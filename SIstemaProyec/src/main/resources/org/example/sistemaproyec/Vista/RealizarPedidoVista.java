package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RealizarPedidoVista {

    @FXML
    private TextField clienteTextField;

    @FXML
    protected void agregarPedido() {
        String cliente = clienteTextField.getText();
        if (cliente == null || cliente.isEmpty()) {
            System.out.println("El cliente no puede estar vacío.");
        } else {
            System.out.println("Pedido agregado para el cliente: " + cliente);
            // Aquí puedes integrar con el controlador de pedidos
        }
    }
}
