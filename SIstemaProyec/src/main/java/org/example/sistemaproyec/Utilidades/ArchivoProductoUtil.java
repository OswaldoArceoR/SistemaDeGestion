package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Producto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArchivoProductoUtil {

    // Método para cargar productos desde un archivo
    public static List<Producto> cargarProductos(String rutaArchivo) throws IOException {
        List<Producto> productos = new ArrayList<>();
        Path path = Paths.get(rutaArchivo);

        // Verificamos si el archivo existe
        if (!Files.exists(path)) {
            throw new IOException("El archivo no existe en la ruta especificada.");
        }

        // Leemos el archivo línea por línea
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Separamos los datos de la línea por coma
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    try {
                        // Parseamos los valores y creamos el objeto Producto
                        String nombre = datos[0];
                        String descripcion = datos[1];
                        String clasificacion = datos[2];
                        double precio = Double.parseDouble(datos[3]);
                        int cantidadDisponible = Integer.parseInt(datos[4]);

                        Producto producto = new Producto(nombre, descripcion, clasificacion, precio, cantidadDisponible);
                        productos.add(producto);
                    } catch (NumberFormatException e) {
                        System.err.println("Error al parsear los datos del producto: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error al leer el archivo de productos.");
        }

        return productos;
    }
}
