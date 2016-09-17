package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class editProducto extends Fragment {

    EditText nombreProd;
    EditText precioProd;
    String datos;

    public editProducto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_producto, container, false);
        // Inflate the layout for this fragment
        //String datos = getArguments() != null ? getArguments().getString("datos") : "email@email.com";
        datos = getArguments().getString("datos");
        Button agregar = (Button) view.findViewById(R.id.button);

        return view;
    }

    public void editProd(){
        
    }


    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        Toast.makeText(getContext(),datos,Toast.LENGTH_LONG ).show();
        nombreProd = (EditText)getView().findViewById(R.id.edtNombreProd);
        precioProd = (EditText)getView().findViewById(R.id.edtPrecioProd);

        String[] dataArray = datos.split(",");
        nombreProd.setText(dataArray[0]);
        precioProd.setText(dataArray[1].trim());
        //lista = (ListView)getView().findViewById(R.id.listViewProductos);

    }
}
