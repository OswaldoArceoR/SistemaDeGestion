package org.example.sistemaproyec.Controlador;

import org.example.sistemaproyec.Modelo.Producto;

import java.util.Scanner;

public class ProductoControlador {

    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);
    Scanner sc2 = new Scanner(System.in);
    Scanner sc3 = new Scanner(System.in);
    Scanner sc4 = new Scanner(System.in);
    private org.example.sistemaproyec.Modelo.Producto[] Producto;

    public void AgregarProducto() {
        System.out.println("¿Cuántos Productos quiere agregar?");
        int tamaño = sc4.nextInt();
        for(int i=0;i<=tamaño;i++){
            Producto[i] = new Producto();
            System.out.println("Agregar Producto");
            System.out.println("Ingresar el Nombre del producto: ");
            String nombre = sc.nextLine();
            Producto[i].setNombre(nombre);
            System.out.println("Ingresar Descripcion del producto: ");
            String descripcion = sc1.nextLine();
            Producto[i].setDescripcion(descripcion);
            System.out.println("Ingresar Precio del producto: ");
            double precio = sc2.nextDouble();
            Producto[i].setPrecio(precio);
            System.out.println("Ingresar Cantidad Disponible del producto: ");
            int cantidadDisponible = sc3.nextInt();
            Producto[i].setCantidadDisponible(cantidadDisponible);
        }
    }

    public void EditarProducto() {

    }

}