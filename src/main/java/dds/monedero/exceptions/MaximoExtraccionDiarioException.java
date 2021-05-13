package dds.monedero.exceptions;

public class MaximoExtraccionDiarioException extends RuntimeException {
  public MaximoExtraccionDiarioException() {
    super("Ha superado el permitido de extraccion diaria ($1000)");
  }
}