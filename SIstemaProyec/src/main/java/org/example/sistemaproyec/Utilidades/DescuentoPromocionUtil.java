package main.java.org.example.sistemaproyec.Utilidades;

import main.java.org.example.sistemaproyec.Modelo.Cliente;
import main.java.org.example.sistemaproyec.Modelo.Producto;
import java.time.LocalDate;
import java.util.List;

public class DescuentoPromocionUtil {

    private static final double DESCUENTO_CLIENTE_FRECUENTE = 0.10; // 10%
    private static final double DESCUENTO_PROMOCION = 0.20; // 20% para Black Friday
    private static final int MES_BLACK_FRIDAY = 11; // Noviembre

    // Descuento para cliente frecuente
    public static double aplicarDescuentoClienteFrecuente(double total, Cliente cliente) {
        if (cliente.esFrecuente()) { // Asumiendo que existe este método en Cliente
            return total * (1 - DESCUENTO_CLIENTE_FRECUENTE);
        }
        return total;
    }

    // Descuento de promociones por mes (como Black Friday)
    public static double aplicarPromocionPorMes(double total) {
        LocalDate hoy = LocalDate.now();
        if (hoy.getMonthValue() == MES_BLACK_FRIDAY) {
            return total * (1 - DESCUENTO_PROMOCION);
        }
        return total;
    }

    // Descuento manual aplicado por un empleado
    public static double aplicarDescuentoManual(double total, double porcentajeDescuento) {
        return total * (1 - (porcentajeDescuento / 100));
    }

    // Calcular total con descuentos automáticos (cliente frecuente + promoción)
    public static double calcularTotalConDescuentos(double total, Cliente cliente) {
        total = aplicarDescuentoClienteFrecuente(total, cliente);
        total = aplicarPromocionPorMes(total);
        return total;
    }
}
