package org.example;

/**
 * Controlador de la aplicación, capa superior del backend siguiendo una arquitectura Clean Code
 */
public class Controller {
    private final Repository repository = new Repository();

    private static final float MIN_CANTIDAD_TRANSACCION = 0.50f;
    private static final float MAX_CANTIDAD_TRANSACCION = 500f;

    public boolean registrarCuenta(String dniString, float saldoInicial)
            throws CantidadMenorOIgualQueCeroException, DniFormatException {
        if (saldoInicial <= 0) throw new CantidadMenorOIgualQueCeroException();
        return repository.add(new Cuenta(Dni.parseDni(dniString), saldoInicial));
    }

    public float consultarSaldo(String dni) throws CuentaNoExisteException {
        Cuenta cuenta = repository.findByDni(dni);
        if (cuenta == null) throw new CuentaNoExisteException();
        else return cuenta.getSaldo();
    }

    public void ingresarDinero(String dni, float cantidad)
            throws CantidadMenorOIgualQueCeroException, CantidadNoIntervaloValidoException {
        if (cantidad <= 0) throw new CantidadMenorOIgualQueCeroException();
        if ((cantidad < MIN_CANTIDAD_TRANSACCION) || (cantidad > MAX_CANTIDAD_TRANSACCION))
            throw new CantidadNoIntervaloValidoException();
        repository.findByDni(dni).sumarASaldo(cantidad);
    }

    public void sacarDinero(String dni, float cantidad)
            throws CantidadMenorOIgualQueCeroException, CantidadNoIntervaloValidoException,
            CantidadMayorQueSaldoException {
        if (cantidad <= 0) throw new CantidadMenorOIgualQueCeroException();
        if ((cantidad < MIN_CANTIDAD_TRANSACCION) || (cantidad > MAX_CANTIDAD_TRANSACCION))
            throw new CantidadNoIntervaloValidoException();
        repository.findByDni(dni).restarASaldo(cantidad);
    }

    /**
     * Realiza una transferencia de una cantidad de dinero entre una cuenta emisora (a la que se restará la cantidad)
     * y una cuenta receptora (que se incrementará en dicha cantidad)
     *
     * @param dniEmisor String del DNI (con formato 12345678A) identificativo de la cuenta emisora
     * @param dniReceptor String del DNI (con formato 12345678A) identificativo de la cuenta receptora
     * @param cantidad cantidad a transferir
     * @throws CantidadMenorOIgualQueCeroException si la cantidad a transferir es cero o negativa
     * @throws CantidadMayorQueSaldoException si la cuenta emisora no tiene suficiente saldo para transferir esa cantidad
     * @throws CantidadNoIntervaloValidoException si el cantidad a transferir no se encuentra en el rango válido: 0.50f &lt; cantidad &lt; 500f
     */
    // TODO: No permite ingresar DNI con separador
    public void transferir(String dniEmisor, String dniReceptor, float cantidad)
            throws CantidadMenorOIgualQueCeroException, CantidadMayorQueSaldoException, CantidadNoIntervaloValidoException {
        // TODO: Excepción cuenta emisora misma que receptora
        if (cantidad <= 0) throw new CantidadMenorOIgualQueCeroException();
        if ((cantidad < MIN_CANTIDAD_TRANSACCION) || (cantidad > MAX_CANTIDAD_TRANSACCION))
            throw new CantidadNoIntervaloValidoException();
        sacarDinero(dniEmisor, cantidad);
        ingresarDinero(dniReceptor, cantidad);
    }


}
