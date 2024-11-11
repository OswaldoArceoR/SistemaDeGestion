package main.java.org.example.sistemaproyec.Modelo;

public class Producto {
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;

    // Constructor
    public Producto(String nombre, String descripcion, double precio, int cantidadDisponible) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Producto() {

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " - $" + precio + " - Cantidad: " + cantidadDisponible;
    }
}
