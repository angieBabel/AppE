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
public class addnuevoproducto extends Fragment  {
    EditText nombreProd;
    EditText precioProd;
    String user= "1";
    RequestQueue requestQueueA;
    String addURL = "http://webcolima.com/wsecomapping/addProd.php";


    public addnuevoproducto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnuevoproducto, container, false);

        // Inflate the layout for this fragment
        return view;
    }
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        nombreProd = (EditText)getView().findViewById(R.id.edtNombreProd);
        precioProd = (EditText)getView().findViewById(R.id.edtPrecioProd);
        Button addPP = (Button) getView().findViewById(R.id.button);
        addPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addNewProd();
            }
        });


    }

    public void addNewProd(){
        requestQueueA = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, addURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Producto agregado con éxito",Toast.LENGTH_LONG ).show();
                nombreProd.setText("");
                precioProd.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG ).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("idUsuario",user);
                parameters.put("nombre", nombreProd.getText().toString());
                parameters.put("precio", precioProd.getText().toString());
                return parameters;
            }
        };
        requestQueueA.add(request);
    }

}
