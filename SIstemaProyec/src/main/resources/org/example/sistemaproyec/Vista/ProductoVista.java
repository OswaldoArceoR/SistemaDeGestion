package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Controlador.ProductoControlador;

public class ProductoVista {
    private ProductoControlador controlador = new ProductoControlador();

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private TextField precioTextField;
    @FXML
    private TextField cantidadTextField;

    @FXML
    protected void agregarProducto() {
        // Obtener valores de los campos de texto y agregar un producto
    }

    @FXML
    protected void editarProducto() {
        // Lógica para editar producto
    }

    @FXML
    protected void eliminarProducto() {
        // Lógica para eliminar producto
    }
}
