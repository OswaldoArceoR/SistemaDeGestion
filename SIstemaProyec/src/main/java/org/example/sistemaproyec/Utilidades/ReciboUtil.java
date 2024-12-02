package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Modelo.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReciboUtil {

    public static void generarReciboTXT(Venta venta) {
        String recibo = "Factura de Compra\n"
                + "Cliente: " + venta.getCliente().getNombre() + "\n"
                + "Fecha: " + venta.getFecha() + "\n"
                + "Productos:\n";

        for (Producto producto : venta.getProductosVendidos()) {
            recibo += producto.getNombre() + " - $" + producto.getPrecio() + "\n";
        }

        recibo += "Total: $" + venta.getTotal() + "\n";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("recibo.txt"));
            writer.write(recibo);
            writer.close();
            System.out.println("Recibo generado en formato .txt exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
