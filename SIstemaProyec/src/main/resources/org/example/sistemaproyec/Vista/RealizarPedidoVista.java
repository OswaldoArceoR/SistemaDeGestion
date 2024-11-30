package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.PedidosException;
import main.java.org.example.sistemaproyec.Controlador.PedidosControlador;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoProductoUtil;

import java.util.List;

public class RealizarPedidoVista {

    @FXML
    private TextField clienteTextField;

    private PedidosControlador pedidosControlador = new PedidosControlador();
    private List<Producto> productos;

    public RealizarPedidoVista() throws ProductoException {
        // Cargar productos desde el archivo al inicializar la vista
        productos = ArchivoProductoUtil.cargarProductos("/main/resources/org/example/sistemaproyec/Utilidades/productos.txt");
    }

    @FXML
    protected void agregarPedido() {
        String cliente = clienteTextField.getText();

        if (cliente == null || cliente.isEmpty()) {
            System.out.println("El cliente no puede estar vacío.");
        } else {
            try {
                // Aquí deberías agregar la lógica para seleccionar productos y cantidades.
                // Para demostración, creamos una lista vacía de cantidades.
                List<Integer> cantidades = List.of(1, 2);  // Ejemplo de cantidades (deberías obtenerlo del usuario)

                pedidosControlador.realizarPedido(productos, cantidades, cliente);
                System.out.println("Pedido agregado para el cliente: " + cliente);
            } catch (PedidosException | ProductoException e) {
                System.out.println("Error al realizar el pedido: " + e.getMessage());
            }
        }
    }
}
