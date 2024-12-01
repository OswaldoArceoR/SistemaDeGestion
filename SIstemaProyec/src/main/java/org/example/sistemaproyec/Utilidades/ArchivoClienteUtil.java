package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Cliente;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoClienteUtil {

    private static final String RUTA_ARCHIVO = "clientes.txt";

    // Método para guardar la lista de clientes en un archivo
    public static void guardarClientes(List<Cliente> clientes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA_ARCHIVO))) {
            for (Cliente cliente : clientes) {
                String linea = cliente.getNombre() + "," + cliente.getDireccion() + "," +
                        cliente.getTelefono() + "," + cliente.getEmail() + "," + cliente.isClienteFrecuente();
                writer.write(linea);
                writer.newLine();
            }
        }
    }

    // Método para cargar la lista de clientes desde un archivo
    public static List<Cliente> cargarClientes() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String nombre = partes[0];
                    String direccion = partes[1];
                    String telefono = partes[2];
                    String email = partes[3];
                    boolean clienteFrecuente = Boolean.parseBoolean(partes[4]);
                    clientes.add(new Cliente(nombre, direccion, telefono, email, clienteFrecuente));
                }
            }
        }
        return clientes;
    }
}

