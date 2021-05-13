package dds.monedero.exceptions;

public class MontoNegativoException extends RuntimeException {
  public MontoNegativoException() {
    super("No es posible depositar un monto negativo.");
  }
}