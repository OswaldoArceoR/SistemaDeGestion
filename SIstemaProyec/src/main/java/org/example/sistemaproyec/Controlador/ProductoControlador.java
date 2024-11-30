package main.java.org.example.sistemaproyec.Controlador;

import main.java.org.example.sistemaproyec.Modelo.Producto;
import main.resources.org.example.sistemaproyec.Vista.ProductoVista;
import main.java.org.example.sistemaproyec.Modelo.ProductoException;

public class ProductoControlador {

    private ProductoVista vista;

    // Constructor
    public ProductoControlador(ProductoVista vista) {
        this.vista = vista;
    }

    // Método para agregar un nuevo producto
    public void agregarProducto(String nombre, String descripcion, String clasificacion, double precio, int cantidad) throws ProductoException {
        // Crear un nuevo objeto Producto
        Producto nuevoProducto = new Producto(nombre, descripcion, clasificacion, precio, cantidad);

        // Llamar a la vista para agregar el producto a la lista
        vista.agregarProducto();

        // Guardar el producto en el archivo
        vista.guardarProductosEnArchivo();
    }

    // Método para editar un producto seleccionado
    public void editarProducto(Producto productoSeleccionado, String nombre, String descripcion, String clasificacion, double precio, int cantidad) {
        if (productoSeleccionado != null) {
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setDescripcion(descripcion);
            productoSeleccionado.setClasificacion(clasificacion);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setCantidadDisponible(cantidad);

            // Guardar los productos después de editar
            vista.guardarProductosEnArchivo();
        }
    }

    // Método para eliminar un producto
    public void eliminarProducto(Producto productoSeleccionado) {
        if (productoSeleccionado != null) {
            vista.eliminarProducto();
            vista.guardarProductosEnArchivo();  // Guardar los productos después de eliminar
        }
    }
}
