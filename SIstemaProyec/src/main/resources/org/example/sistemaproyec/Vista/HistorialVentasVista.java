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
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            mostrarAlerta("Error. Por favor, ingresa el nombre del cliente.");
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
                mostrarAlerta("Sin resultados. No se encontraron ventas para el cliente: " + cliente);
            }
        } catch (IOException e) {
            mostrarAlerta("Error. No se pudo cargar el historial de ventas.");
            e.printStackTrace();
        }
    }

    public void cargarTodasLasVentas() {
        ventas.clear();  // Limpia la lista de ventas antes de cargar nuevas.
        try (BufferedReader reader = new BufferedReader(new FileReader("historial_ventas.txt"))) {
            String linea;
            Venta ventaActual = null;
            List<Producto> productos = new ArrayList<>();

            while ((linea = reader.readLine()) != null) {
                System.out.println("Línea leída: " + linea);  // Agrega para depuración.

                if (linea.equals("---")) {
                    // Guardamos la venta si ya existe.
                    if (ventaActual != null) {
                        ventaActual.setProductosVendidos(productos);  // Asocia los productos a la venta.
                        ventas.add(String.valueOf(ventaActual));
                        productos = new ArrayList<>();  // Reinicia la lista de productos.
                        ventaActual = null;  // Reinicia la venta para la siguiente.
                    }
                } else if (ventaActual == null) {
                    // Procesamos la primera línea de la venta.
                    String[] datosVenta = linea.split(",");
                    if (datosVenta.length == 3) {
                        ventaActual = new Venta(datosVenta[0], datosVenta[1], Double.parseDouble(datosVenta[2]));
                    } else {
                        mostrarAlerta("Formato de venta incorrecto.");
                        return;  // Detenemos la carga si el formato es incorrecto.
                    }
                } else {
                    // Procesamos los productos de una venta.
                    String[] datosProducto = linea.split(",");
                    if (datosProducto.length == 3) {
                        Producto producto = new Producto(
                                datosProducto[0],
                                Integer.parseInt(datosProducto[1]),
                                Double.parseDouble(datosProducto[2])
                        );
                        productos.add(producto);
                    } else {
                        mostrarAlerta("Formato de producto incorrecto.");
                        return;  // Detenemos la carga si el formato del producto es incorrecto.
                    }
                }
            }

            // Agregar la última venta si no termina con '---'
            if (ventaActual != null) {
                ventaActual.setProductosVendidos(productos);
                ventas.add(String.valueOf(ventaActual));
            }

        } catch (IOException e) {
            mostrarAlerta("Error al cargar el historial de ventas: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato de número incorrecto en el archivo.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
