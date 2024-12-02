package main.java.org.example.sistemaproyec.Modelo;

import java.io.Serializable;

public class Producto implements Serializable {
    private String nombre;
    private String descripcion;
    private String clasificacion;
    private double precio;
    private int cantidadDisponible;

    // Constructor
    public Producto(String nombre, String descripcion, String clasificacion, double precio, int cantidadDisponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.clasificacion = clasificacion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Producto(String s, int i, double v) {
        this.nombre = s;
        this.cantidadDisponible = i;
        this.precio = v;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws ProductoException {
        if (precio < 0) {
            throw new ProductoException("El precio no puede ser negativo");
        }
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    // Método toString() para mostrar productos en la vista
    @Override
    public String toString() {
        return nombre + " - " + descripcion + " - " + clasificacion + " - " + precio + " - " + cantidadDisponible;
    }
}
