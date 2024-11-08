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
        System.out.println("Editar Producto");
        System.out.println("Ingresar el numero de producto que deseas editar: ");
        Scanner sd = new Scanner(System.in);
        int p=sd.nextInt();

        System.out.println("¿Que parte desea modificar el producto? ");
        System.out.println("1.Nombre ");
        System.out.println("2.Descripcion ");
        System.out.println("3.Precio ");
        System.out.println("4.CantidadDisponible ");
        Scanner sa = new Scanner(System.in);
        int c = sa.nextInt();

        switch(c){
            case 1:
                System.out.println("Ingresar el nuevo nombre del producto: ");
                Scanner sc = new Scanner(System.in);
                String n = sc.nextLine();
                Producto[p].setNombre(n);
                break;
            case 2:
                System.out.println("Ingresar  la nueva descripcion del producto: ");
                Scanner sc1 = new Scanner(System.in);
                String d = sc1.nextLine();
                Producto[p].setDescripcion(d);
                break;
            case 3:
                System.out.println("Ingresar el nuevo precio del producto: ");
                Scanner sc2 = new Scanner(System.in);
                double pr=sc2.nextDouble();
                Producto[p].setPrecio(pr);
                break;
            case 4:
                System.out.println("Ingresar el nuevo cantidad disponible del producto: ");
                Scanner sc3 = new Scanner(System.in);
                int cd=sc3.nextInt();
                Producto[p].setCantidadDisponible(cd);
                break;

        }
    }

    public void EliminarProducto() {
        System.out.println("Eliminar Producto");
        System.out.println("Ingresar el nuevo nombre del producto que quiere eliminar: ");
        Scanner se = new Scanner(System.in);
        int e=se.nextInt();
        Producto[e]=null;
    }

}