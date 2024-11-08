module org.example.sistemaproyec {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sistemaproyec to javafx.fxml;
    exports org.example.sistemaproyec;
}