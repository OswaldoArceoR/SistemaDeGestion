package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Cliente;

public class MainControlador {

    private static Cliente clienteActual; // Cliente seleccionado para realizar el pedido.

    public static Cliente getClienteActual() {
        return clienteActual;
    }

    public static void setClienteActual(Cliente cliente) {
        clienteActual = cliente;
    }
}
