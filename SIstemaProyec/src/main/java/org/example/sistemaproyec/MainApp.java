package main.java.org.example.sistemaproyec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el menú principal en lugar de la vista de productos
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/MenuPrincipalVista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        primaryStage.setTitle("Sistema de Gestión de Materiales - Menú Principal");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResource("/main/resources/org/example/sistemaproyec/un_lobo_ingeniero.png").toExternalForm())); // Configurar el ícono
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
