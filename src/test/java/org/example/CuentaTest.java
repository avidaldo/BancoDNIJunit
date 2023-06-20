package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void restarASaldo() throws CantidadMayorQueSaldoException, DniFormatException {
        Cuenta cuenta = new Cuenta(Dni.parseDni("28879572J"), 500.33f);
        cuenta.restarASaldo(300.11f);
        assertEquals(200.22f, cuenta.getSaldo());
        cuenta.restarASaldo(200.22f);
        assertEquals(0, cuenta.getSaldo());

        assertThrows(CantidadMayorQueSaldoException.class, () -> {
            cuenta.restarASaldo(0.01f);
        });
        Cuenta cuenta3 = new Cuenta(Dni.parseDni("28879572J"), 500.33f);
        assertThrows(CantidadMayorQueSaldoException.class, () -> {
            cuenta3.restarASaldo(500.34f);
        });
    }


    @Test
    void sumarASaldo() throws DniFormatException {
        Cuenta cuenta = new Cuenta(Dni.parseDni("28879572J"), 500.33f);
        cuenta.sumarASaldo(300.11f);
        assertEquals(800.44f, cuenta.getSaldo(), 1E-3);
    }
}