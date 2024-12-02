package main.java.org.example.sistemaproyec.Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public Venta(String s, String s1, double total) {
        this.fecha = LocalDate.parse(s);
        this.productosVendidos = new ArrayList<>();
        this.total = total;
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

    @Override
    public String toString() {
        return "Fecha: " + fecha + ", Cliente: " + cliente.getNombre() + ", Total: $" + total;
    }
}
