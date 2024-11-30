package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteControlador {
    private List<Cliente> clientes = new ArrayList<>();

    public void agregarCliente(String nombre, String direccion, String telefono, String email,boolean clienteFrecuente) {
        Cliente cliente = new Cliente(nombre, direccion, telefono, email, clienteFrecuente);
        clientes.add(cliente);
        System.out.println("Cliente agregado: " + cliente);
    }

    public void editarCliente(Cliente cliente, String nombre, String direccion, String telefono, String email) {
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        System.out.println("Cliente editado: " + cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
        System.out.println("Cliente eliminado: " + cliente);
    }

    public List<Cliente> obtenerClientes() {
        return clientes;
    }

    public void registrarCompra(Cliente cliente) {
        cliente.incrementarCompras();
        System.out.println("Compra registrada para el cliente: " + cliente.getNombre() + ". Descuento actual: " + cliente.getDescuento() * 100 + "%");
    }
}
