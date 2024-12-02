package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Pedidos;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

import java.util.ArrayList;
import java.util.List;

public class PedidosControlador {
    private List<Pedidos> pedidos = new ArrayList<>();

    public void realizarPedido(List<Producto> productos, List<Integer> cantidades, String cliente) throws ProductoException {
        Pedidos pedido = new Pedidos(cliente, cantidades, productos);
        double total = calcularTotal(productos, cantidades);
        pedido.setTotal(total);
        pedidos.add(pedido);
    }

    private double calcularTotal(List<Producto> productos, List<Integer> cantidades) {
        double total = 0;
        for (int i = 0; i < productos.size(); i++) {
            total += productos.get(i).getPrecio() * cantidades.get(i);
        }
        return total;
    }


    public List<Pedidos> obtenerPedidos() {
        return pedidos;
    }
}

