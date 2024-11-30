package main.java.org.example.sistemaproyec.Modelo;

public class Cliente {

    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private boolean clienteFrecuente;
    private int comprasRealizadas;
    private double descuento;

    public Cliente(String nombre, String direccion, String telefono, String email, boolean clienteFrecuente) throws ClienteException {
        if (nombre == null || nombre.isEmpty()) {
            throw new ClienteException("El nombre no puede estar vacío.");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new ClienteException("La dirección no puede estar vacía.");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ClienteException("El teléfono no puede estar vacío.");
        }
        if (email == null || email.isEmpty()) {
            throw new ClienteException("El email no puede estar vacío.");
        }
        if (!email.contains("@")) {
            throw new ClienteException("El email debe ser válido.");
        }

        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.clienteFrecuente = clienteFrecuente;
        this.comprasRealizadas = 0;
        this.descuento = 0.0;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isClienteFrecuente() {
        return clienteFrecuente;
    }

    public void setClienteFrecuente(boolean clienteFrecuente) {
        this.clienteFrecuente = clienteFrecuente;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public void incrementarCompras() {
        this.comprasRealizadas++;
        actualizarDescuento();
    }

    private void actualizarDescuento() {
        if (comprasRealizadas >= 10) {
            this.descuento = 0.10; // 10% de descuento para clientes frecuentes
        }
    }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}
