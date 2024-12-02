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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
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
            mostrarAlerta("Error."," Por favor, ingresa el nombre del cliente.");
            return;
        }

        cargarVentasPorCliente(cliente);
    }

    private void cargarVentasPorCliente(String cliente) {
        ventas.clear();
        cargarVentas((venta) -> venta.getNombreCliente().contains(cliente));
    }

    public void cargarTodasLasVentas() {
        ventas.clear();
        cargarVentas(null);
    }

    public List<Venta> obtenerVentasEntreFechas(LocalDate inicio, LocalDate fin) {
        List<Venta> ventasFiltradas = new ArrayList<>();
        for (Venta venta : ventas) {
            if ((venta.getFecha().isEqual(inicio) || venta.getFecha().isAfter(inicio)) &&
                    (venta.getFecha().isEqual(fin) || venta.getFecha().isBefore(fin))) {
                ventasFiltradas.add(venta);
            }
        }
        return ventasFiltradas;
    }

    public double obtenerTotalVentasEntreFechas(LocalDate inicio, LocalDate fin) {
        double total = 0;
        List<Venta> ventasFiltradas = obtenerVentasEntreFechas(inicio, fin);
        System.out.println("Ventas filtradas entre " + inicio + " y " + fin + ":");
        for (Venta venta : ventasFiltradas) {
            System.out.println("Venta: " + venta + ", Total: " + venta.getTotal());
            total += venta.getTotal();
        }
        System.out.println("Total calculado: $" + total);
        return total;
    }

    private void cargarVentas(FiltroVentas filtro) {
        File archivoVentas = new File("historial_ventas.txt");
        if (!archivoVentas.exists()) {
            mostrarAlerta("Error", "El archivo historial_ventas.txt no existe.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivoVentas))) {
            String linea;
            Venta ventaActual = null;
            List<Producto> productos = new ArrayList<>();
            boolean nuevaVenta = false;

            while ((linea = reader.readLine()) != null) {
                if (linea.equals("---")) {
                    if (ventaActual != null) {
                        ventaActual.setProductosVendidos(productos);
                        if (filtro == null || filtro.aplica(ventaActual)) {
                            ventas.add(ventaActual);
                        }
                        productos = new ArrayList<>();
                        ventaActual = null;
                    }
                    nuevaVenta = false;
                } else if (!nuevaVenta) {
                    String[] datosVenta = linea.split(",");
                    if (datosVenta.length == 3) {
                        String fecha = datosVenta[0].trim();
                        String nombre = datosVenta[1].trim();
                        double total = Double.parseDouble(datosVenta[2].trim());

                        ventaActual = new Venta(fecha, nombre, total);
                        nuevaVenta = true; // Marca que estamos leyendo una nueva venta
                    } else {
                        mostrarAlerta("Error","Formato de venta incorrecto.");
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
                        mostrarAlerta("Error","Formato de producto incorrecto.");
                        return;
                    }
                }
            }

            if (ventaActual != null) {
                ventaActual.setProductosVendidos(productos);
                if (filtro == null || filtro.aplica(ventaActual)) {
                    ventas.add(ventaActual);
                }
            }

        } catch (IOException e) {
            mostrarAlerta("Error"," al cargar el historial de ventas: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error","Formato de número incorrecto en el archivo.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FunctionalInterface
    interface FiltroVentas {
        boolean aplica(Venta venta);
    }
}
