package main.java.org.example.sistemaproyec.Modelo;

public class Producto {

    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;

    public Producto(String nombre, String descripcion, double precio, int cantidadDisponible) throws ProductoException {
        if (nombre == null || nombre.isEmpty()) {
            throw new ProductoException("El nombre del producto no puede estar vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ProductoException("La descripción no puede estar vacía.");
        }
        if (precio <= 0) {
            throw new ProductoException("El precio debe ser mayor que cero.");
        }
        if (cantidadDisponible < 0) {
            throw new ProductoException("La cantidad disponible no puede ser negativa.");
        }

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
