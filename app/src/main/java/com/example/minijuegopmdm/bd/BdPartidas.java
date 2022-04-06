package com.example.minijuegopmdm.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Clase que se encarga de gestionar la inserción y lectura de datos de las partidas a nuestra
 * base de datos SQLite
 */
public class BdPartidas extends BdSQLite {

    Context context;

    public BdPartidas(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Método por el cual insertamos un registro de una partida a la base de datos
     * @param nombre nombre del usuario que ha jugado dicha partida
     * @param inicio fecha y hora a la que se ha iniciado la partida
     * @param fin fecha y hora a la que ha finalizado la partida
     * @param tiradas número de tiradas total
     * @param posiciones cada una de las posiciones por las que ha pasado el héroe
     * @return long que sería el id para comprobar que el registro ha sido exitoso o no
     */
    public long insertarPartida(String nombre, String inicio, String fin, int tiradas, String posiciones) {
        long id = 0;

        try {
            BdSQLite bdSQLite = new BdSQLite(context);
            SQLiteDatabase bd = bdSQLite.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nombre", nombre);
            values.put("inicio", inicio);
            values.put("fin", fin);
            values.put("tiradas", tiradas);
            values.put("posiciones", posiciones);

            id = bd.insert(TABLE_PARTIDAS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    /**
     * Método por el cual llevamos a cabo la lectura de todos los registros de partidas almacenados
     * en la base de datos
     * @return un ArrayList con todos los registros
     */
    public ArrayList<String> mostrarPartidas() {
    String p = "";
    ArrayList<String> datos = new ArrayList<>();
    BdSQLite bdSQLite = new BdSQLite(context);
    SQLiteDatabase bd = bdSQLite.getReadableDatabase();
        Cursor c=  bd.rawQuery("SELECT p.nombre, p.inicio, p.fin, p.tiradas, p.posiciones FROM t_partidas p, " +
                "t_usuarios u WHERE p.nombre = u.nombre", null);

        if(c.moveToFirst()) {
            do {
                String nombre = c.getString(0);
                String inicio = c.getString(1);
                String fin = c.getString(2);
                int tiradas = c.getInt(3);
                String posiciones = c.getString(4);

                p = "Nombre: " + nombre + "\nInicio partida: " + inicio + "\nFin partida: " +
                        fin + "\nNúmero tiradas: " + tiradas + "\nPosiciones: " + posiciones + "\n";
                datos.add(p);
            } while (c.moveToNext());
        }
        return datos;
}
}