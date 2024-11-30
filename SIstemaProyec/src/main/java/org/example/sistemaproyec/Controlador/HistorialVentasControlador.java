package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Modelo.HistorialVentas;

public class HistorialVentasControlador {
    private HistorialVentas historialVentas;

    public HistorialVentasControlador() {
        historialVentas = new HistorialVentas();
    }

    public void registrarVenta(Venta venta) {
        historialVentas.registrarVenta(venta);
    }

    public void mostrarVentas() {
        historialVentas.getTodasLasVentas().forEach(System.out::println);
    }

    public void buscarVentasPorCliente(String nombreCliente) {
        historialVentas.buscarVentasPorCliente(nombreCliente).forEach(System.out::println);
    }
}