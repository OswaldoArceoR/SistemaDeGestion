package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Venta;
import main.java.org.example.sistemaproyec.Modelo.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReciboUtil {

    public static void generarReciboTXT(Venta venta, List<Producto> productosVendidos) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = now.format(formatter);
        String reciboFileName = "recibo_" + fechaHora + ".txt";

        String recibo = "Factura de Compra\n"
                + "Cliente: " + venta.getCliente().getNombre() + "\n"
                + "Fecha: " + venta.getFecha() + "\n"
                + "Productos:\n";

        for (Producto producto : productosVendidos) {
            recibo += "Producto: " + producto.getNombre() + " | Cantidad: " + producto.getCantidadDisponible() + " | Precio unitario: $" + producto.getPrecio() + "\n";
        }

        recibo += "Total: $" + venta.getTotal() + "\n";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(reciboFileName));
            writer.write(recibo);
            writer.close();
            System.out.println("Recibo generado en formato .txt exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
