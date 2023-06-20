package org.example;

/**
 * Clase que representa un DNI válido español.
 * Se compone de dos atributos separados: un string con el número de DNI y otro con la letra de control
 */
public final class Dni {
    private final String numeroString;
    private final String letra;

    private Dni(String numeroString, String letra) {
        this.numeroString = numeroString;
        this.letra = letra;
    }

    private static final String LETRAS_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";


    @Override
    public String toString() {
        return numeroString + letra;
    }

    /**
     * Parsea el string que recibe como argumento para devolver un DNI válido, aceptando o no separador entre los números y la letra. Para ello:
     * <ul>
     *   <li>Elimina espacios vacíos al principio y final del string</li>
     *   <li>Comprueba el tamaño total</li>
     *   <li>Comprueba que los primeros 8 caracteres sean números</li>
     *   <li>En caso de tener 10 caracteres, el separador (en la penúltima posición) deberá ser "-" o " "</li>
     *   <li>El último caracter debe ser una letra</li>
     *   <li>La letra debe ser el caracter de control correspondiente al número, siguiendo el <a href="https://www.interior.gob.es/opencms/ca/servicios-al-ciudadano/tramites-y-gestiones/dni/calculo-del-digito-de-control-del-nif-nie/">criterio establecido por el ministerio del interior</a></li>
     * </ul>
     *
     * Ejemplos de DNI válidos: 56620596R, 46599055 C, 28879572-J
     *
     * @param dniString string que contiene el DNI
     * @return un objeto Dni con el DNI correspondiente al string pasado como argumento
     * @throws DniFormatException si el string no es un DNI válido (el mensaje de la excepción concretará el error)
     */
    public static Dni parseDni(String dniString) throws DniFormatException {
        dniString = dniString.trim();
        int longitud = dniString.length();
        if (longitud < 9 || longitud > 10)
            throw new DniFormatException("El DNI debe tener 9 caracteres o 10 con separador");

        String numeroDniString = dniString.substring(0, 8);

        Character letraCalculada = null;
        try {
            letraCalculada = LETRAS_CONTROL.charAt(Integer.parseInt(numeroDniString) % 23);
        } catch (NumberFormatException e) {
            throw new DniFormatException("Los primeros 8 caracteres deben ser números");
        }

        char letra;
        if (longitud == 10) {
            char separador = dniString.charAt(8);
            if (separador != '-' && separador != ' ') throw new DniFormatException("Separador inválido");
            letra = Character.toUpperCase(dniString.charAt(9));
        } else letra = Character.toUpperCase(dniString.charAt(8));
        if (!Character.isLetter(letra)) throw new DniFormatException("El último caracter debe ser una letra");

        if (letraCalculada != letra) throw new DniFormatException("La letra de control no es válida");

        return new Dni(numeroDniString, Character.toString(letra));
    }
    // Nota: he añadido una corrección para que permita introducir minúsculas


    // En examen: si se testea correctamente parseDni, no será necesario testear validateDni

    public static boolean validateDni(String dniString) {
        dniString = dniString.trim();
        int longitud = dniString.length();
        if (longitud < 9 || longitud > 10) return false;

        String numeroDniString = dniString.substring(0, 8);

        Character letraCalculada = null;
        try {
            letraCalculada = LETRAS_CONTROL.charAt(Integer.parseInt(numeroDniString) % 23);
        } catch (NumberFormatException e) {  return false; }

        char letra;
        if (longitud == 10) {
            char separador = dniString.charAt(8);
            if (separador != '-' && separador != ' ')  return false;
            letra = dniString.charAt(9);
        } else letra = dniString.charAt(8);
        if (!Character.isLetter(letra))  return false;

        return letraCalculada == letra;
    }

}
