package org.example;

import org.junit.jupiter.api.Test;

import static org.example.Dni.parseDni;
import static org.junit.jupiter.api.Assertions.*;

class DniTest {

    @Test
    void parseDniTest() throws DniFormatException {
        assertEquals("28879572J", parseDni("28879572J").toString());
        assertEquals("56620596R", parseDni("56620596R").toString());
        assertEquals("46599055C", parseDni("46599055C").toString());

        assertEquals("00000023T", parseDni("00000023T").toString());

        assertEquals("28879572J", parseDni("28879572j").toString()); // Corregido, no se pedía en el examen
        assertEquals("28879572J", parseDni("28879572 J").toString());
        assertEquals("28879572J", parseDni("28879572-J").toString());

        assertEquals("El DNI debe tener 9 caracteres o 10 con separador",
                assertThrows(DniFormatException.class,
                        () -> parseDni("11222111")).getMessage());
        assertEquals("El DNI debe tener 9 caracteres o 10 con separador",
                assertThrows(DniFormatException.class,
                        () -> parseDni("11222111-JJ")).getMessage());
        assertEquals("Los primeros 8 caracteres deben ser números",
                assertThrows(DniFormatException.class,
                        () -> parseDni("1122211JJ")).getMessage());
        assertEquals("Separador inválido",
                assertThrows(DniFormatException.class,
                        () -> parseDni("11222111.K")).getMessage());
        assertEquals("El último caracter debe ser una letra",
                assertThrows(DniFormatException.class,
                        () -> parseDni("112221111")).getMessage());
        assertEquals("El último caracter debe ser una letra",
                assertThrows(DniFormatException.class,
                        () -> parseDni("11222111-")).getMessage());
        assertEquals("El último caracter debe ser una letra",
                assertThrows(DniFormatException.class,
                        () -> parseDni("11222111 2")).getMessage());



        assertEquals("La letra de control no es válida",
                assertThrows(DniFormatException.class,
                        () -> parseDni("28879572A")).getMessage());
        assertEquals("La letra de control no es válida",
                assertThrows(DniFormatException.class,
                        () -> parseDni("56620596 A")).getMessage());
        assertEquals("La letra de control no es válida",
                assertThrows(DniFormatException.class,
                        () -> parseDni("46599055-A")).getMessage());



    }

}