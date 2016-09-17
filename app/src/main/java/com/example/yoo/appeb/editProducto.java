package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class editProducto extends Fragment {

    private EditText nombreProd;
    private EditText precioProd;

    public editProducto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container, false);
        // Inflate the layout for this fragment
        //String datos = getArguments() != null ? getArguments().getString("datos") : "email@email.com";
        String datos = getArguments().getString("datos");
        nombreProd = (EditText)getView().findViewById(R.id.edtNombreProd);
        precioProd = (EditText)getView().findViewById(R.id.edtPrecioProd);

        String[] dataArray = datos.split(",");
        nombreProd.setText(dataArray[0].trim());
        precioProd.setText(dataArray[1].trim());
        //lista = (ListView)getView().findViewById(R.id.listViewProductos);
        return view;
    }

}
