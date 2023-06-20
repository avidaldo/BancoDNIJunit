package org.example;

import java.util.*;

public class Repository {

    private final List<Cuenta> baseDeDatos = new ArrayList<>();

    public boolean add(Cuenta cuenta) {
        if (findByDni(cuenta.getDni().toString())!=null) return false;
        else return baseDeDatos.add(cuenta);
    }

    public Cuenta findByDni(String dni) {
        return baseDeDatos.stream().filter(e -> e.getDni().toString().equals(dni)).findAny().orElse(null);
    }

}