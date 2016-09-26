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
public class editventa extends Fragment {

    EditText nombreProd;
    EditText abono;
    String datos;
    Button editarVenta;
    String[] dataArray;
    RequestQueue requestQueueA;
    String editURL = "http://webcolima.com/wsecomapping/editVenta.php";
    int abonoD;
    int abonoP;

    public editventa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editventa, container, false);
        // Inflate the layout for this fragment
        //String datos = getArguments() != null ? getArguments().getString("datos") : "email@email.com";
        editarVenta = (Button) view.findViewById(R.id.button);
        editarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abonoP = Integer.parseInt(abono.getText().toString());

                requestQueueA = Volley.newRequestQueue(getContext());
                StringRequest request = new StringRequest(Request.Method.POST, editURL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Abono agregado con éxito",Toast.LENGTH_LONG ).show();

                        nombreProd.setText("");
                        //precioProd.setText("");
                        VentasFragment fragment = new VentasFragment();
                        Bundle args = new Bundle();
                        args.putString("datos", "Credito");
                        fragment.setArguments(args);
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
                        parameters.put("idAdeudo", getArguments().getString("idventa"));
                        parameters.put("abonoT", String.valueOf(abonoP+abonoD));
                        parameters.put("abonoP",String.valueOf(abonoP));
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
        abono = (EditText)getView().findViewById(R.id.edtabonoProd);
        nombreProd.setText(getArguments().getString("nombre"));
        abonoD = Integer.parseInt(getArguments().getString("abono"));
        //abonoP = Integer.parseInt(abono.getText().toString());

    }

    public void editProd(){
//        listaAlumno(v);
    }
}