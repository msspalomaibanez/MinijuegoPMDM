package com.example.minijuegopmdm.juego.juegos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minijuegopmdm.R;
import com.example.minijuegopmdm.bd.BdPartidas;
import com.example.minijuegopmdm.bd.BdSQLite;
import com.example.minijuegopmdm.inicio.RegistroActivity;
import com.example.minijuegopmdm.juego.AdaptadorJuego;
import com.example.minijuegopmdm.juego.Casilla;
import com.example.minijuegopmdm.juego.menus.MenuRayoActivity;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase en la cual se muestra el tablero del juego, un contador de tiradas y un dado con el que
 * jugar
 */
public class JuegoRayoActivity extends AppCompatActivity {


    private GridView grid;
    private ImageView dado;
    private String inicio;
    private String fin;
    private String nom;
    private String nombre;
    private Cursor fila;
    private TextView txt_tiradas;
    private int tiradas = 1;
    private ArrayList<Casilla> casillas;
    private ArrayList<String> posiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_MinijuegoPMDM);
        setContentView(R.layout.grid_juego);
        //Recogemos de la clase anterior (MenuRayoActivity) el nombre del usuario para poder usarlo
        //a la hora de guardar las partidas
        Bundle parametros = this.getIntent().getExtras();
        if(parametros != null){
            nom = parametros.getString("nombre");
        }

        nombre = nom;
        //Almacenamos el momento de inicio de la partida
        inicio = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
        dado = findViewById(R.id.img_dado);
        txt_tiradas = findViewById(R.id.txt_tiradas);
        casillas = new ArrayList(100);

        crearTablero();

        grid = findViewById(R.id.idMiGrid);
        //Hacemos una instancia del adaptador que dar?? formato al tablero
        AdaptadorJuego adapter = new AdaptadorJuego(this, casillas);
        grid.setAdapter(adapter);

        posiciones = new ArrayList<>();

        //Lanzamos el dialogo con el tutorial del juego
        AlertDialog.Builder builder = new AlertDialog.Builder(JuegoRayoActivity.this);
        builder.setTitle(R.string.tutorial_titulo);
        builder.setMessage(R.string.tutorial_contenido);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.tutorial_opcion, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        dado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Deshabilitamos el tablero
                grid.setEnabled(false);
                //Almacenamos en una variable el n??mero del dado
                int resultado = tirarDado();
                //Almacenamos en una variable la posici??n del h??roe anterior a moverse
                int posicion = siguienteCasillaHeroe(resultado, adapter);
                //Ejecutamos el m??todo que realiza el movimiento
                movimientoHeroe(adapter, grid, posicion);
            }
        });
    }

    /**
     * M??todo por el cual mediante de dos bucles pintamos todo el tablero con las casillas normales
     * y pintamos la primera casilla con el h??roe y la ??ltima con el tesoro
     */
    public void crearTablero() {
        int x;
        int y;
        //Recogemos los tres tipos de casillas en sus respectivas variables
        int suelo = getResources().getIdentifier("casilla", "drawable", getPackageName());
        int heroe = getResources().getIdentifier("heroe_rayo", "drawable", getPackageName());
        int tesoro = getResources().getIdentifier("tesoro", "drawable", getPackageName());

        //Primero creamos un bucle que se encargue de recorrer las posiciones de las filas (x)
        for (x = 0; x < 10; x++) {
            //Y dentro del mismo creamos otro bucle que se ocupe de recorrer las posiciones de las columnas (y)
            for (y = 0; y < 10; y++) {
                //Si resulta que la posici??n es 00 asignamos la foto del h??roe a esa posici??n
                if (x == 0 && y == 0) {
                    Casilla casillaHeroe = new Casilla(x, y, heroe);
                    casillas.add(casillaHeroe);
                } else if (x == 9 && y == 9) { //Si resulta que la posici??n es 99 asignamos la foto del tesoro
                    Casilla casillaTesoro = new Casilla(x, y, tesoro);
                    casillas.add(99, casillaTesoro);
                } else { //Para el resto de posiciones asignamos la foto de la casilla base
                    Casilla casillaBase = new Casilla(x, y, suelo);
                    casillas.add(casillaBase);
                }

            }
        }
    }

    /**
     * M??todo por el cual obtenemos un n??mero random del 1 al 6 y lo asignamos con su imagen correspondiente
     * para mostrarlo en el dado
     * @return int que ser?? el n??mero resultante del dado
     */
    public int tirarDado() {
        int resultado;
        int id;
        Random ran = new Random();
        resultado = ran.nextInt(6) + 1;

        switch (resultado) {
            case 1:
                id = getResources().getIdentifier("dado1", "drawable", getPackageName());
                dado.setImageResource(id);
                break;

            case 2:
                id = getResources().getIdentifier("dado2", "drawable", getPackageName());
                dado.setImageResource(id);
                break;

            case 3:
                id = getResources().getIdentifier("dado3", "drawable", getPackageName());
                dado.setImageResource(id);
                break;

            case 4:
                id = getResources().getIdentifier("dado4", "drawable", getPackageName());
                dado.setImageResource(id);
                break;

            case 5:
                id = getResources().getIdentifier("dado5", "drawable", getPackageName());
                dado.setImageResource(id);
                break;

            case 6:
                id = getResources().getIdentifier("dado6", "drawable", getPackageName());
                dado.setImageResource(id);
                break;
        }
        //Por cada vez que tiramos el dado sumamos una tirada m??s al contador
        txt_tiradas.setText("Tiradas: " + tiradas++);
        return resultado;
    }

    /**
     * M??todo por el cual se??alamos la o las casillas (pueden variar de 1 a 4) a las que el h??roe se puede
     * desplazar teniendo en cuenta el resultado de haber tirado el dado y la posici??n en la que se encuentre
     * el h??roe
     * @param dado entero del 1 al 6 obtenido como resultado de tirar el dado
     * @param adapter adaptador a trav??s del cual obtendremos los atributos de la clase Casilla y que nos
     *                servir?? para actualizar el tablero
     * @return entero que devolver?? la posici??n actual del h??roe antes de realizar alg??n movimiento
     */
    public int siguienteCasillaHeroe(int dado, AdaptadorJuego adapter) {
        String posHeroe = "";
        int fila = 0;
        int columna = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            //Buscamos la casilla del h??roe a trav??s de su foto
            if (adapter.getItem(i).getImagen() == R.drawable.heroe_rayo) {
                fila = adapter.getItem(i).getFila();
                columna = adapter.getItem(i).getColumna();
                String f = String.valueOf(fila);
                String c = String.valueOf(columna);
                //Almacenamos la posici??n para devolverla al final
                posHeroe = f + c;
            }
        }

        //Dejamos establecido en las variables de movimiento las operaciones a realizar con el dado
        int derecha = columna + dado;
        int izquierda = columna - dado;
        int arriba = fila - dado;
        int abajo = fila + dado;

        for (int i = 0; i < adapter.getCount(); i++) {

            //Comprobaci??n para establecer la casilla de selecci??n hacia la derecha
            if (adapter.getItem(i).getFila() == fila && adapter.getItem(i).getColumna() == derecha) {
                adapter.getItem(i).setImagen(R.drawable.seleccion);
            }

            //Comprobaci??n para establecer la casilla de selecci??n hacia abajo
            if (adapter.getItem(i).getFila() == abajo && adapter.getItem(i).getColumna() == columna) {
                adapter.getItem(i).setImagen(R.drawable.seleccion);
            }

            //Comprobaci??n para establecer la casilla de selecci??n hacia la izquierda
            if (adapter.getItem(i).getFila() == fila && adapter.getItem(i).getColumna() == izquierda) {
                adapter.getItem(i).setImagen(R.drawable.seleccion);
            }
            //Comprobaci??n para establecer la casilla de selecci??n hacia arriba
            if (adapter.getItem(i).getFila() == arriba && adapter.getItem(i).getColumna() == columna) {
                adapter.getItem(i).setImagen(R.drawable.seleccion);
            }
        }
        //Refrescamos el tablero volviendole a pasar el adaptador ahora modificado
        grid.setAdapter(adapter);
        return Integer.valueOf(posHeroe);
    }

    /**
     * M??todo por el cual se lleva a cabo el movimiento del h??roe a la casilla seleccionada escogida por el
     * usuario
     * @param adapter adaptador a trav??s del cual obtendremos los atributos de la clase Casilla y que nos
     *                servir?? para actualizar el tablero
     * @param grid el tablero al cual le establecemos el OnClick y las funcionalidades necesarias para llevar
     *             a cabo el movimiento del h??roe
     * @param posHeroe la posici??n en la que se encuentra el h??roe previa al movimiento
     */
    public void movimientoHeroe(AdaptadorJuego adapter, GridView grid, int posHeroe) {
        for (int i = 0; i < grid.getCount(); i++) {
            //Localizamos las casillas a las que el h??roe se puede desplazar
            if (adapter.getItem(i).getImagen() == R.drawable.seleccion) {
                grid.setEnabled(true);
                dado.setClickable(false);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Compruebo las casillas a las que se puede mover el heroe (solo las de seleccion)
                        if (adapter.getItem(i).getImagen() == R.drawable.seleccion) {
                            adapter.getItem(i).setImagen(R.drawable.heroe_rayo);
                            int fi = adapter.getItem(i).getFila();
                            int col = adapter.getItem(i).getColumna();
                            String f = String.valueOf(fi);
                            String c = String.valueOf(col);
                            String posicion = f + c;
                            //A??adimos al ArrayList de posiciones la posici??n escogida
                            posiciones.add(posicion);

                            //Compruebo la casilla que ha quedado con la foto del heroe anterior y la repinto
                            if (adapter.getItem(posHeroe).getImagen() == R.drawable.heroe_rayo) {
                                adapter.getItem(posHeroe).setImagen(R.drawable.casilla);
                            }
                            //Busco mediante un bucle las otras casillas que hayan quedado marcadas pero no se hayan seleccionado
                            for (int j = 0; j < grid.getCount(); j++) {
                                if (adapter.getItem(j).getImagen() == R.drawable.seleccion) {
                                    adapter.getItem(j).setImagen(R.drawable.casilla);
                                }
                            }
                            //Actualizamos el teclado con el movimiento del h??roe y limpiando el resto de casillas
                            grid.setAdapter(adapter);
                            //En el caso de que la posici??n a la que llegue el h??roe sea 99
                            if (adapter.getItem(i).getFila() == 9 && adapter.getItem(i).getColumna() == 9) {
                                //Pintamos todo el tablero con la imagen del h??roe
                                for (int j = 0; j < grid.getCount(); j++) {
                                    adapter.getItem(j).setImagen(R.drawable.heroe_rayo);
                                }
                                //Obtenemos el n??mero total de tiradas
                                String total_tiradas = txt_tiradas.getText().toString();
                                String[] parts = total_tiradas.split(" ");
                                String t = parts[1];

                                int tiradasTotal = Integer.valueOf(t);
                                //Obtenemos todas las posiciones por las que ha pasado el h??roe
                                String pos = String.valueOf(posiciones);
                                //Creamos una llamada a la base de datos para poder ingresar el resultado de una partida
                                BdSQLite bdSQLite = new BdSQLite(getApplicationContext());
                                SQLiteDatabase bd = bdSQLite.getWritableDatabase();
                                //Obtenemos el tiempo de final de la partida
                                fin = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                                //Hacemos una instancia de la clase donde tenemos gestionada la inserci??n de partidas
                                BdPartidas bdPartidas = new BdPartidas(JuegoRayoActivity.this);

                                long id = bdPartidas.insertarPartida(nombre, inicio, fin, tiradasTotal, pos);
                                //Comprobamos que el id sea mayor que cero porque si lo es sabremos que se ha insertado el registro, adem??s, avisamos al usuario de que
                                //el registro ha sido exitoso
                                if (id > 0 ){
                                    Log.i("a", "Se ha guardado la partida");
                                    String superado = getString(R.string.txt_superado);
                                    String no_superado = getString(R.string.txt_no_superado);
                                    fila = bd.rawQuery("select min(tiradas) from t_partidas" , null);
                                    try {
                                        if(fila.moveToFirst()) {
                                            int record = fila.getInt(0);
                                            String r = String.valueOf(record);
                                            //Dependiendo de si el n??mero de tiradas de la partida es inferior al menor que haya en la base da datos
                                            if (tiradasTotal <= record) {
                                                lanzarPopUp(superado, t);
                                            } else {
                                                lanzarPopUp(no_superado, r);
                                            }
                                        }
                                    } catch (Exception ex) {
                                        System.out.println(ex.getMessage());
                                    }

                                } else {
                                    //Si esa condici??n anterior no se cumple, avisamos del error al usuario
                                    Log.e("ups", "Error al guardar la partida");
                                    volverMain(null);
                                    Toast.makeText(getApplicationContext(), "Error al guardar la partida", Toast.LENGTH_SHORT).show();
                                }

                            }
                            //Actualizamos el tablero con los cambios que haya sufrido
                            grid.setAdapter(adapter);
                            //Reactivamos el click del tablero
                            dado.setClickable(true);
                        }
                    }
                });
            }
        }
    }

    /**
     * M??todo por el cual lanzamos un Dialog al finalizar la partida en donde se mostrar?? el n??mero de tiradas que ha realizado el usuario,
     * el r??cord que haya registrado en la base de datos y desde el cual se podr?? volver a jugar o volver al men?? principal
     * @param superado  mensaje que depender?? de si el r??cord ha sido superado o no
     * @param record n??mero de tiradas r??cord obtenido de la consulta a la base de datos
     */
    public void lanzarPopUp(String superado, String record) {
        String total_tiradas = txt_tiradas.getText().toString();
        String rec = getString(R.string.info_record);
        String info = superado + "\n" + total_tiradas + "\n" + rec + " " + record;
        AlertDialog.Builder builder = new AlertDialog.Builder(JuegoRayoActivity.this);
        builder.setTitle(R.string.txt_victoria);
        builder.setMessage(info);
        builder.setCancelable(false);
        //Si pulsa a jugar de nuevo volvemos a lanzar esta misma actividad
        builder.setPositiveButton(R.string.opcion_reiniciar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                reiniciarPartida(null);
            }
        });
        //Si pulsa volver lanzaremos la actividad del men??
        builder.setNegativeButton(R.string.opcion_volver, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                volverMain(null);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * M??todo por el cual ejecutamos la actividad para volver al men?? principal y a la cual le pasamos a trav??s de un Bundle el
     * nombre del usuario que est?? jugando
     * @param view
     */
    public void volverMain(View view) {
        Bundle parametros = new Bundle();
        parametros.putString("nombre", nombre);
        Intent i = new Intent(this, MenuRayoActivity.class);
        i.putExtras(parametros);
        startActivity(i);
    }

    /**
     * M??todo por el cual ejecutamos de nuevo la actividad de juego con la estrella y a la cual le pasamos a trav??s de un Bundle el
     * nombre del usuario que est?? jugando
     * @param view
     */
    public void reiniciarPartida(View view) {
        Bundle parametros = new Bundle();
        parametros.putString("nombre", nombre);
        Intent i = new Intent(this, JuegoRayoActivity.class);
        i.putExtras(parametros);
        startActivity(i);
    }

    /**
     * M??todo sobreescrito del bot??n de volver para que est?? desactivada esa funci??n
     */
    @Override
    public void onBackPressed() {
    }
}
