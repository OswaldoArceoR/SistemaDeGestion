package main.java.org.example.sistemaproyec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/MenuPrincipalVista.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 380);
            primaryStage.setTitle("Sistema de Gestión de Materiales - Menú Principal");
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(getClass().getResource("/main/resources/org/example/sistemaproyec/un_lobo_ingeniero.png").toExternalForm()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la aplicación.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
