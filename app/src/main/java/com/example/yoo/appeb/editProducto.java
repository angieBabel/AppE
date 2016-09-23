package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class editProducto extends Fragment {

    EditText nombreProd;
    EditText precioProd;
    String datos;
    Button editarProd;
    String[] dataArray;
    RequestQueue requestQueueA;
    String editURL = "http://webcolima.com/wsecomapping/editProd.php";

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
        editarProd = (Button) view.findViewById(R.id.button);
        editarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueueA = Volley.newRequestQueue(getContext());
                StringRequest request = new StringRequest(Request.Method.POST, editURL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Producto modificado con éxito",Toast.LENGTH_LONG ).show();
                        nombreProd.setText("");
                        precioProd.setText("");
                        ProductoFragment fragment = new ProductoFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("idProd", dataArray[0]);
                        parameters.put("nombre", nombreProd.getText().toString());
                        parameters.put("precio", precioProd.getText().toString());
                        return parameters;
                    }
                };
                requestQueueA.add(request);
            }
        });

        return view;
    }


    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        nombreProd = (EditText)getView().findViewById(R.id.edtNombreProd);
        precioProd = (EditText)getView().findViewById(R.id.edtPrecioProd);

        dataArray = datos.split(",");
        nombreProd.setText(dataArray[1]);
        precioProd.setText(dataArray[2].trim());
        //lista = (ListView)getView().findViewById(R.id.listViewProductos);

    }

    public void editProd(){
//        listaAlumno(v);
    }
}
