package com.example.minijuegopmdm.juego;

/**
 * Clase en la cual tenemos los atributos, constructores, getters y setters de una Casilla
 */
public class Casilla {

    private int fila;
    private int columna;
    private int imagen;

    public Casilla () {

    }

    public Casilla (int fila, int columna, int imagen) {
        this.fila = fila;
        this.columna = columna;
        this.imagen = imagen;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
