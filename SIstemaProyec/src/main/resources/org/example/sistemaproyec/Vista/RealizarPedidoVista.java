package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoProductoUtil;
import java.time.LocalDate;
import main.java.org.example.sistemaproyec.Controlador.HistorialVentasControlador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    private HistorialVentasControlador historialVentasController;
    private Cliente clienteSeleccionado;

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

        System.out.println("Inicialización completa");
    }

    private void cargarProductosDisponibles() {
        // Usar ruta relativa simplificada y BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0];
                String descripcion = datos[1];
                String clasificacion = datos[2];
                double precio = Double.parseDouble(datos[3]);
                int cantidadDisponible = Integer.parseInt(datos[4]);

                Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidadDisponible);
                productosDisponibles.add(producto);
            }
            System.out.println("Productos cargados: " + productosDisponibles.size());
        } catch (IOException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
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

        // Reducir la cantidad disponible en productosDisponibles
        productoSeleccionado.setCantidadDisponible(productoSeleccionado.getCantidadDisponible() - cantidad);

        // Agregar el producto con la cantidad seleccionada a la lista de compras
        String productoCompra = "Producto: " + productoSeleccionado.getNombre() + " | Cantidad: " + cantidad + " | Precio: $" + (productoSeleccionado.getPrecio() * cantidad);
        productosEnCompra.add(productoCompra);

        // Limpiar el campo de cantidad
        cantidadField.clear();

        // Comprobar si el stock está bajo
        verificarStockBajo(productoSeleccionado);

        // Actualizar el archivo con el nuevo stock
        guardarProductosEnArchivo();
    }

    // Método para verificar si el stock de un producto está bajo
    private void verificarStockBajo(Producto producto) {
        int umbralStockBajo = 10; // Definir el umbral de stock bajo (ejemplo: 10 unidades)

        if (producto.getCantidadDisponible() <= umbralStockBajo) {
            mostrarAlerta("Stock Bajo", "El producto " + producto.getNombre() + " tiene un nivel de stock bajo. Quedan solo "
                    + producto.getCantidadDisponible() + " unidades.");
        }
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

        List<Producto> productosVendidos = new ArrayList<>();
        for (String item : productosEnCompra) {
            String nombreProducto = item.split(" \\| ")[0].replace("Producto: ", "").trim();
            Producto producto = buscarProductoPorNombre(nombreProducto);
            if (producto != null) {
                productosVendidos.add(producto);
            }
        }

        double total = productosVendidos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidadDisponible())
                .sum();

        Venta venta = new Venta(LocalDate.now(), productosVendidos, total, clienteSeleccionado);
        historialVentasController.registrarVenta(venta);

        mostrarAlerta("Pedido Realizado", "¡El pedido ha sido realizado con éxito!");

        productosEnCompra.clear();

        // Actualizar las existencias de los productos luego de realizar el pedido
        actualizarExistencias(productosVendidos);
    }

    // Actualizar las existencias en el archivo
    private void actualizarExistencias(List<Producto> productosVendidos) {
        for (Producto productoVendido : productosVendidos) {
            // Reducir el stock disponible en el inventario
            Producto productoEnInventario = buscarProductoPorNombre(productoVendido.getNombre());
            if (productoEnInventario != null) {
                int cantidadVendida = (int) productosVendidos.stream()
                        .filter(p -> p.getNombre().equals(productoVendido.getNombre()))
                        .count();
                productoEnInventario.setCantidadDisponible(productoEnInventario.getCantidadDisponible() - cantidadVendida);
            }
        }
        // Guardar los productos actualizados
        guardarProductosEnArchivo();
    }

    // Método para guardar los productos en el archivo
    private void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productosDisponibles) {
                String linea = producto.getNombre() + "," + producto.getDescripcion() + ","
                        + producto.getClasificacion() + "," + producto.getPrecio() + ","
                        + producto.getCantidadDisponible();
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto producto : productosDisponibles) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null; // Si no se encuentra el producto
    }
}
