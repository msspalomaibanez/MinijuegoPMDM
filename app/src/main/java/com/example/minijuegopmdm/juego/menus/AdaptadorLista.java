package com.example.minijuegopmdm.juego.menus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minijuegopmdm.R;

import java.util.ArrayList;

/**
 * Clase en la cual se gestiona como se van a mostrar los datos de las partidas jugadas en el ListView
 * del menú principal
 */
public class AdaptadorLista extends BaseAdapter {
    private final Activity actividad;
    private ArrayList<String> datos;

    public AdaptadorLista(Activity actividad, ArrayList<String> datos) {
        super();
        this.actividad = actividad;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = actividad.getLayoutInflater();
        View view = inflater.inflate(R.layout.elemento_lista, null, true);

        TextView txt = view.findViewById(R.id.texto_datos);
        txt.setText(datos.get(position));

        return view;
    }
    @Override
    public int getCount() {
        return datos.size();
    }
    @Override
    public Object getItem(int arg0) {
        return datos.get(arg0);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}
