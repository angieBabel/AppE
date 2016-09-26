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

public class vcontadolist_adapter extends ArrayAdapter {
    private Context context;
    private ArrayList<vcontado_list> datos;

    public vcontadolist_adapter(Context context, ArrayList<vcontado_list> datos) {
        super(context, R.layout.list_ventascredito,datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_ventascontado, null);


        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        TextView nombreproducto = (TextView ) item.findViewById(R.id.productoVcont);
        nombreproducto .setText(datos.get(position).getNombreProductoVc());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView fechaVcont = (TextView) item.findViewById(R.id.fechaVcont);
        fechaVcont.setText(datos.get(position).getFechaVc());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        TextView precioVcont = (TextView) item.findViewById(R.id.precioVcont);
        precioVcont.setText(datos.get(position).getPrecioVc());

        TextView cantidadVcont= (TextView) item.findViewById(R.id.cantidadVcont);
        cantidadVcont.setText(datos.get(position).getCantidadVc());

        TextView mpagoVcont = (TextView) item.findViewById(R.id.mpagoVcont);
        mpagoVcont.setText(datos.get(position).getModopagoVc());

        TextView totalVcont = (TextView) item.findViewById(R.id.totalVcont);
        totalVcont.setText(datos.get(position).getTotalVc());

        TextView idVcont = (TextView) item.findViewById(R.id.idVcont);
        idVcont.setText(datos.get(position).getIdVc());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
