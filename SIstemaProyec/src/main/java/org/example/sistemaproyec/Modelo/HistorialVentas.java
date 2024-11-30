package main.java.org.example.sistemaproyec.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HistorialVentas {
    private List<Venta> ventas;

    public HistorialVentas() {
        ventas = new ArrayList<>();
    }

    public void registrarVenta(Venta venta) {
        ventas.add(venta);
    }

    public List<Venta> buscarVentasPorCliente(String nombreCliente) {
        return ventas.stream()
                .filter(venta -> venta.getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                .collect(Collectors.toList());
    }

    public List<Venta> getTodasLasVentas() {
        return ventas;
    }
}
