package com.example.minijuegopmdm.inicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minijuegopmdm.ILogin;
import com.example.minijuegopmdm.LoginPresenter;
import com.example.minijuegopmdm.R;
import com.example.minijuegopmdm.bd.BdSQLite;
import com.example.minijuegopmdm.bd.BdUsuarios;

import java.util.ArrayList;

/**
 * Clase en la cual se muestra un formulario para rellenar los datos de un usuario para
 * registrarse en el juego, con opción a ir a la actividad para iniciar sesión
 */
public class RegistroActivity extends AppCompatActivity implements ILogin.Vista {

    private Button btn_inicio;
    private Button btn_registro;
    private EditText txt_nombre;
    private EditText txt_pass_1;
    private EditText txt_pass_2;
    private ImageView img;
    private String tag = "RegistroActivity";

    private ILogin.Presentador presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MinijuegoPMDM);
        setContentView(R.layout.registro);
        //Creamos una llamada a la base de datos para poder consultar los datos
        BdSQLite bdSQLite = new BdSQLite(getApplicationContext());
        SQLiteDatabase bd = bdSQLite.getWritableDatabase();
        if (bd != null) {
            Log.i(tag, "Base de datos creada");
        } else {
            Log.e(tag, "Error al crear bd");
            Toast.makeText(getApplicationContext(), "Error al crear bd", Toast.LENGTH_SHORT).show();
        }

        //Asociamos las variables a los elementos del xml
        txt_nombre = findViewById(R.id.edtxt_nombre2);
        txt_pass_1 = findViewById(R.id.edtxt_pass2);
        txt_pass_2 = findViewById(R.id.edtxt_pass3);
        img = findViewById(R.id.imgAyuda);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                builder.setTitle(R.string.info_registro);
                builder.setMessage(R.string.msg_registro);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        presentador = new LoginPresenter(this);

        btn_registro = findViewById(R.id.btn_registro2);
        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validamos a través del presentador los campos de nombre y contraseña
                if (!(presentador.esNomValido(presentador.validarNombre(txt_nombre.getText().toString()))
                        && presentador.esPassValido(presentador.validarPassword(txt_pass_1.getText().toString())))) {
                    Log.w(tag, "El nombre tendrá como mucho 12 letras sin números y la contraseña ");
                //Lanzamos un diálogo informando al usuario de que no ha introducido bien sus datos
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                    builder.setTitle(R.string.alert_registro);
                    builder.setMessage(R.string.msg_registro);
                    builder.setCancelable(false);

                    builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            dialogo1.dismiss();
                            limpiar();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                //Además de comprobar la primera validación también comprobaremos que ambas contraseñas coinciden
                } else if(txt_pass_1.equals(txt_pass_2)) {
                    Log.w(tag, "Las contraseñas no coinciden");
                    Toast.makeText(getApplicationContext(), "La contraseña tiene que ser la misma", Toast.LENGTH_SHORT).show();
                    txt_pass_1.setText("");
                    txt_pass_2.setText("");
                    txt_pass_1.requestFocus();
                } else {
                    //Hacemos una instancia de la clase donde tenemos gestionada la inserción de usuarios en la base de datos
                    BdUsuarios bdUsuarios = new BdUsuarios(RegistroActivity.this);
                    long id = bdUsuarios.insertarUsuario(txt_nombre.getText().toString(), txt_pass_1.getText().toString());
                    //Comprobamos que el id sea mayor que cero porque si lo es sabremos que se ha insertado el registro, además, avisamos al usuario de que
                    //el registro ha sido exitoso
                    if (id > 0){
                        Log.i(tag, "Se ha guardado correctamente el registro");
                        Toast.makeText(getApplicationContext(), "Registro guardado", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } else {
                        //Si esa condición anterior no se cumple, avisamos del error al usuario
                        Log.e(tag, "Error al guardar el registro");
                        Toast.makeText(getApplicationContext(), "Error al guardar el registro", Toast.LENGTH_SHORT).show();
                    }
                    // Borrar después del envío
                    limpiar();
                    Log.i(tag, "Vuelve al InicioActivity y vuelve a introducir sus datos");
                    lanzarActivityInicio(null);
                    Toast.makeText(getApplicationContext(), "Introduce de nuevo tus datos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_inicio = findViewById(R.id.btn_inicio2);
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(tag, "Cambio de activity a InicioActivity");
                lanzarActivityInicio(null);
            }
        });
    }

    /**
     * Método por el cual se ejecutará la actividad para iniciar sesión
     * @param view
     */
    public void lanzarActivityInicio (View view) {
        Intent i = new Intent(this, InicioActivity.class);
        startActivity(i);
        limpiar();
    }

    /**
     * Método que se encargará de limpiar los campos de nombre de usuario y de contraseña
     */
    private void limpiar() {
        txt_nombre.setText("");
        txt_pass_1.setText("");
        txt_pass_2.setText("");
    }

    @Override
    public boolean esNomValido(boolean val) {
        return val;
    }

    @Override
    public boolean esPassValido(boolean val) {
        return val;
    }
}
