package dds.monedero.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
  public SaldoInsuficienteException() {
    super("Saldo Insuficiente");
  }
}