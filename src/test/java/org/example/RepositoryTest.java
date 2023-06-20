package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    Repository repository;

    @BeforeEach
    void setUp() {
        repository = new Repository();
    }

    @Test
    void findByEmailException() {
        assertNull(repository.findByDni("28879572J"));
    }

    @Test
    void test1() throws DniFormatException {
        assertTrue(repository.add(new Cuenta(Dni.parseDni("28879572J"), 500)));
        assertEquals(repository.findByDni("28879572J").getSaldo(), 500);
        assertFalse(repository.add(new Cuenta(Dni.parseDni("28879572J"), 400)));
    }


}