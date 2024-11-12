# Producto Controlador.java:

package main.java.org.example.sistemaproyec.Controlador;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con los productos.
 * Actúa como intermediario entre la vista y el modelo.
 */
public class ProductoControlador {
    private List<Producto> productos = new ArrayList<>();

    /**
     * Agrega un nuevo producto a la lista de productos.
     *
     * @param nombre Nombre del producto.
     * @param descripcion Descripción del producto.
     * @param precio Precio del producto.
     * @param cantidadDisponible Cantidad disponible del producto.
     */
    public void agregarProducto(String nombre, String descripcion, double precio, int cantidadDisponible) {
        // ... (implementación existente)
    }

    /**
     * Edita un producto existente en la lista de productos.
     *
     * @param index Índice del producto a editar en la lista.
     * @param nombre Nuevo nombre del producto.
     * @param descripcion Nueva descripción del producto.
     * @param precio Nuevo precio del producto.
     * @param cantidadDisponible Nueva cantidad disponible del producto.
     */
    public void editarProducto(int index, String nombre, String descripcion, double precio, int cantidadDisponible) {
        // ... (implementación existente)
    }

    /**
     * Elimina un producto de la lista de productos.
     *
     * @param index Índice del producto a eliminar en la lista.
     */
    public void eliminarProducto(int index) {
        // ... (implementación existente)
    }

    /**
     * Devuelve una lista con todos los productos almacenados.
     *
     * @return Una lista de objetos Producto.
     */
    public List<Producto> obtenerProductos() {
        return productos;
    }
}
# MainApp.java
package main.java.org.example.sistemaproyec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * Clase principal de la aplicación JavaFX "Sistema de Gestión de Materiales".
 * Se encarga de iniciar la aplicación, cargar la ventana principal y establecer el ícono.
 *
 * @author (opcional: incluir nombre del autor)
 * @version (opcional: incluir versión de la aplicación)
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ... (implementación existente)
    }

    public static void main(String[] args) {
        launch(args);
    }
}
# ProductoVista.java
package main.resources.org.example.sistemaproyec.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controlador FXML para la vista de productos.
 *
 * Esta clase se encarga de gestionar la interacción del usuario con la vista de productos,
 * como agregar, editar y eliminar productos. También se encarga de actualizar la interfaz de usuario
 * para reflejar los cambios en los datos.
 */
public class ProductoVista {

    @FXML
    private TextField nombreTextField; // Campo de texto para el nombre del producto
    @FXML
    private TextField descripcionTextField; // Campo de texto para la descripción del producto
    @FXML
    private TextField precioTextField; // Campo de texto para el precio del producto
    @FXML
    private TextField cantidadTextField; // Campo de texto para la cantidad disponible del producto
    @FXML
    private ListView<Producto> productosListView; // Lista que muestra los productos disponibles

    /**
     * Lista observable de productos para sincronizar con la ListView.
     */
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    /**
     * Método de inicialización que se ejecuta al cargar la vista.
     *
     * Configura la lista de productos en la ListView y agrega un listener
     * para actualizar los campos de texto al seleccionar un producto.
     */
    @FXML
    protected void initialize() {
        // Vincula la lista de productos con la ListView
        productosListView.setItems(productos);

        // Agrega un listener para actualizar los campos de texto al seleccionar un producto
        productosListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nombreTextField.setText(newValue.getNombre());
                descripcionTextField.setText(newValue.getDescripcion());
                precioTextField.setText(String.valueOf(newValue.getPrecio()));
                cantidadTextField.setText(String.valueOf(newValue.getCantidadDisponible()));
            }
        });
    }

    /**
     * Agrega un nuevo producto a la lista.
     *
     * Obtiene los datos del formulario, crea un nuevo objeto Producto y lo agrega
     * a la lista observable. Luego, limpia los campos del formulario.
     */
    @FXML
    protected void agregarProducto() {
        // Crea un nuevo producto a partir de los datos del formulario
        Producto producto = new Producto(
                nombreTextField.getText(),
                descripcionTextField.getText(),
                Double.parseDouble(precioTextField.getText()),
                Integer.parseInt(cantidadTextField.getText())
        );

        // Agrega el producto a la lista y limpia los campos
        productos.add(producto);
        limpiarCampos();
    }

    // ... (métodos editarProducto y eliminarProducto con comentarios similares)

    /**
     * Limpia los campos de texto del formulario.
     */
    private void limpiarCampos() {
        nombreTextField.clear();
        descripcionTextField.clear();
        precioTextField.clear();
        cantidadTextField.clear();
    }
}
# ProductoVista.fxml:
Descripción:
Este archivo define la interfaz gráfica (GUI) de la vista de productos en la aplicación "Sistema de Gestión de Materiales" utilizando JavaFX FXML.
Elementos:
•	VBox: Contenedor principal con una alineación central (alignment="CENTER"). 
o	Padding: Margen interior del VBox (padding).
o	ImageView: Imagen del logo o icono de la aplicación. 
	Image: Ruta a la imagen del logo (/main/resources/org/example/sistemaproyec/un_lobo_ingeniero-removebg-preview.png).
