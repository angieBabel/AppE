package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angelicabarreda on 24/09/16.
 */

public class vcreditolist_adapter extends ArrayAdapter{
    private Context context;
    private ArrayList<vcredito_list> datos;


    public vcreditolist_adapter(Context context, ArrayList<vcredito_list> datos) {
        super(context, R.layout.list_ventascredito,datos);
        //super(context, R.layout.list_ventascredito,datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_ventascredito, null);
        /*   this.nombreProductoVcred = nombreProductoVcred;
        this.fechaVcred= fechaVcred;
        this.deudorVcred= deudorVcred;
        this.deudaVcred = deudaVcred;
        this.abonoVcred = abonoVcred;
        this.abonoperiodoVcred = abonoperiodoVcred;
        this.idVcred = idVcred;*/



        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        TextView nombreproducto = (TextView ) item.findViewById(R.id.productoVcred);
        nombreproducto .setText(datos.get(position).getnombreProductoVcred());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView fechaVcred = (TextView) item.findViewById(R.id.fechaVcred);
        fechaVcred.setText(datos.get(position).getfechaVcred());

        TextView deudorVcred = (TextView) item.findViewById(R.id.deudorVcred);
        deudorVcred.setText(datos.get(position).getdeudorVcred());

        TextView deudaVcred = (TextView) item.findViewById(R.id.deudaVcred);
        deudaVcred.setText(datos.get(position).getdeudaVcred());

        TextView abonopVcred = (TextView) item.findViewById(R.id.abonoPeriodoVcred);
        abonopVcred.setText(datos.get(position).getabonoperiodoVcred());

        TextView abonoVcred = (TextView) item.findViewById(R.id.abonoVcred);
        abonoVcred.setText(datos.get(position).getabonoVcred());

        TextView idVcred = (TextView) item.findViewById(R.id.idVcred);
        idVcred.setText(datos.get(position).getidVcred());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}

