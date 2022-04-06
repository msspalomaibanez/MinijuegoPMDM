package com.example.minijuegopmdm.inicio;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minijuegopmdm.ILogin;
import com.example.minijuegopmdm.LoginPresenter;
import com.example.minijuegopmdm.R;
import com.example.minijuegopmdm.bd.BdSQLite;

/**
 * Clase en la cual se muestra un formulario para rellenar los datos de un usuario para
 * iniciar sesión en el juego, con opción a ir a la actividad para registrarse
 */
public class InicioActivity extends AppCompatActivity{

    private Button btn_inicio;
    private Button btn_registro;
    private EditText txt_nombre;
    private EditText txt_pass;
    private Cursor fila;
    private String nom;
    private String tag = "InicioActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MinijuegoPMDM);
        setContentView(R.layout.inicio_sesion);
        //Creamos una llamada a la base de datos para poder consultar los datos
        BdSQLite bdSQLite = new BdSQLite(getApplicationContext());
        SQLiteDatabase bd = bdSQLite.getWritableDatabase();
        //Asociamos las variables a los elementos del xml
        txt_nombre = findViewById(R.id.edtxt_nombre);
        txt_pass = findViewById(R.id.edtxt_pass);
        btn_inicio = findViewById(R.id.btn_inicio);
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Hacemos una comprobación de si los campos están o no vacíos:
                //1. Si los campos están vacíos hacemos un log y un Toast para informar por consola y al usuario respectivamente
                //además de poner el focus en el EditText del nombre
                //2. Si los campos contienen algo comprobamos mediante una consulta a nuestra base de datos si ese nombre y contraseña
                //han sido previamente registrados

                if (txt_nombre.getText().toString().isEmpty() || txt_pass.getText().toString().isEmpty()) {
                    Log.w(tag, "El nombre o la contraseña no se han introducido");
                    Toast.makeText(getApplicationContext(), "El nombre y la contraseña son obligatorios", Toast.LENGTH_SHORT).show();
                    txt_nombre.requestFocus();
                }else{
                    String nombre = txt_nombre.getText().toString();
                    String password = txt_pass.getText().toString();

                    //Recogemos la consulta en en Cursor para luego comparar los datos de la bd con los introducidos por el usuario
                    
                    fila = bd.rawQuery("select nombre, password from t_usuarios where nombre = '" + nombre + "' and password = '" + password + "'", null);
                    try {
                        if (fila.moveToFirst()) {
                            nom = fila.getString(0);
                            String pass = fila.getString(1);
                            if (nombre.equals(nom) && password.equals(pass)) {
                                lanzarActivityMainMenu(null);
                                limpiar();
                            }
                        } else {

                            //Si esos datos no coinciden hacemos un log y un Toast para informar por consola y al usuario respectivamente

                            Log.w(tag, "No se ha encontrado un usuario con esos datos");
                            Toast.makeText(getApplicationContext(), "No se ha encontrado un usuario con esos datos", Toast.LENGTH_SHORT).show();
                            txt_pass.setText("");
                            txt_nombre.requestFocus();
                        }
                    } catch (Exception ex) {
                        Log.e(tag, "- - Exception - - " + ex);
                        Toast.makeText(getApplicationContext(), "Error" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        btn_registro = findViewById(R.id.btn_registro);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActivityRegistro(null);
            }
        });
    }

    /**
     * Método por el cual se ejecutará la actividad para escoger personajes y a la cual le mandaremos a través de un Bundle
     * el nombre del usuario que va a jugar
      * @param view
     */
    private void lanzarActivityMainMenu(View view) {
        Bundle parametros = new Bundle();
        System.out.println(nom);
        parametros.putString("nombre", nom);
        Intent i = new Intent(this, CharacterActivity.class);
        i.putExtras(parametros);
        startActivity(i);
        limpiar();
    }

    /**
     * Método por el cual se ejecutará la actividad para registrar un usuario
     * @param view
     */
    public void lanzarActivityRegistro(View view) {
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
        limpiar();
    }

    /**
     * Método que se encargará de limpiar los campos de nombre de usuario y de contraseña
     */
    private void limpiar() {
        txt_nombre.setText("");
        txt_pass.setText("");
    }


}
