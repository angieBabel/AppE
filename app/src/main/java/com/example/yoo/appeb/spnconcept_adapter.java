package com.example.yoo.appeb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by angelicabarreda on 26/09/16.
 */

public class spnconcept_adapter extends ArrayAdapter{

    private Context context;

   ArrayList<spnconcept> datos= null;

    public spnconcept_adapter(Context context, ArrayList<spnconcept> datos) {
        super(context, R.layout.spinner_concepto, datos);
        this.context = context;
        this.datos = datos;
    }

    /*public spnconcept_adapter(FragmentActivity activity, int simple_spinner_item, List<spnconcept> listaCatGastos) {
        super();
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View item=convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            item = inflater.inflate(R.layout.spinner_concepto, null);
            //convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_concepto,null);
        }

        TextView idconcep = (TextView ) item.findViewById(R.id.idconcepto);
        idconcep.setText(datos.get(position).getIdconceptosp());

        TextView nameconcepto = (TextView) item.findViewById(R.id.nameConcept);
        nameconcepto.setText(datos.get(position).getConceptosp());

        TextView precioconcepto = (TextView) item.findViewById(R.id.precioConcept);
        precioconcepto.setText(datos.get(position).getPreciosp());

        // Devolvemos la vista para que se muestre en el ListView.
        return item;
        /*((TextView) convertView.findViewById(R.id.idconcepto)).setText(datos.get(position).getIdconceptosp());
        ((TextView) convertView.findViewById(R.id.nameConcept)).setText(datos.get(position).getConceptosp());
        ((TextView) convertView.findViewById(R.id.precioConcept)).setText(datos.get(position).getPreciosp());

        return convertView;*/
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(R.layout.spinner_concepto,parent, false);
            /*LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.spinner_concepto, parent, false);*/
        }

        if (row.getTag() == null)
        {
            SocialNetworkHolder redSocialHolder = new SocialNetworkHolder();
            redSocialHolder.settextIdcp((TextView) row.findViewById(R.id.idconcepto));
            redSocialHolder.settextcp((TextView) row.findViewById(R.id.nameConcept));
            redSocialHolder.settextpreciocp((TextView) row.findViewById(R.id.precioConcept));
            row.setTag(redSocialHolder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        spnconcept redSocial = datos.get(position);

        ((SocialNetworkHolder) row.getTag()).gettextIdcp().setText(redSocial.getIdconceptosp());
        ((SocialNetworkHolder) row.getTag()).gettextcp().setText(redSocial.getConceptosp());
        ((SocialNetworkHolder) row.getTag()).gettextpreciocp().setText(redSocial.getPreciosp());

        return row;
    }

    private static class SocialNetworkHolder
    {

        private TextView textIdcp;
        private TextView textcp;
        private TextView textpreciocp;

        public TextView gettextIdcp()
        {
            return textIdcp;
        }

        public void settextIdcp(TextView textIdcp)
        {
            this.textIdcp = textIdcp;
        }

        public TextView gettextcp()
        {
            return textcp;
        }

        public void settextcp(TextView textcp)
        {
            this.textcp = textcp;
        }

        public TextView gettextpreciocp()
        {
            return textpreciocp;
        }

        public void settextpreciocp(TextView textpreciocp)
        {
            this.textpreciocp = textpreciocp;
        }

    }

}
