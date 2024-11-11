package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductoVista {

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField descripcionTextField;
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
                precioTextField.setText(String.valueOf(newValue.getPrecio()));
                cantidadTextField.setText(String.valueOf(newValue.getCantidadDisponible()));
            }
        });
    }

    @FXML
    protected void agregarProducto() {
        Producto producto = new Producto(
                nombreTextField.getText(),
                descripcionTextField.getText(),
                Double.parseDouble(precioTextField.getText()),
                Integer.parseInt(cantidadTextField.getText())
        );
        productos.add(producto);
        limpiarCampos();
    }

    @FXML
    protected void editarProducto() {
        Producto seleccionado = productosListView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.setNombre(nombreTextField.getText());
            seleccionado.setDescripcion(descripcionTextField.getText());
            seleccionado.setPrecio(Double.parseDouble(precioTextField.getText()));
            seleccionado.setCantidadDisponible(Integer.parseInt(cantidadTextField.getText()));
            productosListView.refresh();
            limpiarCampos();
        }
    }

    @FXML
    protected void eliminarProducto() {
        Producto seleccionado = productosListView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productos.remove(seleccionado);
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        nombreTextField.clear();
        descripcionTextField.clear();
        precioTextField.clear();
        cantidadTextField.clear();
    }
}
