package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.org.example.sistemaproyec.Modelo.Producto;

import java.io.*;

public class ProductoVista {

    @FXML
    private ListView<Producto> productosListView;
    @FXML
    private TextField barraBusqueda;
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

        // Configurar celdas personalizadas para mostrar detalles
        productosListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Producto> call(ListView<Producto> listView) {
                return new ListCell<Producto>() {
                    @Override
                    protected void updateItem(Producto producto, boolean empty) {
                        super.updateItem(producto, empty);
                        if (empty || producto == null) {
                            setText(null);
                        } else {
                            // Crear un HBox para contener todos los detalles del producto
                            HBox hbox = new HBox(10);

                            // Crear un TextFlow para organizar los detalles
                            TextFlow textFlow = new TextFlow();
                            textFlow.getChildren().add(new Text("Nombre: " + producto.getNombre() + "\n"));
                            textFlow.getChildren().add(new Text("Descripción: " + producto.getDescripcion() + "\n"));
                            textFlow.getChildren().add(new Text("Clasificación: " + producto.getClasificacion() + "\n"));
                            textFlow.getChildren().add(new Text("Precio: $" + producto.getPrecio() + "\n"));
                            textFlow.getChildren().add(new Text("Cantidad: " + producto.getCantidadDisponible() + "\n"));

                            // Añadir el TextFlow al HBox
                            hbox.getChildren().add(textFlow);

                            // Establecer el contenido de la celda
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        // Mostrar datos del producto seleccionado en los campos
        productosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarProductoSeleccionado(newSelection);
            }
        });

        // Agregar evento para búsqueda
        barraBusqueda.setOnKeyReleased(this::filtrarProductos);
    }

    public void cargarProductosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                Producto producto = new Producto(datos[0], datos[1], datos[2],
                        Double.parseDouble(datos[3]), Integer.parseInt(datos[4]));
                productos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void agregarProducto() {
        String nombre = nombreTextField.getText();
        String descripcion = descripcionTextField.getText();
        String clasificacion = clasificacionTextField.getText();
        double precio = Double.parseDouble(precioTextField.getText());
        int cantidad = Integer.parseInt(cantidadTextField.getText());

        Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);
        productos.add(producto);
        guardarProductosEnArchivo();
    }

    public void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," + producto.getDescripcion() + ","
                        + producto.getClasificacion() + "," + producto.getPrecio() + ","
                        + producto.getCantidadDisponible());
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
            selectedProducto.setNombre(nombreTextField.getText());
            selectedProducto.setDescripcion(descripcionTextField.getText());
            selectedProducto.setClasificacion(clasificacionTextField.getText());
            selectedProducto.setPrecio(Double.parseDouble(precioTextField.getText()));
            selectedProducto.setCantidadDisponible(Integer.parseInt(cantidadTextField.getText()));
            productosListView.refresh();
            guardarProductosEnArchivo();
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

    private void mostrarProductoSeleccionado(Producto producto) {
        nombreTextField.setText(producto.getNombre());
        descripcionTextField.setText(producto.getDescripcion());
        clasificacionTextField.setText(producto.getClasificacion());
        precioTextField.setText(String.valueOf(producto.getPrecio()));
        cantidadTextField.setText(String.valueOf(producto.getCantidadDisponible()));
    }

    private void filtrarProductos(KeyEvent event) {
        String filtro = barraBusqueda.getText().toLowerCase();
        productosListView.setItems(productos.filtered(producto ->
                producto.getNombre().toLowerCase().contains(filtro) ||
                        producto.getClasificacion().toLowerCase().contains(filtro)));
    }
}
