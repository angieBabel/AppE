package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angelicabarreda on 26/09/16.
 */

public class adapterspi extends ArrayAdapter {

    private Context context;
    private ArrayList<spnconcept> datos;

    public adapterspi(Context context, ArrayList<spnconcept> datos) {
        super(context, R.layout.spinner_concepto, datos);
        this.context = context;
        this.datos = datos;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.spinner_concepto, null);


        // A partir de la vista, recogeremos los controles que contiene para
        // poder manipularlos.
        // Recogemos el ImageView y le asignamos una foto.
        TextView idconcep = (TextView ) item.findViewById(R.id.idconcepto);
        idconcep.setText(datos.get(position).getIdconceptosp());

        // Recogemos el TextView para mostrar el nombre y establecemos el
        // nombre.
        TextView nameconcepto = (TextView) item.findViewById(R.id.nameConcept);
        nameconcepto.setText(datos.get(position).getConceptosp());

        // Recogemos el TextView para mostrar el número de celda y lo
        // establecemos.
        TextView precioconcepto = (TextView) item.findViewById(R.id.precioConcept);
        precioconcepto.setText(datos.get(position).getPreciosp());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}
