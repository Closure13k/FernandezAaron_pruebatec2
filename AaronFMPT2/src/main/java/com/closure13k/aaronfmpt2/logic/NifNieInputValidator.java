package com.closure13k.aaronfmpt2.logic;


/**
 * Valida inputs antes de proceder con las acciones.
 */
public class NifNieInputValidator {
    private static final String NIF_REGEX = "^\\d{8}[A-Z]$";
    private static final String NIE_REGEX = "^[XYZ]\\d{7}[A-Z]$";
    private static final String CONTROL_DIGITS = "TRWAGMYFPDXBNJZSQVHLCKE";


    /**
     * Comprueba si un NIF es válido.
     * <p>
     * Evaluá contra un patrón simple, para pruebas rápidas.
     */
    public static boolean isValidNifSimple(String nif) {
        return nif != null && nif.matches(NIF_REGEX);
    }

    /**
     * Comprueba si un NIF o NIE es válido.
     * <p>
     * Evaluá contra patrones y comprueba que el dígito de control sea correcto.
     */
    public static boolean isValidNifNie(String nif) {
        if (nif == null || nif.length() != 9) {
            return false;
        }
        String nifUpper = nif.toUpperCase();
        if (!nifUpper.matches(NIF_REGEX)) {
            return controlDigitIsValid(
                    Integer.parseInt(nifUpper.substring(0, 8)),
                    nifUpper.charAt(8)
            );
        } else if (!nifUpper.matches(NIE_REGEX)) {
            char initialLetter = getNieInitialNumber(nifUpper.charAt(0));
            return controlDigitIsValid(
                    Integer.parseInt(String.valueOf(initialLetter) + nifUpper.substring(1, 8)),
                    nifUpper.charAt(8)
            );
        } else {
            return false;
        }
    }

    /**
     * Obtiene el número correspondiente a la letra inicial de un NIE.
     */
    private static char getNieInitialNumber(char c) {
        return switch (c) {
            case 'X' -> '0';
            case 'Y' -> '1';
            case 'Z' -> '2';
            default -> c;
        };
    }

    /**
     * Comprueba si el dígito de control de un NIF o NIE es válido.
     */
    private static boolean controlDigitIsValid(int nifNumber, char controlDigit) {
        return controlDigit == CONTROL_DIGITS.charAt(nifNumber % 23);
    }


    private NifNieInputValidator() {
    }
}
