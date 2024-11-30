package main.java.org.example.sistemaproyec.Modelo;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private LocalDate fecha;
    private List<Producto> productosVendidos;
    private double total;
    private Cliente cliente;

    public Venta(LocalDate fecha, List<Producto> productosVendidos, double total, Cliente cliente) {
        this.fecha = fecha;
        this.productosVendidos = productosVendidos;
        this.total = total;
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public double getTotal() {
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Fecha: " + fecha + ", Cliente: " + cliente.getNombre() + ", Total: $" + total;
    }
}
