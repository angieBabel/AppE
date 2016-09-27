package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jordigarcia on 9/25/16.
 */

public class spinner_rubros_adapter extends ArrayAdapter{
    private Context context;

    ArrayList<spinner_rubros> datos= null;


    public spinner_rubros_adapter(Context context, ArrayList<spinner_rubros> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)

        super(context, R.layout.spinner_rubros,datos);
        this.context = context;
        this.datos = datos;
    }


    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View item=convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.spinner_rubros, null);
        }

        TextView nombreRubro = (TextView ) item.findViewById(R.id.nombreRubro);
        nombreRubro .setText(datos.get(position).getNombre());

        TextView idrubro = (TextView) item.findViewById(R.id.idRubro);
        idrubro.setText(datos.get(position).getidRubro());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(R.layout.spinner_rubros,parent, false);
        }

        if (row.getTag() == null)
        {
            SocialNetworkHolder redSocialHolder = new SocialNetworkHolder();
            redSocialHolder.setTextIdrubro((TextView) row.findViewById(R.id.idRubro));
            redSocialHolder.setTextNameRubro((TextView) row.findViewById(R.id.nombreRubro));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        spinner_rubros redSocial = datos.get(position);
        ((SocialNetworkHolder) row.getTag()).getTextIdrubro().setText(redSocial.getidRubro());
        ((SocialNetworkHolder) row.getTag()).getTextNameRubro().setText(redSocial.getNombre());


        return row;
    }

/*
*
     * Holder para el Adapter del Spinner
     * @author danielme.com
     *
*/


    private static class SocialNetworkHolder
    {


        private TextView textNameRubro;
        private TextView textIdrubro;


        public TextView getTextNameRubro()
        {
            return textNameRubro;
        }

        public void setTextNameRubro(TextView textNameRubro)
        {
            this.textNameRubro = textNameRubro;
        }

        public TextView getTextIdrubro()
        {
            return textIdrubro;
        }

        public void setTextIdrubro(TextView textIdrubro)
        {
            this.textIdrubro = textIdrubro;
        }

    }
}


