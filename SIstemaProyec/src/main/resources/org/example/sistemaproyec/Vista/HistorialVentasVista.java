package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.Venta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistorialVentasVista {

    @FXML
    private TableView<Venta> tablaVentas;
    @FXML
    private TableColumn<Venta, String> colFecha;
    @FXML
    private TableColumn<Venta, String> colCliente;
    @FXML
    private TableColumn<Venta, Double> colTotal;
    @FXML
    private TextField campoCliente;
    @FXML
    private Button buscarButton;

    private ObservableList<Venta> ventas;

    public HistorialVentasVista() {
        ventas = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

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
            Venta ventaActual = null;
            List<Producto> productos = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("---")) {
                    if (ventaActual != null && ventaActual.getNombreCliente().contains(cliente)) {
                        ventaActual.setProductosVendidos(productos);
                        ventas.add(ventaActual);
                        productos = new ArrayList<>();
                        ventaActual = null;
                    }
                } else if (ventaActual == null) {
                    String[] datosVenta = line.split(",");
                    if (datosVenta.length == 3) {
                        ventaActual = new Venta(datosVenta[0].trim(), datosVenta[1].trim(), Double.parseDouble(datosVenta[2].trim()));
                    } else {
                        mostrarAlerta("Formato de venta incorrecto.");
                        return;
                    }
                } else {
                    String[] datosProducto = line.split(",");
                    if (datosProducto.length == 3) {
                        Producto producto = new Producto(
                                datosProducto[0].trim(),
                                Integer.parseInt(datosProducto[1].trim()),
                                Double.parseDouble(datosProducto[2].trim())
                        );
                        productos.add(producto);
                    } else {
                        mostrarAlerta("Formato de producto incorrecto.");
                        return;
                    }
                }
            }

            if (ventaActual != null && ventaActual.getNombreCliente().contains(cliente)) {
                ventaActual.setProductosVendidos(productos);
                ventas.add(ventaActual);
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
        ventas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("historial_ventas.txt"))) {
            String linea;
            Venta ventaActual = null;
            List<Producto> productos = new ArrayList<>();

            while ((linea = reader.readLine()) != null) {
                System.out.println("Línea leída: " + linea);

                if (linea.equals("---")) {
                    if (ventaActual != null) {
                        ventaActual.setProductosVendidos(productos);
                        ventas.add(ventaActual);
                        productos = new ArrayList<>();
                        ventaActual = null;
                    }
                } else if (ventaActual == null) {
                    String[] datosVenta = linea.split(",");
                    if (datosVenta.length == 3) {
                        ventaActual = new Venta(datosVenta[0].trim(), datosVenta[1].trim(), Double.parseDouble(datosVenta[2].trim()));
                    } else {
                        mostrarAlerta("Formato de venta incorrecto.");
                        return;
                    }
                } else {
                    String[] datosProducto = linea.split(",");
                    if (datosProducto.length == 3) {
                        Producto producto = new Producto(
                                datosProducto[0].trim(),
                                Integer.parseInt(datosProducto[1].trim()),
                                Double.parseDouble(datosProducto[2].trim())
                        );
                        productos.add(producto);
                    } else {
                        mostrarAlerta("Formato de producto incorrecto.");
                        return;
                    }
                }
            }

            if (ventaActual != null) {
                ventaActual.setProductosVendidos(productos);
                ventas.add(ventaActual);
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
