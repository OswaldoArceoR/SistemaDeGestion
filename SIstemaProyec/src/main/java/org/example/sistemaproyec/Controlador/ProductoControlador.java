package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Intefaces.IGestion;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;
import main.resources.org.example.sistemaproyec.Vista.ProductoVista;

public class ProductoControlador implements IGestion<Producto> {

    private ProductoVista vista;
    private String nombre;
    private String descripcion;
    private String clasificacion;
    private Double precio;
    private int cantidad;

    // Constructor
    public ProductoControlador(ProductoVista vista) {
        this.vista = vista;
    }

    @Override
    public void agregar(Producto entidad) {
        // Crear un nuevo objeto Producto

        Producto nuevoProducto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);

        // Llamar a la vista para agregar el producto a la lista
        vista.agregarProducto();

        // Guardar el producto en el archivo
        vista.cargarProductosDesdeArchivo();
    }

    @Override
    public void eliminar(Producto productoSeleccionado) {
        if (productoSeleccionado != null) {
            vista.eliminarProducto();
            vista.cargarProductosDesdeArchivo();  // Guardar los productos después de eliminar
        }
    }

    @Override
    public void editar(Producto productoSeleccionado, String nombre, String descripcion, String clasificacion, double precio, int cantidad) throws ProductoException {
        if (productoSeleccionado != null) {
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setDescripcion(descripcion);
            productoSeleccionado.setClasificacion(clasificacion);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setCantidadDisponible(cantidad);

            // Guardar los productos después de editar
            vista.cargarProductosDesdeArchivo();
        }
    }
}
