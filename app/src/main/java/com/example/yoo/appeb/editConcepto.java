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
    Spinner rubroExistente; //spinnerRE
    EditText nuevoRubro; //edtTextNuevoRubro
    String datos;
    Button editarProd;
    ArrayList<String> listaRubros= new ArrayList<String>();
    ArrayAdapter<String> dataAdapter;
    String[] dataArray;
    RequestQueue requestQueueSend;
    RequestQueue requestQueueGetRubros;
    String editURL = "http://webcolima.com/wsecomapping/editConcepto.php";
    String getURL = "http://webcolima.com/wsecomapping/rubros.php";
    String user= "1";


    public editConcepto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agregargasto, container, false);
        //text = (TextView) view.findViewById(R.id.Productos);

        datos = getArguments().getString("datos");
        //idC+","+rubro+", "+nombre+", "+costo
        editarProd = (Button) view.findViewById(R.id.button);
        editarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueueSend = Volley.newRequestQueue(getContext());
                StringRequest request = new StringRequest(Request.Method.POST, editURL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),"Producto modificado con éxito",Toast.LENGTH_LONG ).show();
                        nombreConcepto.setText("");
                        precioConcpeto.setText("");
                        nuevoRubro.setText("");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                       /* parameters.put("idConcepto", dataArray[0]);
                        parameters.put("idRubro", nombreProd.getText().toString());
                        parameters.put("nombre", precioProd.getText().toString());
                        parameters.put("costo", precioProd.getText().toString());*/
                        return parameters;

                       // idC+","+rubro+", "+nombre+", "+costo
                    }
                };
                requestQueueSend.add(request);
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        nombreConcepto = (EditText)getView().findViewById(R.id.edtNombreConcepto);
        precioConcpeto = (EditText)getView().findViewById(R.id.edtPrecioConcepto);

        dataArray = datos.split(",");
        nombreConcepto.setText(dataArray[2]);
        precioConcpeto.setText(dataArray[3].trim());

        LinearLayout layoutRE = (LinearLayout)getView().findViewById(R.id.RE);
        LinearLayout layoutRN = (LinearLayout)getView().findViewById(R.id.RN);
        RadioGroup RG = (RadioGroup)getView().findViewById(R.id.RubrosRB);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  switch(checkedId){
                      case R.id.rubroE:
                          // do operations specific to this selection
                          mostrarRE(getView());
                          break;

                      case R.id.rubroN:
                          // do operations specific to this selection
                          mostrarRN(getView());
                          break;
                  }
              }
          }
        );
    }
    public void mostrarRE(View view) {
        //((TextView) view.findViewById(R.id.textView_superior)).setText(mItem.textoEncima);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.GONE);
        listaRubros.clear();
        Spinner spinnerAlumAc = (Spinner)getView().findViewById(R.id.spinnerRE);
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
        spinnerAlumAc.setAdapter(dataAdapter);
        //spinnerAlumAc.setOnItemSelectedListener(AlumnoActividad.this);


    }
    public void mostrarRN(View view){
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.GONE);
    }
}
