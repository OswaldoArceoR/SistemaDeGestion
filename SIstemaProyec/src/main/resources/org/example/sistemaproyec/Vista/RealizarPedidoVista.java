package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.java.org.example.sistemaproyec.Controlador.HistorialVentasControlador;
import main.java.org.example.sistemaproyec.Controlador.MainControlador;
import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Utilidades.DescuentoPromocionUtil;
import main.java.org.example.sistemaproyec.Utilidades.ReciboUtil;

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
    @FXML
    private Label totalLabel;

    private ObservableList<Producto> productosDisponibles;
    private ObservableList<String> productosEnCompra;
    private HistorialVentasControlador historialVentasController;
    private Cliente clienteSeleccionado;

    public RealizarPedidoVista() {
        productosDisponibles = FXCollections.observableArrayList();
        productosEnCompra = FXCollections.observableArrayList();
        historialVentasController = new HistorialVentasControlador(); // Inicializar el controlador
    }

    @FXML
    public void initialize() {
        cargarProductosDisponibles();
        productosDisponiblesListView.setItems(productosDisponibles);
        productosEnCompraListView.setItems(productosEnCompra);
        barraBusqueda.setOnKeyReleased(this::filtrarProductos);
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

        actualizarTotalPedido();
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
            actualizarTotalPedido();
        }
    }

    @FXML
    public void realizarPedido() {
        if (productosEnCompra.isEmpty()) {
            mostrarAlerta("No hay productos", "Debes agregar productos a la compra antes de realizar el pedido.");
            return;
        }

        if (clienteSeleccionado == null) {
            mostrarAlerta("Cliente no seleccionado", "Debes seleccionar un cliente antes de realizar el pedido.");
            return;
        }

        List<Producto> productosVendidos = new ArrayList<>();
        double total = 0.0;

        for (String item : productosEnCompra) {
            String[] partes = item.split(" \\| ");
            String nombreProducto = partes[0].replace("Producto: ", "").trim();
            int cantidad = Integer.parseInt(partes[1].replace("Cantidad: ", "").trim());
            Producto producto = buscarProductoPorNombre(nombreProducto);
            if (producto != null) {
                double precioUnitario = producto.getPrecio();  // Obtener el precio unitario
                productosVendidos.add(new Producto(producto.getNombre(), producto.getDescripcion(), producto.getClasificacion(), precioUnitario, cantidad));

                // Depuración: Imprimir cantidad y precio unitario
                System.out.println("Nombre: " + nombreProducto + ", Cantidad: " + cantidad + ", Precio unitario: " + precioUnitario);

                total += cantidad * precioUnitario;  // Multiplica correctamente cantidad y precio unitario
            }
        }

        total = DescuentoPromocionUtil.calcularTotalConDescuentos(total, clienteSeleccionado);

        Venta venta = new Venta(LocalDate.now(), productosVendidos, total, clienteSeleccionado);
        historialVentasController.registrarVenta(venta);

        ReciboUtil.generarReciboTXT(venta, productosVendidos);//Pasar productosVendidos para incluir cantidades en el recibo

        // Registrar venta en el historial_ventas.txt
        registrarVentaEnHistorial(venta);

        productosEnCompra.clear();
        actualizarTotalPedido();
        actualizarExistencias(productosVendidos);
    }

    public void registrarVentaEnHistorial(Venta venta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historial_ventas.txt", true))) {
            writer.write(venta.getFecha() + "," + venta.getCliente().getNombre() + "," + venta.getTotal());
            writer.newLine();

            for (Producto producto : venta.getProductosVendidos()) {
                writer.write(producto.getNombre() + "," + producto.getCantidadDisponible() + "," + producto.getPrecio());
                writer.newLine();
            }

            writer.write("---"); // Separador para cada venta.
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } catch (IOException e) {
            mostrarAlerta("Error al cargar productos", "No se pudo cargar los productos desde el archivo.");
        }
    }

    private void actualizarTotalPedido() {
        double total = 0.0;

        for (String item : productosEnCompra) {
            String[] partes = item.split(" \\| ");
            double precio = Double.parseDouble(partes[2].replace("Precio: $", "").trim());
            total += precio;
        }

        totalLabel.setText("Total: $" + String.format("%.2f", total));
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

    private Producto buscarProductoPorNombre(String nombre) {
        for (Producto producto : productosDisponibles) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void filtrarProductos(KeyEvent event) {
        String filtro = barraBusqueda.getText().toLowerCase();
        productosDisponiblesListView.setItems(productosDisponibles.filtered(producto ->
                producto.getNombre().toLowerCase().contains(filtro) ||
                        producto.getDescripcion().toLowerCase().contains(filtro) ||
                        producto.getClasificacion().toLowerCase().contains(filtro)));
    }

    @FXML
    public void abrirSeleccionCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/SeleccionarClienteVista.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Seleccionar Cliente");
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait(); // Espera hasta que se cierre la ventana de selección.

            Cliente clienteActual = MainControlador.getClienteActual();
            if (clienteActual != null) {
                mostrarAlerta("Cliente seleccionado", "Cliente actual: " + clienteActual.getNombre());
                clienteSeleccionado = clienteActual; // Actualizar cliente seleccionado
            }
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de selección de clientes.");
        }
    }
}
