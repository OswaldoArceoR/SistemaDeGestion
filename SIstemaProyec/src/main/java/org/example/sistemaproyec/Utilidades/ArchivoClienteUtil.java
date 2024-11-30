package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoClienteUtil {

    // Método para cargar clientes desde un archivo
    public static List<Cliente> cargarClientes(String rutaArchivo) throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        File archivo = new File(rutaArchivo);

        // Verificamos si el archivo existe
        if (!archivo.exists()) {
            throw new IOException("El archivo no existe en la ruta especificada.");
        }

        // Leemos el archivo línea por línea
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separamos los datos de la línea por coma
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    // Parseamos los valores y creamos el objeto Cliente
                    String nombre = datos[0];
                    String direccion = datos[1];
                    String telefono = datos[2];
                    String email = datos[3];
                    boolean clienteFrecuente = Boolean.parseBoolean(datos[4]);

                    Cliente cliente = new Cliente(nombre, direccion, telefono, email, clienteFrecuente);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage(), e);
        }

        return clientes;
    }

    // Método para guardar los clientes en un archivo
    public static void guardarClientes(String rutaArchivo, List<Cliente> clientes) throws IOException {
        File archivo = new File(rutaArchivo);

        // Verificamos si el archivo existe, si no lo creamos
        if (!archivo.exists()) {
            archivo.createNewFile();
        }

        // Escribimos los clientes en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Cliente cliente : clientes) {
                // Escribimos los datos del cliente en formato CSV
                writer.write(cliente.getNombre() + ","
                        + cliente.getDireccion() + ","
                        + cliente.getTelefono() + ","
                        + cliente.getEmail() + ","
                        + cliente.isClienteFrecuente());
                writer.newLine(); // Saltamos a la siguiente línea
            }
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }
}
