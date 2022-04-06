package com.example.minijuegopmdm;

/**
 * Clase en la cual tenemos los métodos de validación referentes a la clase RegistroActivity
 */
public class LoginModelo implements ILogin.Modelo{

    private ILogin.Presentador presentador;
    private boolean valido = true;

    public LoginModelo(ILogin.Presentador presentador) {
        this.presentador = presentador;
    }

    /**
     * Método por el cual comprobamos la validez del campo nombre
     * @param nombre el nombre a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    @Override
    public boolean validarNombre(String nombre) {
        //Si el nombre está vacío será falso
        if (nombre.isEmpty()) {
            valido = false;
        }
        //Si el nombre no está entre 3 y 12 caracteres será falso
        if (nombre.length() < 3 || nombre.length() > 12) {
            valido = false;
        }
        //Si el nombre contiene algún dígito o algún espacio será falso
        for (char c : nombre.toCharArray()) {
            if (Character.isDigit(c) || (!Character.isLetter(c) && c == ' ')) {
                valido = false;
                break;
            }

        }
        return valido;
    }

    /**
     * Método por el cual comprobamos la validez del campo contraseña
     * @param pass la contraseña a comprobar
     * @return booleano que será true de ser válido o false de no serlo
     */
    @Override
    public boolean validarPassword(String pass) {
        //Si la contraseña está vacía será falso
        if (pass.isEmpty()) {
            valido = false;
        }
        //Si la contraseá no tiene 8 caracteres será falso
        if (pass.length() != 8) {
            valido = false;
        }
        //Si la contraseña no tiene un dígito o contiene algún espacio será falso
        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c) ||  c == ' ') {
                valido = false;
                break;
            }

        }
        return valido;
    }
}
