package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

import java.io.*;

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
    @FXML
    private TextField barraBusqueda; // Nueva barra de búsqueda
    @FXML
    private TextArea detalleProducto; // Área para mostrar detalles del producto seleccionado

    private ObservableList<Producto> productos;

    @FXML
    public void initialize() {
        productos = FXCollections.observableArrayList();
        cargarProductosDesdeArchivo();

        // Configurar la lista filtrada para la búsqueda
        FilteredList<Producto> productosFiltrados = new FilteredList<>(productos, p -> true);
        barraBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            productosFiltrados.setPredicate(producto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String filtro = newValue.toLowerCase();
                return producto.getNombre().toLowerCase().contains(filtro) ||
                        producto.getClasificacion().toLowerCase().contains(filtro);
            });
        });

        // Lista ordenada
        SortedList<Producto> productosOrdenados = new SortedList<>(productosFiltrados);
        productosListView.setItems(productosOrdenados); // Asignación directa

        // Configurar la visualización personalizada de las celdas del ListView
        productosListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Producto> call(ListView<Producto> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Producto producto, boolean empty) {
                        super.updateItem(producto, empty);
                        if (empty || producto == null) {
                            setText(null);
                        } else {
                            setText(String.format("Nombre: %s | Clasificación: %s | Precio: %.2f | Cantidad: %d",
                                    producto.getNombre(), producto.getClasificacion(),
                                    producto.getPrecio(), producto.getCantidadDisponible()));
                            setStyle("-fx-padding: 10px; -fx-font-size: 14px; -fx-border-color: #000000;");
                        }
                    }
                };
            }
        });

        // Mostrar detalles del producto seleccionado
        productosListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetallesProducto(newValue);
            }
        });
    }

    public void cargarProductosDesdeArchivo() {
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

    private void mostrarDetallesProducto(Producto producto) {
        detalleProducto.setText("Nombre: " + producto.getNombre() +
                "\nDescripción: " + producto.getDescripcion() +
                "\nClasificación: " + producto.getClasificacion() +
                "\nPrecio: $" + producto.getPrecio() +
                "\nStock Disponible: " + producto.getCantidadDisponible());
    }

    // Métodos para agregar, editar y eliminar productos (sin cambios)
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
            cargarProductosDesdeArchivo();
        } catch (NumberFormatException e) {
            System.out.println("Error: Los valores numéricos no son válidos.");
        } catch (ProductoException e) {
            System.out.println(e.getMessage());
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
                cargarProductosDesdeArchivo();
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
            cargarProductosDesdeArchivo();
        }

    }
}
