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

    public RealizarPedidoVista (){
        productosDisponibles = FXCollections.observableArrayList();
        productosEnCompra = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Cargar productos disponibles (puedes reemplazar esto con tu base de datos o modelo)
        cargarProductosDisponibles();

        // Vincular las listas con las vistas
        productosDisponiblesListView.setItems(productosDisponibles);
        productosEnCompraListView.setItems(productosEnCompra);
    }

    private void cargarProductosDisponibles() {
        // Ejemplo de productos disponibles (esto debe ser dinámico desde tu base de datos o modelo)
        productosDisponibles.add(new Producto("Producto 1", "Descripción 1", "Clasificación A", 100.0, 10));
        productosDisponibles.add(new Producto("Producto 2", "Descripción 2", "Clasificación B", 200.0, 5));
        productosDisponibles.add(new Producto("Producto 3", "Descripción 3", "Clasificación C", 150.0, 8));
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

        // Aquí procesarías la creación del pedido
        System.out.println("Pedido realizado con éxito!");

        // Limpiar la lista de compras después de realizar el pedido
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
