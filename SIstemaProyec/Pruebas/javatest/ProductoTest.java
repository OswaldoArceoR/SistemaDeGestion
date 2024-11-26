package javatest;

import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    private Producto producto;

    @BeforeEach
    public void setUp() {
        // Inicializamos un nuevo objeto Producto antes de cada prueba
        producto = new Producto();
    }

    @Test
    public void testSetAndGetNombre() {
        producto.setNombre("Cemento");
        assertEquals("Cemento", producto.getNombre(), "El nombre debería ser Cemento");
    }

    @Test
    public void testSetAndGetDescripcion() {
        producto.setDescripcion("Bolsa de cemento de 50 kg");
        assertEquals("Bolsa de cemento de 50 kg", producto.getDescripcion(), "La descripción debería ser 'Bolsa de cemento de 50 kg'");
    }

    @Test
    public void testSetAndGetPrecio() {
        producto.setPrecio(100.50);
        assertEquals(100.50, producto.getPrecio(), 0.01, "El precio debería ser 100.50");
    }

    @Test
    public void testSetAndGetCantidadDisponible() {
        producto.setCantidadDisponible(20);
        assertEquals(20, producto.getCantidadDisponible(), "La cantidad disponible debería ser 20");
    }

    @Test
    public void testToString() throws ProductoException {
        // Crear un objeto Producto
        Producto producto = new Producto("Arena", "Bolsa de arena de 40 kg", 75.0, 15);

        // Comprobar que el formato del toString es el esperado
        String expected = "Arena - Bolsa de arena de 40 kg - $75.0 - Cantidad: 15";
        assertEquals(expected, producto.toString());
    }
}
