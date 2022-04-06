package com.example.minijuegopmdm.juego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.minijuegopmdm.R;
import com.example.minijuegopmdm.juego.Casilla;

import java.util.ArrayList;

/**
 * Clase en la cual se gestiona la forma en la que se van a mostrar las casillas en el tablero del juego
 */
public class AdaptadorJuego extends BaseAdapter {

    private Context contexto;


    public ArrayList<Casilla> arraylist;

    public AdaptadorJuego(Context contexto, ArrayList<Casilla> arraylist) {
        this.contexto = contexto;
        this.arraylist = arraylist;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.elemento_casilla, null, true);
        }

        ImageView imageView = convertView.findViewById(R.id.casilla);
        imageView.setImageResource(arraylist.get(position).getImagen());


        return convertView;
    }
    @Override
    //Devuelve el numero de elementos del grid
    public int getCount() {
        return arraylist.size();
    }
    @Override
    public Casilla getItem(int i) {
        return arraylist.get(i);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}
