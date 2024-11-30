package main.java.org.example.sistemaproyec.Modelo;

import java.time.LocalDate;
import java.util.List;

public class ClienteException extends RuntimeException {
  public ClienteException(String mensaje) {
    super(mensaje);
  }

  public ClienteException(String mensaje, Throwable causa) {
    super(mensaje, causa);
  }

}
