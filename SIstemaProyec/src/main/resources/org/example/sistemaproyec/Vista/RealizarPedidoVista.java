package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoProductoUtil;
import main.java.org.example.sistemaproyec.Controlador.HistorialVentasControlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RealizarPedidoVista {

    @FXML
    private ListView<Producto> productosDisponiblesListView;
    @FXML
    private ListView<String> productosEnCompraListView;
    @FXML
    private TextField cantidadField;
    @FXML
    private Button agregarButton;
    @FXML
    private Button quitarButton;
    @FXML
    private Button realizarPedidoButton;

    private ObservableList<Producto> productosDisponibles;
    private ObservableList<String> productosEnCompra;

    public RealizarPedidoVista() {
        productosDisponibles = FXCollections.observableArrayList();
        productosEnCompra = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Cargar productos desde el archivo
        cargarProductosDisponibles();

        // Vincular las listas con las vistas
        productosDisponiblesListView.setItems(productosDisponibles);
        productosEnCompraListView.setItems(productosEnCompra);
    }

    private void cargarProductosDisponibles() {
        // Cargar los productos desde el archivo .txt
        try {
            List<Producto> productos = ArchivoProductoUtil.cargarProductos("C:/Users/jenrr/Documents/tareasJAVA/SistemaDeGestion/productos.txt");
            productosDisponibles.addAll(productos);
        } catch (IOException e) {
            mostrarAlerta("Error al cargar productos", "No se pudo cargar los productos desde el archivo.");
        }
    }

    // Método para agregar un producto seleccionado a la lista de compras
    @FXML
    public void agregarACompra() {
        Producto productoSeleccionado = productosDisponiblesListView.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            mostrarAlerta("Seleccionar Producto", "Debes seleccionar un producto.");
            return;
        }

        String cantidadTexto = cantidadField.getText();
        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad inválida", "La cantidad debe ser un número válido.");
            return;
        }

        if (cantidad <= 0 || cantidad > productoSeleccionado.getCantidadDisponible()) {
            mostrarAlerta("Cantidad no válida", "La cantidad debe ser mayor que 0 y no superar el stock disponible.");
            return;
        }

        // Agregar el producto con la cantidad seleccionada a la lista de compras
        String productoCompra = "Producto: " + productoSeleccionado.getNombre() + " | Cantidad: " + cantidad + " | Precio: $" + (productoSeleccionado.getPrecio() * cantidad);
        productosEnCompra.add(productoCompra);

        // Limpiar el campo de cantidad
        cantidadField.clear();
    }

    // Método para quitar un producto de la lista de compras
    @FXML
    public void quitarDeCompra() {
        String productoSeleccionado = productosEnCompraListView.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            productosEnCompra.remove(productoSeleccionado);
        }
    }

    // Método para realizar el pedido
    @FXML
    public void realizarPedido() {
        if (productosEnCompra.isEmpty()) {
            mostrarAlerta("No hay productos", "Debes agregar productos a la compra antes de realizar el pedido.");
            return;
        }

        mostrarAlerta("Pedido Realizado", "¡El pedido ha sido realizado con éxito!");
        productosEnCompra.clear();
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
