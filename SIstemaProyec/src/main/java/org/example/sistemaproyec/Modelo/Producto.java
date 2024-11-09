package main.java.org.example.sistemaproyec.Modelo;

public class Producto {
    protected String nombre;
    protected String descripcion;
    protected double precio;
    protected int cantidadDisponible;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

}
