package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.org.example.sistemaproyec.Modelo.Venta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReporteVentaYFinanzasVista {

    private HistorialVentasVista historialVentasVista;

    public ReporteVentaYFinanzasVista() {
        historialVentasVista = new HistorialVentasVista();
        historialVentasVista.cargarTodasLasVentas(); // Asegurarse de cargar todas las ventas al iniciar
    }

    @FXML
    private void generarInformeDiario() {
        LocalDate hoy = LocalDate.now();
        List<Venta> ventasHoy = historialVentasVista.obtenerVentasEntreFechas(hoy, hoy);
        double totalVentasHoy = historialVentasVista.obtenerTotalVentasEntreFechas(hoy, hoy);
        mostrarAlerta("Informe Diario", "Número de ventas: " + ventasHoy.size() + "\nTotal de ventas: $" + totalVentasHoy);
    }

    @FXML
    private void generarInformeSemanal() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioSemana = hoy.minusWeeks(1);
        List<Venta> ventasSemana = historialVentasVista.obtenerVentasEntreFechas(inicioSemana, hoy);
        double totalVentasSemana = historialVentasVista.obtenerTotalVentasEntreFechas(inicioSemana, hoy);
        mostrarAlerta("Informe Semanal", "Número de ventas: " + ventasSemana.size() + "\nTotal de ventas: $" + totalVentasSemana);
    }

    @FXML
    private void generarInformeMensual() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.minusMonths(1);
        List<Venta> ventasMes = historialVentasVista.obtenerVentasEntreFechas(inicioMes, hoy);
        double totalVentasMes = historialVentasVista.obtenerTotalVentasEntreFechas(inicioMes, hoy);
        mostrarAlerta("Informe Mensual", "Número de ventas: " + ventasMes.size() + "\nTotal de ventas: $" + totalVentasMes);
    }

    @FXML
    private void generarInformeAnual() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicioAno = hoy.minusYears(1);
        List<Venta> ventasAno = historialVentasVista.obtenerVentasEntreFechas(inicioAno, hoy);
        double totalVentasAno = historialVentasVista.obtenerTotalVentasEntreFechas(inicioAno, hoy);
        mostrarAlerta("Informe Anual", "Número de ventas: " + ventasAno.size() + "\nTotal de ventas: $" + totalVentasAno);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
