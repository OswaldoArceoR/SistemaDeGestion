package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Utilidades.ArchivoProductoUtil;
import main.java.org.example.sistemaproyec.Controlador.HistorialVentasControlador;
import main.java.org.example.sistemaproyec.Utilidades.DescuentoPromocionUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RealizarPedidoVista {

    @FXML
    private ListView<Producto> productosDisponiblesListView;
    @FXML
    private ListView<String> productosEnCompraListView;
    @FXML
    private TextField barraBusqueda;
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

        // Agregar evento para búsqueda
        barraBusqueda.setOnKeyReleased(this::filtrarProductos);

        System.out.println("Inicialización completa");
    }

    private void cargarProductosDisponibles() {
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

        productoSeleccionado.setCantidadDisponible(productoSeleccionado.getCantidadDisponible() - cantidad);

        String productoCompra = "Producto: " + productoSeleccionado.getNombre() + " | Cantidad: " + cantidad + " | Precio: $" + (productoSeleccionado.getPrecio() * cantidad);
        productosEnCompra.add(productoCompra);

        productosDisponiblesListView.refresh();
        cantidadField.clear();
    }

    private void verificarStockBajo(Producto producto) {
        int umbralStockBajo = 10;

        if (producto.getCantidadDisponible() <= umbralStockBajo) {
            mostrarAlerta("Stock Bajo", "El producto " + producto.getNombre() + " tiene un nivel de stock bajo. Quedan solo "
                    + producto.getCantidadDisponible() + " unidades.");
        }
    }

    @FXML
    public void quitarDeCompra() {
        String productoCompra = productosEnCompraListView.getSelectionModel().getSelectedItem();
        if (productoCompra != null) {
            String nombreProducto = productoCompra.split(" \\| ")[0].replace("Producto: ", "").trim();
            int cantidad = Integer.parseInt(productoCompra.split(" \\| ")[1].replace("Cantidad: ", "").trim());

            Producto producto = buscarProductoPorNombre(nombreProducto);
            if (producto != null) {
                producto.setCantidadDisponible(producto.getCantidadDisponible() + cantidad);
            }

            productosEnCompra.remove(productoCompra);
            productosDisponiblesListView.refresh();
        }
    }

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

        total = DescuentoPromocionUtil.calcularTotalConDescuentos(total, clienteSeleccionado);

        Venta venta = new Venta(LocalDate.now(), productosVendidos, total, clienteSeleccionado);
        historialVentasController.registrarVenta(venta);

        mostrarAlerta("Pedido Realizado", "¡El pedido ha sido realizado con éxito!");

        productosEnCompra.clear();

        actualizarExistencias(productosVendidos);
    }

    private void actualizarExistencias(List<Producto> productosVendidos) {
        for (Producto productoVendido : productosVendidos) {
            Producto productoEnInventario = buscarProductoPorNombre(productoVendido.getNombre());
            if (productoEnInventario != null) {
                int cantidadVendida = (int) productosVendidos.stream()
                        .filter(p -> p.getNombre().equals(productoVendido.getNombre()))
                        .count();
                productoEnInventario.setCantidadDisponible(productoEnInventario.getCantidadDisponible() - cantidadVendida);
            }
        }
        guardarProductosEnArchivo();
    }

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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
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
        return null;
    }

    @FXML
    private void filtrarProductos(KeyEvent event) {
        String filtro = barraBusqueda.getText().toLowerCase();
        productosDisponiblesListView.setItems(productosDisponibles.filtered(producto ->
                producto.getNombre().toLowerCase().contains(filtro) ||
                        producto.getDescripcion().toLowerCase().contains(filtro) ||
                        producto.getClasificacion().toLowerCase().contains(filtro)));
    }
}
