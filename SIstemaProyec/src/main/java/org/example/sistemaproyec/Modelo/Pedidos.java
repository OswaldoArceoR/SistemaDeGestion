package main.java.org.example.sistemaproyec.Modelo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Pedidos {
    private String cliente;
    private Date fecha;
    private List<Producto> productos;
    private List<Integer> cantidades;
    private double total;
    private String numeroPedido;

    public Pedidos(String cliente, List<Integer> cantidades, List<Producto> productos) throws PedidosException {
        if (productos == null || productos.isEmpty()) {
            throw new PedidosException("El pedido debe contener al menos un producto.");
        }
        if (cantidades == null || cantidades.isEmpty() || cantidades.size() != productos.size()) {
            throw new PedidosException("Las cantidades deben corresponder a los productos.");
        }
        if (cliente == null || cliente.isEmpty()) {
            throw new PedidosException("El nombre del cliente no puede estar vacío.");
        }
        this.cliente = cliente;
        this.fecha = new Date();
        this.cantidades = cantidades;
        this.productos = productos;
        this.total = 0;
        this.numeroPedido = UUID.randomUUID().toString();
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Integer> getCantidades() {
        return cantidades;
    }

    public void setCantidades(List<Integer> cantidades) {
        this.cantidades = cantidades;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = String.valueOf(numeroPedido);
    }

    @Override
    public String toString() {
        return cliente + " - " + fecha + " - "+ cantidades + " - "+ productos + " - $" + total + " - " + numeroPedido;
    }
}
