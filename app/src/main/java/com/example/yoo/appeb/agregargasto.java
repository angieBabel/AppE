package com.example.yoo.appeb;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
public class agregargasto extends Fragment {
    RequestQueue requestQueueA;
    String addURL = "http://webcolima.com/wsecomapping/addConcepto.php";
    String getURL = "http://webcolima.com/wsecomapping/rubros.php";

    EditText nombreConcepto;// edtNombreConcepto;
    EditText precioConcpeto;//           edtPrecioConcepto
    String rubroExistente; //spinnerRE
    EditText nuevoRubro; //edtTextNuevoRubro
    String tipoRubro;
    Button addConcepto;
    Spinner spinner;
    ArrayList<String> listaRubros= new ArrayList<String>();
    ArrayAdapter<String> dataAdapter;
    RequestQueue requestQueueSend;
    RequestQueue requestQueueGetRubros;
    String user;

    public agregargasto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias",getActivity().MODE_PRIVATE);
        String usuario = prefs.getString("User", "0");
        user = usuario;
        View view = inflater.inflate(R.layout.fragment_agregargasto, container, false);
        //text = (TextView) view.findViewById(R.id.Productos);
        super.onCreate(savedInstanceState);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        nombreConcepto = (EditText)getView().findViewById(R.id.edtNombreConcepto);
        precioConcpeto = (EditText)getView().findViewById(R.id.edtPrecioConcepto);
        nuevoRubro = (EditText)getView().findViewById(R.id.newRubro);
        spinner = (Spinner)getView().findViewById(R.id.spinnerRE);

        LinearLayout layoutRE = (LinearLayout)getView().findViewById(R.id.RE);
        LinearLayout layoutRN = (LinearLayout)getView().findViewById(R.id.RN);


        Button addPP = (Button) getView().findViewById(R.id.button);
        addPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(),"si manda llamar bien al onclick",Toast.LENGTH_LONG ).show();
                addNewConcepto();
            }
        });
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


    public void addNewConcepto(){
        if (rubroExistente.equals("si")){
            String text = spinner.getSelectedItem().toString();
            String[] rubros=text.split("\n");
            rubroExistente=rubros[0].trim();
        }

        requestQueueA = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, addURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Concepto agregado con éxito",Toast.LENGTH_LONG ).show();
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
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG ).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("idUsuario",user);
                parameters.put("nombre", nombreConcepto.getText().toString());
                parameters.put("precio", precioConcpeto.getText().toString());
                parameters.put("rubroExistente", rubroExistente);
                parameters.put("tiporubro", tipoRubro);
                parameters.put("rubroNuevo", nuevoRubro.getText().toString());
                return parameters;
            }
        };
        requestQueueA.add(request);
    }


    public void mostrarRE(View view) {
        tipoRubro="Existe";
        rubroExistente="si";
        //((TextView) view.findViewById(R.id.textView_superior)).setText(mItem.textoEncima);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.GONE);
        listaRubros.clear();
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
        //spinnerAlumAc.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        //rubroExistente = spinnerAlumAc.getSelectedItem().toString();
    }

    public void mostrarRN(View view){
        tipoRubro = "NoExiste";
        rubroExistente="no";
        ((LinearLayout) view.findViewById(R.id.RN)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.RE)).setVisibility(View.GONE);
    }
}
