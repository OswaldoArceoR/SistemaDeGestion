package main.java.org.example.sistemaproyec.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private LocalDate fecha;
    private List<Producto> productosVendidos;
    private double total;
    private Cliente cliente;
    private String nombreCliente; // Nueva propiedad para el nombre del cliente

    public Venta(LocalDate fecha, List<Producto> productosVendidos, double total, Cliente cliente) {
        this.fecha = fecha;
        this.productosVendidos = productosVendidos;
        this.total = total;
        this.cliente = cliente;
        this.nombreCliente = cliente != null ? cliente.getNombre() : ""; // Inicializa el nombre del cliente
    }

    public Venta(String fecha, String nombreCliente, double total) {
        this.fecha = LocalDate.parse(fecha);
        this.productosVendidos = new ArrayList<>();
        this.total = total;
        this.nombreCliente = nombreCliente; // Asigna el nombre del cliente
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public double getTotal() {
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha + ", Cliente: " + nombreCliente + ", Total: $" + total;
    }
}
