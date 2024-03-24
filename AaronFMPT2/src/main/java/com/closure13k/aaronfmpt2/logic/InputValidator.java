package com.closure13k.aaronfmpt2.logic;

/**
 * Valida inputs antes de proceder con las acciones.
 */
public class InputValidator {

    private static final String NIF_REGEX = "^[0-9]{8}[A-Za-z]{1}$";

    public static boolean isValidNif(String nif) {
        return nif.matches(NIF_REGEX);
    }

}
