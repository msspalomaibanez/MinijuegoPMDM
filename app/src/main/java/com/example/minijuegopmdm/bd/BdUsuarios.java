package com.example.minijuegopmdm.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

/**
 * Clase que se encarga de gestionar la inserción de datos de los usuarios a nuestra base
 * de datos SQLite
 */
public class BdUsuarios extends BdSQLite {

    Context context;

    public BdUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Método por el cual insertamos un registro de un usuario a la base de datos
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return long que sería el id para comprobar que el registro ha sido exitoso o no
     */
    public long insertarUsuario(String nombre, String password) {
        long id = 0;

        try {
            BdSQLite bdSQLite = new BdSQLite(context);
            SQLiteDatabase bd = bdSQLite.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nombre", nombre);
            values.put("password", password);

            id = bd.insert(TABLE_USUARIOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
}
