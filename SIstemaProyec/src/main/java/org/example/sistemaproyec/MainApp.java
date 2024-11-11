package main.java.org.example.sistemaproyec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/org/example/sistemaproyec/Vista/ProductoVista.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        primaryStage.setTitle("Sistema de Gestión de Materiales - Productos");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResource("/main/resources/org/example/sistemaproyec/un_lobo_ingeniero.png").toExternalForm()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
