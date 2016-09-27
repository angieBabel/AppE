package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class editConcepto extends Fragment {

    EditText nombreConcepto;// edtNombreConcepto;
    EditText precioConcpeto;//           edtPrecioConcepto
    String rubroExistente; //spinnerRE
    String datos;

    ArrayList<String> listaRubros= new ArrayList<String>();
    ArrayAdapter<String> dataAdapter;
    String[] dataArray;
    Spinner spinner;
    RequestQueue requestQueueSend;
    RequestQueue requestQueueGetRubros;
    String editURL = "http://webcolima.com/wsecomapping/editConcepto.php";
    String getURL = "http://webcolima.com/wsecomapping/rubros.php";
    String user= "1";
    String nombreR;
    String concepto;
    String costo;
    String idCG;


    public editConcepto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_concepto, container, false);
        //text = (TextView) view.findViewById(R.id.Productos);
        nombreR = getArguments().getString("rubro");
        concepto = getArguments().getString("concepto");
        costo =getArguments().getString("costo");
        idCG =getArguments().getString("ideCG");


        //datos = getArguments().getString("datos");
        //idC+","+rubro+", "+nombre+", "+costo
        Button editarProd = (Button) view.findViewById(R.id.button);
        editarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editConcept();
            }

        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        nombreConcepto = (EditText)getView().findViewById(R.id.edtNombreConcepto);
        precioConcpeto = (EditText)getView().findViewById(R.id.edtPrecioConcepto);

        //dataArray = datos.split(",");
        //idC+","+rubro+", "+nombre+", "+costo
        //String myString = dataArray[0]+ "\n" +dataArray[1]; //the value you want the position for


        nombreConcepto.setText(concepto);
        precioConcpeto.setText(costo);
        listaRubros.clear();
        spinner = (Spinner)getView().findViewById(R.id.spinnerRE);
        requestQueueGetRubros = Volley.newRequestQueue(getContext());
        //listaA(la);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getURL ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)  {
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject alumno = alumnos.getJSONObject(i);
                        String idRubro = alumno.getString("id_rubro");
                        String nombre = alumno.getString("nombre");
                        String usuario = alumno.getString("id_usuario");
                        if (usuario.equals(user) || usuario.equals("0") ){
                            listaRubros.add(idRubro + "\n" + nombre);
                        };
                        //listaA[i]=idaula;
                    }
                    dataAdapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        });

        requestQueueGetRubros.add(jsonObjectRequest);
        dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,listaRubros);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        //spinner.setSelection(3);//getIndex(spinner)
    }

    public void editConcept(){
        String text = spinner.getSelectedItem().toString();
        String[] rubros=text.split("\n");
        rubroExistente=rubros[0].trim();
        requestQueueSend = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, editURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Producto modificado con éxito",Toast.LENGTH_LONG ).show();
                nombreConcepto.setText("");
                precioConcpeto.setText("");
                catalogogastos fragment = new catalogogastos();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"no se pudo"+error,Toast.LENGTH_LONG ).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //Toast.makeText(getContext(),"id concepto"+idCG,Toast.LENGTH_LONG ).show();

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("idConcepto",idCG);
                parameters.put("nombre", nombreConcepto.getText().toString());
                parameters.put("costo", precioConcpeto.getText().toString());
                parameters.put("idRubro", rubroExistente);
                return parameters;
            }
            // idC+","+rubro+", "+nombre+", "+costo
        };
        requestQueueSend.add(request);
    };
}
