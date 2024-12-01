package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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

        // Configurar la visualización personalizada de las celdas del ListView
        productosListView.setCellFactory(new Callback<ListView<Producto>, ListCell<Producto>>() {
            @Override
            public ListCell<Producto> call(ListView<Producto> listView) {
                return new ListCell<Producto>() {
                    @Override
                    protected void updateItem(Producto producto, boolean empty) {
                        super.updateItem(producto, empty);
                        if (empty || producto == null) {
                            setText(null);
                        } else {
                            setText(String.format("Nombre: %s | Descripción: %s | Clasificación: %s | Precio: %.2f | Cantidad: %d",
                                    producto.getNombre(), producto.getDescripcion(), producto.getClasificacion(),
                                    producto.getPrecio(), producto.getCantidadDisponible()));
                            setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-border-color: #000000;");
                        }
                    }
                };
            }
        });

        // Agregar manejador de eventos para seleccionar un producto
        productosListView.setOnMouseClicked(this::mostrarProductoSeleccionado);
    }

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
            e.printStackTrace();
        }
    }

    @FXML
    public void agregarProducto() {
        try {
            if (nombreTextField.getText().isEmpty() || descripcionTextField.getText().isEmpty() ||
                    clasificacionTextField.getText().isEmpty() || precioTextField.getText().isEmpty() ||
                    cantidadTextField.getText().isEmpty()) {
                throw new ProductoException("Todos los campos deben ser completados.");
            }

            String nombre = nombreTextField.getText();
            String descripcion = descripcionTextField.getText();
            String clasificacion = clasificacionTextField.getText();
            double precio = Double.parseDouble(precioTextField.getText());
            int cantidad = Integer.parseInt(cantidadTextField.getText());

            Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);
            productos.add(producto);
            guardarProductosEnArchivo();
        } catch (NumberFormatException e) {
            System.out.println("Error: Los valores numéricos no son válidos.");
        } catch (ProductoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
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

    @FXML
    public void editarProducto() {
        Producto selectedProducto = productosListView.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            try {
                selectedProducto.setNombre(nombreTextField.getText());
                selectedProducto.setDescripcion(descripcionTextField.getText());
                selectedProducto.setClasificacion(clasificacionTextField.getText());
                selectedProducto.setPrecio(Double.parseDouble(precioTextField.getText()));
                selectedProducto.setCantidadDisponible(Integer.parseInt(cantidadTextField.getText()));
                productosListView.refresh();
                guardarProductosEnArchivo();
            } catch (NumberFormatException e) {
                System.out.println("Error: Los valores numéricos no son válidos.");
            }
        }
    }

    @FXML
    public void eliminarProducto() {
        Producto selectedProducto = productosListView.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            productos.remove(selectedProducto);
            guardarProductosEnArchivo();
        }
    }

    private void mostrarProductoSeleccionado(MouseEvent event) {
        Producto selectedProducto = productosListView.getSelectionModel().getSelectedItem();
        if (selectedProducto != null) {
            nombreTextField.setText(selectedProducto.getNombre());
            descripcionTextField.setText(selectedProducto.getDescripcion());
            clasificacionTextField.setText(selectedProducto.getClasificacion());
            precioTextField.setText(String.valueOf(selectedProducto.getPrecio()));
            cantidadTextField.setText(String.valueOf(selectedProducto.getCantidadDisponible()));
        }
    }
}
