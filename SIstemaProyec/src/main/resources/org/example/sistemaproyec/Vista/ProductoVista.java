package main.resources.org.example.sistemaproyec.Vista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Producto;

public class ProductoVista {

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
    private ListView<Producto> productosListView;

    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        productosListView.setItems(productos);

        productosListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nombreTextField.setText(newValue.getNombre());
                descripcionTextField.setText(newValue.getDescripcion());
                clasificacionTextField.setText(newValue.getClasificacion());
                precioTextField.setText(String.valueOf(newValue.getPrecio()));
                cantidadTextField.setText(String.valueOf(newValue.getCantidadDisponible()));
            }
        });
    }

    @FXML
    protected void agregarProducto() {
        try {
            String nombre = nombreTextField.getText();
            String descripcion = descripcionTextField.getText();
            String clasificacion = clasificacionTextField.getText();
            double precio = Double.parseDouble(precioTextField.getText());
            int cantidad = Integer.parseInt(cantidadTextField.getText());

            // Verificar que los campos no estén vacíos
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                mostrarAlerta("Campos vacíos", "Por favor, complete todos los campos.", AlertType.WARNING);
                return;
            }

            Producto producto = new Producto(nombre, descripcion, precio, cantidad);
            productos.add(producto);
            limpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Por favor, ingrese valores numéricos válidos para el precio y la cantidad.", AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error desconocido", "Ha ocurrido un error inesperado al agregar el producto.", AlertType.ERROR);
        }
    }

    @FXML
    protected void editarProducto() {
        try {
            Producto seleccionado = productosListView.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                String nombre = nombreTextField.getText();
                String descripcion = descripcionTextField.getText();
                String clasificacion = clasificacionTextField.getText();
                double precio = Double.parseDouble(precioTextField.getText());
                int cantidad = Integer.parseInt(cantidadTextField.getText());

                // Verificar que los campos no estén vacíos
                if (nombre.isEmpty() || descripcion.isEmpty()) {
                    mostrarAlerta("Campos vacíos", "Por favor, complete todos los campos.", AlertType.WARNING);
                    return;
                }

                seleccionado.setNombre(nombre);
                seleccionado.setDescripcion(descripcion);
                seleccionado.setClasificacion(clasificacion);
                seleccionado.setPrecio(precio);
                seleccionado.setCantidadDisponible(cantidad);
                productosListView.refresh();
                limpiarCampos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Por favor, ingrese valores numéricos válidos para el precio y la cantidad.", AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error desconocido", "Ha ocurrido un error inesperado al editar el producto.", AlertType.ERROR);
        }
    }

    @FXML
    protected void eliminarProducto() {
        try {
            Producto seleccionado = productosListView.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                productos.remove(seleccionado);
                limpiarCampos();
            } else {
                mostrarAlerta("Producto no seleccionado", "Por favor, seleccione un producto para eliminar.", AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error desconocido", "Ha ocurrido un error inesperado al eliminar el producto.", AlertType.ERROR);
        }
    }

    private void limpiarCampos() {
        nombreTextField.clear();
        descripcionTextField.clear();
        clasificacionTextField.clear();
        precioTextField.clear();
        cantidadTextField.clear();
    }

    // Método para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
