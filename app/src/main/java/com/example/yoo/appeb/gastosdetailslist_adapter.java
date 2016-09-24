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

public class gastosdetailslist_adapter extends ArrayAdapter {

    private Context context;
    private ArrayList<gastosDetail_list> datos;

    public gastosdetailslist_adapter(Context context, ArrayList<gastosDetail_list> datos) {
        super(context, R.layout.list_gastodetail, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_gastodetail, null);


        TextView nombreConcepto = (TextView ) item.findViewById(R.id.txtNombreConcepto);
        nombreConcepto.setText(datos.get(position).getNombreConcepto());

        TextView cantidadGD = (TextView) item.findViewById(R.id.txtCantidadGD);
        cantidadGD.setText(datos.get(position).getCantidadGD());

        TextView totalGD = (TextView) item.findViewById(R.id.txtTotalGD);
        totalGD.setText(datos.get(position).getTotalGD());


        TextView fechaGD = (TextView) item.findViewById(R.id.txtfechaGD);
        fechaGD.setText(datos.get(position).getFechaGD());

        TextView idGD = (TextView) item.findViewById(R.id.txtidGD);
        idGD.setText(datos.get(position).getIdGD());

        TextView idRGD = (TextView) item.findViewById(R.id.txtidRGD);
        idRGD.setText(datos.get(position).getIdRGD());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
