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
            stage.setScene(new Scene(fxmlLoader.load(), 600, 500));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
