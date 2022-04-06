package com.example.minijuegopmdm;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase de pruebas JUnit4 en donde probamos los métodos de validación usados en RegistroActivity
 */
public class LoginModeloTest {

    private LoginModelo usuario;
    private ILogin.Presentador presentador;

    //A comntinuación, implementamos el siguiente método con la etiqueta @Before para que se ejecute
    //antes de realizar los métodos @Test. En este caso lo que estamos es creando un objeto de tipo
    //LoginModelo pasándole el presentador como parámetro

    @Before
    public void instanciarLoginModelo() {
        usuario = new LoginModelo(presentador);
    }

    /**
     * Este primer test nos ayudará a comprobar que el parámetro que le paso es nulo o no, comprobando
     * si el objeto LoginModelo existe y será lo primero que ejecutaremos para poder continuar con
     * el resto de @Test
     */
    @Test
    public void loginNoNulo() {
        assertNotNull(usuario);
    }

    /**
     * Método de validación en el que comprobaremos que si metemos un nombre válido el método
     * de validación devuelve true
     */
    @Test
    public void validarNombreSuperado() {
        String nombre = "paloma";
        assertTrue(usuario.validarNombre(nombre));
    }

    /**
     * Método de validación en el que comprobaremos que si metemos un nombre inválido con números
     * el método de validación devuelve false
     */
    @Test
    public void validarNombreNoSuperadoNumeros() {
        String nombre = "pal123";
        assertFalse(usuario.validarNombre(nombre));
    }

    /**
     * Método de validación en el que comprobaremos que si metemos un nombre inválido con espacios
     * el método de validación devuelve false
     */
    @Test
    public void validarNombreNoSuperadoEspacios() {
        String nombre = "pa   a";
        assertFalse(usuario.validarNombre(nombre));
    }

    /**
     * Método de validación en el que comprobaremos que si metemos una contraseña válida el método
     * de validación devuelve true
     */
    @Test
    public void validarPasswordSuperado() {
        String pass = "12345678";
        assertTrue(usuario.validarPassword(pass));
    }

    /**
     * Método de validación en el que comprobaremos que si metemos una contraseña inválido con
     * letras el método de validación devuelve false
     */
    @Test
    public void validarPasswordNoSuperadoLetras() {
        String pass = "1234aa78";
        assertFalse(usuario.validarPassword(pass));
    }

    /**
     * Método de validación en el que comprobaremos que si metemos una contraseña inválida con
     * espacios el método de validación devuelve false
     */
    @Test
    public void validarPasswordNoSuperadoEspacios() {
        String pass = "1234  78";
        assertFalse(usuario.validarPassword(pass));
    }
}