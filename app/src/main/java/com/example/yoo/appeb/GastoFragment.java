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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GastoFragment extends Fragment {

    RequestQueue requestQueueLA;
    String showURL = "http://webcolima.com/wsecomapping/gastos.php";

    ArrayList<String> listaGastos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;
    String idProd;


    public GastoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gasto, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabGasto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addconcepto fragment = new addconcepto();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lista = (ListView)getView().findViewById(R.id.listView);
       /* lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                datos = (String) lista.getItemAtPosition(position);
                //Toast.makeText(this,datos,Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Seleccione una opción")
                        .setTitle("")
                        .setPositiveButton("Ver detalle", new DialogInterface.OnClickListener()  {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Agregar.");

                                editProducto fragment = new editProducto();
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
        });*/


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
                listaGastos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String nombre = producto.getString("nombrerubro");
                        String total = producto.getString("totalgasto");
                        String idR = producto.getString("rubro");
                        String usuario = producto.getString("id_usuario");
                        if (usuario.equals(user)){
                            listaGastos.add(idR+","+nombre + "," + total);
                        };
                    } // for loop ends
                    ad.notifyDataSetChanged();

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
        });
        requestQueueLA.add(jsonObjectRequest);

        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaGastos);
        lista.setAdapter(ad);
    }
}
