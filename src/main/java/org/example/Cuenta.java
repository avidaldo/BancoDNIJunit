package org.example;


public class Cuenta {

    private final Dni dni;

    public Dni getDni() {
        return dni;
    }

    public Cuenta(Dni dni, float saldo) {
        this.dni = dni;
        this.saldo = saldo;
    }

    private float saldo;
    public float getSaldo() { return saldo; }


    public void restarASaldo(float cantidad) throws CantidadMayorQueSaldoException {
        if (cantidad > saldo) throw new CantidadMayorQueSaldoException();
        this.saldo -= cantidad;
    }

    public void sumarASaldo(float cantidad) {
        this.saldo += cantidad;
    }


}
