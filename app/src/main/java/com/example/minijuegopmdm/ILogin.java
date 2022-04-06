package com.example.minijuegopmdm;

/**
 * Interfaz en la que tenemos implementados los métodos de cada una de las
 * diferentes partes del modelo vista presentador
 */
public interface ILogin {

    interface Vista {
        boolean esNomValido (boolean val);
        boolean esPassValido (boolean val);
    }

    interface Presentador {
        boolean esNomValido (boolean val);
        boolean esPassValido (boolean val);
        boolean validarNombre(String nombre);
        boolean validarPassword(String pass);
    }

    interface Modelo {
        boolean validarNombre(String nombre);
        boolean validarPassword(String pass);
    }
}
