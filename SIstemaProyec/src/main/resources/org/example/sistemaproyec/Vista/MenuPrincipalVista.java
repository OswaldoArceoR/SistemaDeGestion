package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuPrincipalVista {

    @FXML
    protected void irAVistaProducto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/ProductoVista.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Gestión de Productos");
            stage.setScene(new Scene(fxmlLoader.load(), 800, 700));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void realizarPedido() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/RealizarPedidoVista.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Realizar Pedido");
            stage.setScene(new Scene(fxmlLoader.load(), 800, 700));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista de realizar pedido.");
        }
    }
}
