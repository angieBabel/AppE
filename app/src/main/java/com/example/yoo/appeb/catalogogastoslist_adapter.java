package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angelicabarreda on 23/09/16.
 */

public class catalogogastoslist_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<catalogoGastos_list> datos;

    public catalogogastoslist_adapter(Context context, ArrayList<catalogoGastos_list> datos) {
        super(context,R.layout.list_catalogogasto, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_catalogogasto, null);


        TextView nombreRubroCG = (TextView ) item.findViewById(R.id.txtNombreRubroCG);
        nombreRubroCG.setText(datos.get(position).getNombreRubroCG());

        TextView conceptoCG = (TextView) item.findViewById(R.id.txtConceptoCG);
        conceptoCG.setText(datos.get(position).getConceptoCG());

        TextView costoCG = (TextView) item.findViewById(R.id.txtcostoCG);
        costoCG.setText(datos.get(position).getCostoCG());


        TextView idCG = (TextView) item.findViewById(R.id.txtidCG);
        idCG.setText(datos.get(position).getIdCG());


        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
