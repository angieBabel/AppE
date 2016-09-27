package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by angelicabarreda on 27/09/16.
 */

public class spinnerProductos_adapter  extends ArrayAdapter {

    private Context context;

    ArrayList<spinner_productos> datos= null;

    public spinnerProductos_adapter(Context context, ArrayList<spinner_productos> datos) {
        super(context, R.layout.spinner_productos, datos);
        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View item=convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.spinner_productos, null);
        }

        TextView idprod = (TextView ) item.findViewById(R.id.idproducto);
        idprod.setText(datos.get(position).getIdproductos());

        TextView nameprod = (TextView) item.findViewById(R.id.nameProd);
        nameprod.setText(datos.get(position).getNameProducto());

        TextView precioprod = (TextView) item.findViewById(R.id.precioProd);
        precioprod.setText(datos.get(position).getPrecioProduct());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(R.layout.spinner_concepto,parent, false);
        }

        if (row.getTag() == null)
        {
            SocialNetworkHolder redSocialHolder = new SocialNetworkHolder();
            redSocialHolder.settextIdP((TextView) row.findViewById(R.id.idconcepto));
            redSocialHolder.settextnameP((TextView) row.findViewById(R.id.nameConcept));
            redSocialHolder.settextprecioP((TextView) row.findViewById(R.id.precioConcept));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        spinner_productos redSocial = datos.get(position);

        ((SocialNetworkHolder) row.getTag()).gettextIdP().setText(redSocial.getIdproductos());
        ((SocialNetworkHolder) row.getTag()).gettextnameP().setText(redSocial.getNameProducto());
        ((SocialNetworkHolder) row.getTag()).gettextprecioP().setText(redSocial.getPrecioProduct());

        return row;
    }

    private static class SocialNetworkHolder
    {

        private TextView textIdP;
        private TextView textnameP;
        private TextView textprecioP;

        public TextView gettextIdP()
        {
            return textIdP;
        }

        public void settextIdP(TextView textIdP)
        {
            this.textIdP = textIdP;
        }

        public TextView gettextnameP()
        {
            return textnameP;
        }

        public void settextnameP(TextView textnameP)
        {
            this.textnameP = textnameP;
        }

        public TextView gettextprecioP()
        {
            return textprecioP;
        }

        public void settextprecioP(TextView textprecioP)
        {
            this.textprecioP = textprecioP;
        }

    }


}
