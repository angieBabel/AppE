package com.example.yoo.appeb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jordigarcia on 9/23/16.
 */

public class productoslistadapter extends ArrayAdapter {
    private Context context;
    private ArrayList<productos_list> datos;

    public productoslistadapter(Context context, ArrayList<productos_list> datos) {
        //super(context, resource);
        super(context, R.layout.list_productos, datos);
        // Guardamos los parámetros en variables de clase.
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.list_productos, null);


        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        TextView nombrep = (TextView ) item.findViewById(R.id.txtNombreProductos);
        nombrep.setText(datos.get(position).getNombre());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView preciop = (TextView) item.findViewById(R.id.txtprecioProductos);
        preciop.setText(datos.get(position).getprecioProducto());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        TextView idp = (TextView) item.findViewById(R.id.txtidProductos);
        idp.setText(datos.get(position).getIdProducto());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

}
