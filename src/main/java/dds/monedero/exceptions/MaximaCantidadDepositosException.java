package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException() {
    super("Ha llegado al limite de depositos diarios");
  }

}