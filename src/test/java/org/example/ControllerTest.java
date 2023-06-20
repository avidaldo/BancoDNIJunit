package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    void tests() throws CantidadMenorOIgualQueCeroException, CuentaNoExisteException, CantidadMayorQueSaldoException, CantidadNoIntervaloValidoException, DniFormatException {

        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.registrarCuenta("28879572J", 0); // Valor límite
        });
        assertTrue(controller.registrarCuenta("28879572J", 0.01f)); // Valor límite
        assertTrue(controller.registrarCuenta("56620596R", 600));
        assertFalse(controller.registrarCuenta("28879572J", 2)); // Cuenta ya existente

        // Recuperamos el saldo de la cuenta ya creada, comprobando que se creó correctamente y que consultar saldo funciona
        assertEquals(0.01f, controller.consultarSaldo("28879572J"));

        assertThrows(CuentaNoExisteException.class, () -> {
            controller.consultarSaldo("46599055C");
        });


        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.ingresarDinero("28879572J", 0);
        });
        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.ingresarDinero("28879572J", -1);
        });

        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.ingresarDinero("28879572J", 0.49f);
        });
        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.ingresarDinero("28879572J", 500.01f);
        });

        controller.ingresarDinero("28879572J", 500);
        assertEquals(500.01, controller.consultarSaldo("28879572J"), 0.001);


        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.sacarDinero("28879572J", 0);
        });
        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.sacarDinero("28879572J", -1);
        });

        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.sacarDinero("28879572J", 0.49f);
        });
        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.sacarDinero("28879572J", 500.01f);
        });

        controller.sacarDinero("28879572J", 300);
        assertEquals(200.01, controller.consultarSaldo("28879572J"), 0.001);

    }

    @Test
    void transferirTest() throws CantidadMenorOIgualQueCeroException, DniFormatException,
            CantidadMayorQueSaldoException, CantidadNoIntervaloValidoException, CuentaNoExisteException {
        controller.registrarCuenta("28879572J", 444.44f);
        controller.registrarCuenta("56620596R", 333.33f);

        assertThrows(CantidadMenorOIgualQueCeroException.class, () -> {
            controller.transferir("28879572J", "56620596R", 0);
        });

        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.transferir("28879572J", "56620596R", 0.49f);
        });

        assertThrows(CantidadNoIntervaloValidoException.class, () -> {
            controller.transferir("28879572J", "56620596R", 500.01f);
        });

        assertThrows(CantidadMayorQueSaldoException.class, () -> {
            controller.transferir("28879572J", "46599055C", 444.45f);
        });

        controller.transferir("28879572J", "56620596R", 222.22f);
        assertEquals(222.22f, controller.consultarSaldo("28879572J"), 10E-3);
        assertEquals(555.55f, controller.consultarSaldo("56620596R"), 10E-3);

    }


}