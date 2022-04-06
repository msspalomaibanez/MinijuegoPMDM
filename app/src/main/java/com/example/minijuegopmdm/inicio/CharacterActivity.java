package com.example.minijuegopmdm.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.minijuegopmdm.juego.menus.MenuEstrellaActivity;
import com.example.minijuegopmdm.juego.menus.MenuRayoActivity;
import com.example.minijuegopmdm.R;

import rb.popview.PopField;

/**
 * Clase en la cual se muestran dos personajes a los que poder elegir a la hora de jugar. Tras elegir
 * uno de ellos se desplegará un Dialog para confimar la decisión y tras ello se pasará a un menú principal
 * por cada personaje escogido
 */
public class CharacterActivity extends AppCompatActivity {

    private ImageView popImageFem;
    private ImageView popImageMasc;
    private String nom;
    private String nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MinijuegoPMDM);
        setContentView(R.layout.character_menu);
        //Recogemos de la clase anterior (InicioActivity) el nombre del usuario para poder usarlo
        //a la hora de guardar las partidas
        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            nom = parametros.getString("nombre");
        }

        nombre = nom;

        //Instanciamos un objeto de tipo PopField que nos servirá para la animación al escoger el personaje
        final PopField popField = PopField.attach2Window(this);
        //Asociamos las variables a los elementos del xml
        popImageFem = findViewById(R.id.imageFemBlur);

        popImageFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se ejecutará la animación
                popField.popView(v);
                //Lanzamos el Dialog en el cual preguntamos al usuario si esta seguro con su decisión
                //1. Si escoge seguir le llevaremos al menú principal del rayo
                //2. Si escoge volver "reiniciaremos" la animación y le dejaremos volver a elegir
                AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                builder.setTitle(R.string.opcion_rayo);
                builder.setMessage(R.string.msg_continuar);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptarRayo();
                    }
                });
                builder.setNegativeButton(R.string.opcion_volver, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        popField.popView(v, v, true);
                        popImageFem.setVisibility(View.VISIBLE);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //Asociamos las variables a los elementos del xml
        popImageMasc = findViewById(R.id.imageMascBlur);

        popImageMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se ejecutará la animación
                popField.popView(v);
                //Lanzamos el Dialog en el cual preguntamos al usuario si esta seguro con su decisión
                //1. Si escoge seguir le llevaremos al menú principal de la estrella
                //2. Si escoge volver "reiniciaremos" la animación y le dejaremos volver a elegir
                AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                builder.setTitle(R.string.opcion_estrella);
                builder.setMessage(R.string.msg_continuar);
                builder.setCancelable(false);

                builder.setPositiveButton(R.string.opcion_aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        aceptarEstrella();
                    }
                });
                builder.setNegativeButton(R.string.opcion_volver, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        popField.popView(v, v, true);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Método por el cual se ejecutará la actividad del menú principal del rayo al cual le mandaremos
     * a través de un Bundle el nombre del usuario que hemos recogido de la anterior actividad
     */
    public void aceptarRayo() {
        Bundle parametros = new Bundle();
        System.out.println(nom);
        parametros.putString("nombre", nombre);
        Intent i = new Intent(this, MenuRayoActivity.class);
        i.putExtras(parametros);
        startActivity(i);
    }

    /**
     * Método por el cual se ejecutará la actividad del menú principal de la estrella al cual le mandaremos
     * a través de un Bundle el nombre del usuario que hemos recogido de la anterior actividad
     */
    public void aceptarEstrella() {
        Bundle parametros = new Bundle();
        parametros.putString("nombre", nombre);
        Intent i = new Intent(this, MenuEstrellaActivity.class);
        i.putExtras(parametros);
        startActivity(i);
    }
}