package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();
//doble declaracion de variable

  public Cuenta() {
    saldo = 0;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  //feo nombre del metodo poner, poco expresivo -> depositar
  public void depositar(double dinero) {
    if (dinero <= 0) {
      //mandaria el mj directo a la excepcion
      throw new MontoNegativoException();
    }
    if (this.depositosDelaFecha(LocalDate.now()) >= 3) {
      throw new MaximaCantidadDepositosException();
    }
    this.agregarMovimiento(new Movimiento(LocalDate.now(), dinero, true));
  }

  public double depositosDelaFecha(LocalDate fecha){
    return getMovimientos().stream().filter(movimiento -> movimiento.isDeposito())
        .filter(movimiento -> movimiento.getFecha().equals(fecha)).count();
  }
//no expresivo
  public void extraer(double dinero) {
    if (dinero <= 0) {
      throw new MontoNegativoException();
    }
    if (getSaldo() - dinero < 0) {
      throw new SaldoMenorException("No puede extraer mas de " + getSaldo() + " $");
    }
    //redundante, se puede hacer con una variable
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (dinero > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
    new Movimiento(LocalDate.now(), dinero, false).agregateA(this);
  }

  //definir bien en donde quiero el metodo, si en movimiento o en cuenta
  public void agregarMovimiento(Movimiento movimiento) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
