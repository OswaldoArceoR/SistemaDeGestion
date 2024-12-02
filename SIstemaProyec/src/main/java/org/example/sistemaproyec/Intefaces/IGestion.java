package main.java.org.example.sistemaproyec.Intefaces;

import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

public interface IGestion<T> {
    void agregar(T entidad);
    void eliminar(T productoSeleccionado);
    void editar(T productoSeleccionado, String nombre, String descripcion, String clasificacion, double precio, int cantidad) throws ProductoException;
}
