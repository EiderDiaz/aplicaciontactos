package com.example.lomeli.listview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter{

    Context contexto;
    ArrayList <Contactos>  listaContactos;

    public Adaptador(Context contexto, ArrayList<Contactos> listaContactos) {
        this.contexto = contexto;
        this.listaContactos = listaContactos;
    }

    @Override
    public int getCount() {
        return listaContactos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaContactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View renglon = convertView;

        if (renglon == null) { //la vista viene desinflada
            LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            renglon = inflador.inflate(R.layout.renglon, parent, false);

        }
            TextView tvNombre = (TextView) renglon.findViewById(R.id.textViewNombre);
            TextView tvTelefono = (TextView) renglon.findViewById(R.id.textViewTelefono);
            TextView tvEmail = (TextView) renglon.findViewById(R.id.textViewCorreo);

            tvNombre.setText(listaContactos.get(position).getNombre());
            tvTelefono.setText(listaContactos.get(position).getCelular());
            tvEmail.setText(listaContactos.get(position).getEmail());


        return renglon;
    }
}
