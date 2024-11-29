package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

import java.util.ArrayList;
import java.util.List;

public class ProductoControlador {

    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(String nombre, String descripcion,String clasificacion, double precio, int cantidadDisponible) throws ProductoException {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setClasificacion(clasificacion);
        producto.setPrecio(precio);
        producto.setCantidadDisponible(cantidadDisponible);
        productos.add(producto);
    }

    public void editarProducto(int index, String nombre, String descripcion, String clasificacion, double precio, int cantidadDisponible) {
        if (index >= 0 && index < productos.size()) {
            Producto producto = productos.get(index);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setClasificacion(clasificacion);
            producto.setPrecio(precio);
            producto.setCantidadDisponible(cantidadDisponible);
        }
    }

    public void eliminarProducto(int index) {
        if (index >= 0 && index < productos.size()) {
            productos.remove(index);
        }
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }
}