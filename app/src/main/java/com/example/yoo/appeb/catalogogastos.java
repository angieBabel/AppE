package com.example.yoo.appeb;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class catalogogastos extends Fragment {
    RequestQueue requestQueueLA;
    RequestQueue requestQueueDelete;
    String showURL = "http://webcolima.com/wsecomapping/catalogogastos.php";
    String deleteURL = "http://webcolima.com/wsecomapping/delConcepto.php";
    //ArrayList<String> listaGastos= new ArrayList<String>();
    ArrayList <catalogoGastos_list> listaGastos = new ArrayList<catalogoGastos_list>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;
    String nombreR;
    String concepto;
    String costo;
    String idCG;
    catalogogastoslist_adapter adapter;



    public catalogogastos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_catalogogastos, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabCatGastos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregargasto fragment = new agregargasto();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lista = (ListView)getView().findViewById(R.id.listViewgastos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                //datos = (String) lista.getItemAtPosition(position);
                datos = listaGastos.get(position).getIdCG();
                //Toast.makeText(this,datos,Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Seleccione una opción")
                        .setTitle("")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener()  {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Eliminar.");
                                //String[] data = datos.split(",");
                                //eliminar(data[0]);
                                eliminar(datos);

                            }
                        })
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Agregar.");
                                String nombreR;
                                String concepto;
                                String costo;
                                String idCG;

                                editConcepto fragment = new editConcepto();
                                Bundle args = new Bundle();
                                args.putString("datos", datos);
                                fragment.setArguments(args);
                                android.support.v4.app.FragmentTransaction fragmentTransaction =
                                        getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                fragmentTransaction.commit();
                            }
                        });
                builder.show();

            }
        });

        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        ReadDataFromDB();

    }
    public void ReadDataFromDB() {

        requestQueueLA = Volley.newRequestQueue(getActivity());

        PD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,showURL,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaGastos.add("Id Concepto          Costo");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String rubro = producto.getString("nombrerubro");
                        String nombre = producto.getString("nombre");
                        String costo = producto.getString("costo");
                        String usuario = producto.getString("id_usuario");
                        String idC = producto.getString("id_concepto");
                        if (usuario.equals(user)){
                            //listaGastos.add(idC+","+rubro+", "+nombre+", "+costo);
                            listaGastos.add(new catalogoGastos_list(rubro,nombre,costo,idC));
                        };


                    } // for loop ends
                    //ad.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();

                    PD.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG ).show();
            }
        });/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id_usuario", "1");
                return param;
            }
        };*/

        requestQueueLA.add(jsonObjectRequest);

        /*ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaGastos);
        lista.setAdapter(ad);*/

        adapter = new catalogogastoslist_adapter(getActivity(), listaGastos);
        lista.setAdapter(adapter);


    }
    public void eliminar(final String s){
        requestQueueDelete = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, deleteURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Producto eliminado con éxito",Toast.LENGTH_LONG ).show();
                //ad.clear();
                adapter.clear();
                //ad.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                ReadDataFromDB();
                //ad.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("idConcepto", s);
                return parameters;
                // idC+","+rubro+", "+nombre+", "+costo
            }
        };
        requestQueueDelete.add(request);

    }


}
