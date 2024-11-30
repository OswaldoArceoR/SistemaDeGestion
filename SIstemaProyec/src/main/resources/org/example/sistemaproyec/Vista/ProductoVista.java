package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

import java.io.*;
import java.util.List;

public class ProductoVista {

    @FXML
    private ListView<Producto> productosListView;

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private TextField clasificacionTextField;
    @FXML
    private TextField precioTextField;
    @FXML
    private TextField cantidadTextField;

    private ObservableList<Producto> productos;

    @FXML
    public void initialize() {
        productos = FXCollections.observableArrayList();
        productosListView.setItems(productos);
        cargarProductosDesdeArchivo();
    }

    // Cargar los productos desde el archivo CSV
    private void cargarProductosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                String nombre = datos[0];
                String descripcion = datos[1];
                String clasificacion = datos[2];
                double precio = Double.parseDouble(datos[3]);
                int cantidad = Integer.parseInt(datos[4]);

                Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);
                productos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();  // Manejar errores al leer el archivo
        }
    }

    @FXML
    public void agregarProducto() throws ProductoException {
        String nombre = nombreTextField.getText();
        String descripcion = descripcionTextField.getText();
        String clasificacion = clasificacionTextField.getText();
        double precio = Double.parseDouble(precioTextField.getText());
        int cantidad = Integer.parseInt(cantidadTextField.getText());

        Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);
        productos.add(producto);
        guardarProductosEnArchivo();  // Guardar productos después de agregar uno nuevo
    }

    // Guardar los productos en un archivo CSV
    public void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
                String linea = producto.getNombre() + "," + producto.getDescripcion() + ","
                        + producto.getClasificacion() + "," + producto.getPrecio() + ","
                        + producto.getCantidadDisponible();
                writer.write(linea);
                writer.newLine();  // Escribir una nueva línea después de cada producto
            }
        } catch (IOException e) {
            e.printStackTrace();  // Manejar errores al escribir el archivo
        }
    }

    @FXML
    public void editarProducto() {
        Producto selectedProducto = productosListView.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            selectedProducto.setNombre(nombreTextField.getText());
            selectedProducto.setDescripcion(descripcionTextField.getText());
            selectedProducto.setClasificacion(clasificacionTextField.getText());
            selectedProducto.setPrecio(Double.parseDouble(precioTextField.getText()));
            selectedProducto.setCantidadDisponible(Integer.parseInt(cantidadTextField.getText()));
            productosListView.refresh();
            guardarProductosEnArchivo();  // Guardar los productos después de editar
        }
    }

    @FXML
    public void eliminarProducto() {
        Producto selectedProducto = productosListView.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            productos.remove(selectedProducto);
            guardarProductosEnArchivo();  // Guardar productos después de eliminar
        }
    }
}
