package com.example.minijuegopmdm.juego.menus;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minijuegopmdm.R;
import com.example.minijuegopmdm.bd.BdPartidas;
import com.example.minijuegopmdm.bd.BdSQLite;
import com.example.minijuegopmdm.juego.juegos.JuegoRayoActivity;

import java.util.ArrayList;

/**
 * Clase en la cual se muestra el menú principal después de escoger el personaje del rayo, y en donde
 * aparece el logo del juego, una lista con las estadísticas y el botón para empezar a jugar
 */
public class MenuRayoActivity extends AppCompatActivity {

    private Button btn_jugar;
    private ListView listView;
    private String nom;
    private String nombre;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MinijuegoPMDM);
        setContentView(R.layout.main_menu_rayo);
        //Recogemos de la clase anterior (CharacterActivity) el nombre del usuario para poder usarlo
        //a la hora de guardar las partidas
        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            nom = parametros.getString("nombre");
        }

        nombre = nom;

        listView = findViewById(R.id.listView);
        //Creamos una llamada a la base de datos para poder consultar los datos
        BdSQLite bdSQLite = new BdSQLite(getApplicationContext());
        SQLiteDatabase bd = bdSQLite.getReadableDatabase();
        //Hacemos una instancia de la clase donde tenemos gestionada la lectura de partidas en la base de datos
        BdPartidas bdPartidas = new BdPartidas(MenuRayoActivity.this);
        //Almacenamos en un ArrayList todos los registros obtenidos
        ArrayList<String> datos = bdPartidas.mostrarPartidas();
        //Pasamos esos datos al AdaptadorLista
        AdaptadorLista adaptador = new AdaptadorLista(this, datos);
        //Asignamos esos datos al ListView que tenemos en el xml
        listView.setAdapter(adaptador);

        btn_jugar = findViewById(R.id.btn_jugar);
        btn_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarActivityJuego(null);
            }
        });
    }

    /**
     * Método por el cual se ejecutará la actividadd para jugar con el personaje del rayo y a la cual
     * le mandaremos a través de un Bundle el nombre del usuario que va a jugar
     * @param view
     */
    public void lanzarActivityJuego(View view) {
        Bundle parametros = new Bundle();
        System.out.println(nombre);
        parametros.putString("nombre", nombre);
        Intent i = new Intent(this, JuegoRayoActivity.class);
        i.putExtras(parametros);
        startActivity(i);
    }

    /**
     * Método sobreescrito del botón de volver para que esté desactivada esa función
     */
    @Override
    public void onBackPressed() {
    }

}
