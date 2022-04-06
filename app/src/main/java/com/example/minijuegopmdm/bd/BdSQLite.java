package com.example.minijuegopmdm.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase que se encarga de gestionar la creación de nuestra base de datos SQLite junto con
 * sus correspondientes tablas de usuarios y partidas
 */
public class BdSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "minijuegoCTS.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_PARTIDAS = "t_partidas";

    public BdSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Si las tablas ya existen las eliminamos
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDAS);
        //Creación de la tabla de usuarios
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "usuario_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "password TEXT NOT NULL)");
        //Creación de la tabla de partidas
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PARTIDAS + "(" +
                "partida_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "inicio TEXT NOT NULL," +
                "fin TEXT NOT NULL," +
                "tiradas INTEGER," +
                "posiciones TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PARTIDAS);
        onCreate(sqLiteDatabase);
    }
}
