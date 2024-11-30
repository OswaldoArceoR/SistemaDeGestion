package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoProductoUtil {

    // Método para guardar productos en un archivo
    public static void guardarProductos(List<Producto> productos, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Producto producto : productos) {
                // Guardamos también la clasificación del producto
                writer.write(producto.getNombre() + " - "
                        + producto.getDescripcion() + " - "
                        + producto.getClasificacion() + " - "  // Añadido la clasificación
                        + "$" + producto.getPrecio() + " - "
                        + "Cantidad: " + producto.getCantidadDisponible());
                writer.newLine();
            }
            System.out.println("Productos guardados en el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los productos.");
        }
    }

    // Método para cargar productos desde un archivo
    public static List<Producto> cargarProductos(String filePath) throws ProductoException {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Asumiendo que los productos están en formato:
                // "nombre - descripcion - clasificacion - $precio - Cantidad: cantidadDisponible"
                String[] parts = line.split(" - ");
                if (parts.length == 5) {  // Se espera un total de 5 partes (nombre, descripcion, clasificacion, precio, cantidad)
                    String nombre = parts[0];
                    String descripcion = parts[1];
                    String clasificacion = parts[2];  // Leer la clasificación
                    double precio = Double.parseDouble(parts[3].substring(1));  // Eliminar el '$' del precio
                    int cantidadDisponible = Integer.parseInt(parts[4].split(": ")[1]);

                    // Crear el producto con la clasificación leída
                    Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidadDisponible);
                    productos.add(producto);
                }
            }
            System.out.println("Productos cargados desde el archivo.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los productos.");
        }
        return productos;
    }
}
