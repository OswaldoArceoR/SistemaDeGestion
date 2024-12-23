package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    @FXML
    protected void abrirClienteVista() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/ClienteVista.fxml"));
            Parent root = loader.load();
            Stage clienteStage = new Stage();
            clienteStage.setTitle("Gestión de Clientes");
            clienteStage.setScene(new Scene(root, 800, 600)); // Ajusta el tamaño según sea necesario
            clienteStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void irAHistorialVentas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/HistorialVentasVista.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Historial de Ventas");
            stage.setScene(new Scene(root, 800, 600)); // Ajusta el tamaño según sea necesario
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista del historial de ventas.");
        }
    }

    @FXML
    private void abrirReporteVentaYFinanzas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/ReporteVentaYFinanzasVista.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Reporte de Venta y Finanzas");
            stage.setScene(new Scene(root, 800, 600)); // Ajusta el tamaño según sea necesario
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista del reporte de venta y finanzas.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
