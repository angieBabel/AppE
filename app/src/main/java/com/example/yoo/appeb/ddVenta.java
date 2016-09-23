package com.example.yoo.appeb;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ddVenta extends Fragment {
    RequestQueue requestQueueA;
    String addURL = "http://webcolima.com/wsecomapping/addVenta.php";
    String getURL = "http://webcolima.com/wsecomapping/productos.php";

    EditText cant;// canntidad;
    EditText deudo;// canntidad;
    EditText precioConcpeto;//           edtPrecioConcepto
    String rubroExistente; //spinnerRE
    EditText nuevoRubro; //edtTextNuevoRubro
    String modoP;
    Spinner spinner;
    ArrayList<String> listaProductos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    RequestQueue requestQueueSend;
    RequestQueue requestQueueGet;
    String user;
    SharedPreferences prefs;


    public ddVenta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dd_venta, container, false);
        super.onCreate(savedInstanceState);
        return view;

    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias", getActivity().MODE_PRIVATE);
        String usuario = prefs.getString("User", "0");
        user = usuario;

        spinner = (Spinner)getView().findViewById(R.id.spinnerProducto);

        cant =(EditText)getView().findViewById(R.id.cantidad); //cantidad
        deudo =(EditText)getView().findViewById(R.id.deudo); //cantidad
        LinearLayout deudor = (LinearLayout)getView().findViewById(R.id.deudor);
        getSpiner();

        Button addPP = (Button) getView().findViewById(R.id.button);
        addPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewVenta();
            }
        });


        RadioGroup RG = (RadioGroup)getView().findViewById(R.id.ModopagoRB);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(RadioGroup group, int checkedId) {
                      switch(checkedId){
                          case R.id.pagoCredito:
                              // do operations specific to this selection
                              showDeudor(getView());
                              break;

                          case R.id.pagoContado:
                              // do operations specific to this selection
                              hideDeudor(getView());
                              break;
                      }
                  }
              }
        );
    }

    public void addNewVenta(){
        /*if (!rubroExistente.equals("no")){
            String text = spinner.getSelectedItem().toString();
            String[] productos=text.split(",");
            rubroExistente=productos[0].trim();
            //idP+","+nombre + "  ,  " + preci
        }*/
        String text = spinner.getSelectedItem().toString();
        final String[] productos=text.split(",");

        requestQueueA = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, addURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Venta agregado con éxito",Toast.LENGTH_LONG ).show();
                cant.setText("");
                VentasFragment fragment = new VentasFragment();
                Bundle args = new Bundle();
                args.putString("datos", "contado");
                fragment.setArguments(args);
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

                //String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                //idP+","+nombre + "  ,  " + preci
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id_usuario",user);
                parameters.put("idP", productos[0]);
                parameters.put("precio", productos[2]);
                parameters.put("cantidad", cant.getText().toString());
                parameters.put("modopago", modoP);
                parameters.put("deudor", deudo.getText().toString());
                //parameters.put("fecha", date);
                return parameters;
            }
        };
        requestQueueA.add(request);
    }

    public void  getSpiner(){

        requestQueueGet = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,getURL,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaProductos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String nombre = producto.getString("nombre");
                        String precio = producto.getString("precio");
                        String usuario = producto.getString("id_usuario");
                        String idP = producto.getString("id_producto");
                        if (usuario.equals(user)){
                            listaProductos.add(idP+","+nombre + "," + precio);
                        };


                    } // for loop ends
                    ad.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG ).show();
            }
        });

        requestQueueGet.add(jsonObjectRequest);
        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,listaProductos);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);


    }

    public void showDeudor(View view) {
        modoP="Credito";
        //((TextView) view.findViewById(R.id.textView_superior)).setText(mItem.textoEncima);
        ((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.VISIBLE);

    }
    public void hideDeudor(View view){
        modoP="Contado";
        ((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.GONE);
    }


}
