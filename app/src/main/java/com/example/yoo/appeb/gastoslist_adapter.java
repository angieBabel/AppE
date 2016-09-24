package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 23/09/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class gastoslist_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<gastos_list> datos;


    public gastoslist_adapter(Context context, ArrayList<gastos_list> datos) {
        super(context,R.layout.list_gastos, datos);

        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_gastos, null);


        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        TextView nombreRubro = (TextView ) item.findViewById(R.id.txtNombreRubro);
        nombreRubro.setText(datos.get(position).getNombreRubro());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView totalGasto = (TextView) item.findViewById(R.id.txtTotalGasto);
        totalGasto.setText(datos.get(position).getTotalGasto());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        TextView idRubro = (TextView) item.findViewById(R.id.txtidGastos);
        idRubro.setText(datos.get(position).getIdRubro());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