o	ListView (fx:id="productosListView"): Lista que muestra los productos disponibles.
o	TextField (x4): Campos de texto para ingresar datos del producto: 
	nombreTextField (promptText="Nombre del Producto").
	descripcionTextField (promptText="Descripción").
	precioTextField (promptText="Precio").
	cantidadTextField (promptText="Cantidad Disponible").
o	Button (x3): Botones para interactuar con los productos: 
	agregarProducto (text="Agregar Producto", onAction="#agregarProducto"): Invoca el método agregarProducto del controlador ProductoVista.
	editarProducto (text="Editar Producto", onAction="#editarProducto"): Invoca el método editarProducto del controlador ProductoVista.
	eliminarProducto (text="Eliminar Producto", onAction="#eliminarProducto"): Invoca el método eliminarProducto del controlador ProductoVista.
Estilos:
•	El archivo FXML utiliza estilos CSS para definir el aspecto visual de los elementos.
•	La propiedad style se utiliza para aplicar estilos en línea a los elementos.
•	Se utilizan estilos para: 
o	Fondo del VBox (background-color).
o	Bordes de los TextFields (border-color).
o	Bordes redondeados de los TextFields (background-radius).
o	Color del texto de los botones (font).
Relación con el controlador:
•	El atributo fx:controller especifica la clase ProductoVista que implementa el controlador FXML para la vista.
•	Los botones utilizan el atributo onAction para invocar métodos específicos en el controlador (agregarProducto, editarProducto, eliminarProducto).

# MenuPrincipalVista.fxml:
Documentación del archivo FXML MenuPrincipalVista.fxml
Descripción:
Este archivo define la interfaz gráfica (GUI) del menú principal de la aplicación "Sistema de Gestión de Materiales" utilizando JavaFX FXML.
Elementos:
•	VBox: Contenedor principal con una alineación central (alignment="CENTER"). 
o	Padding: Margen interior del VBox (padding).
o	ImageView: Imagen del logo o icono de la aplicación. 
	Image: Ruta a la imagen del logo (/main/resources/org/example/sistemaproyec/un_lobo_ingeniero-removebg-preview.png).
o	Button: Botón para acceder a la vista de productos. 
	Gestionar Productos (onAction="#irAVistaProducto", text="Gestionar Productos"): Invoca el método irAVistaProducto del controlador MenuPrincipalVista.
Estilos:
•	El archivo FXML utiliza estilos CSS para definir el aspecto visual del botón.
•	La propiedad style se utiliza para aplicar estilos en línea al botón.
•	Se utilizan estilos para: 
o	Fondo del VBox (background-color).
o	Bordes del botón (border-color, border-width).
Relación con el controlador:
•	El atributo fx:controller especifica la clase MenuPrincipalVista que implementa el controlador FXML para el menú.
•	El botón utiliza el atributo onAction para invocar un método específico en el controlador (irAVistaProducto).

