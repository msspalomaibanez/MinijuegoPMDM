package com.example.minijuegopmdm;

/**
 * Clase en la cual llevamos a cabo un paso intermedio entre el modelo y la vista donde
 * usaremos los métodos de validación
 */
public class LoginPresenter implements ILogin.Presentador{
    private ILogin.Vista vista;
    private ILogin.Modelo modelo;

    public LoginPresenter (ILogin.Vista vista) {
        this.vista = vista;
        modelo = new LoginModelo(this);
    }

    @Override
    public boolean esNomValido(boolean val) {
        if (vista != null) {
            vista.esNomValido(val);
        }

        return vista.esNomValido(val);
    }

    @Override
    public boolean esPassValido(boolean val) {
        return vista.esPassValido(val);
    }

    @Override
    public boolean validarNombre(String nombre) {
        return modelo.validarNombre(nombre);
    }

    @Override
    public boolean validarPassword(String pass) {
        return modelo.validarPassword(pass);
    }
}
