package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void abrirClienteVista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/ClienteVista.fxml"));
            Object root = loader.load();

            Stage clienteStage = new Stage();
            clienteStage.setTitle("Gestión de Clientes");
            clienteStage.setScene(new Scene((Parent) root));
            clienteStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
