package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jordigarcia on 9/25/16.
 */

/*public class spinner_rubros_adapter extends ArrayAdapter<spinner_rubros> {
    private Context context;

    List<spinner_rubros> datos = null;

    public spinner_rubros_adapter(Context context, List<spinner_rubros> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)

        super(context, R.layout.spinner_rubros,datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.spinner_rubros, null);


        TextView nombreRubroCG = (TextView ) item.findViewById(R.id.idRubro);
        nombreRubroCG.setText(datos.get(position).getidRubro());

        TextView conceptoCG = (TextView) item.findViewById(R.id.nombreRubro);
        conceptoCG.setText(datos.get(position).getNombre());


        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }
}*/
public class spinner_rubros_adapter extends ArrayAdapter<spinner_rubros> {
    private Context context;

    List<spinner_rubros> datos = null;


    public spinner_rubros_adapter(Context context, List<spinner_rubros> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)

        super(context, R.layout.spinner_rubros,datos);
        this.context = context;
        this.datos = datos;
    }

    public spinner_rubros_adapter(Context context, int simple_spinner_item, List<spinner_rubros> datos)
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
        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_rubros,null);
        }
        ((TextView) convertView.findViewById(R.id.nombreRubro)).setText(datos.get(position).getNombre());
        ((TextView) convertView.findViewById(R.id.idRubro)).setText(datos.get(position).getidRubro());

        return convertView;
    }

    //gestiona la lista usando el View Holder Pattern. Equivale a la típica implementación del getView
    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.spinner_rubros, parent, false);
        }

        if (row.getTag() == null)
        {
            SocialNetworkHolder redSocialHolder = new SocialNetworkHolder();
            redSocialHolder.setTextView((TextView) row.findViewById(R.id.idRubro));
            redSocialHolder.setTextView((TextView) row.findViewById(R.id.nombreRubro));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
         spinner_rubros redSocial = datos.get(position);
        ((SocialNetworkHolder) row.getTag()).getTextView().setText(redSocial.getidRubro());
        ((SocialNetworkHolder) row.getTag()).getTextView().setText(redSocial.getNombre());


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



        private TextView textView;


        public TextView getTextView()
        {
            return textView;
        }

        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }
}


