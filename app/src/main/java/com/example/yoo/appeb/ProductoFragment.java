package com.example.yoo.appeb;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoFragment extends Fragment  implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue requestQueueLA;
    String showURL = "http://webcolima.com/wsecomapping/productos.php";
    ArrayList<String> listaProductos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;

    public ProductoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabProducto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnuevoproducto fragment = new addnuevoproducto();
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
        lista = (ListView)getView().findViewById(R.id.listViewProductos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                datos = (String) lista.getItemAtPosition(position);
                //Toast.makeText(this,datos,Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Seleccione una opción")
                        .setTitle("")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener()  {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Dialogos", "Confirmacion Eliminar.");

                            }
                        })
                        .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
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
                listaProductos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String nombre = producto.getString("nombre");
                        String precio = producto.getString("precio");
                        String usuario = producto.getString("id_usuario");
                        String idP = producto.getString("id_producto");
                        if (usuario.equals(user)){
                            listaProductos.add(idP+","+nombre + "  ,  " + precio);
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
        });/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String,String>();
                param.put("id_usuario", "1");
                return param;
            }
        };*/

        requestQueueLA.add(jsonObjectRequest);

        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaProductos);
        lista.setAdapter(ad);


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    public static class HeadlinesFragment extends Fragment {
        OnHeadlineSelectedListener mCallback;

        // Container Activity must implement this interface
        public interface OnHeadlineSelectedListener {
            public void editProduct (String dataP);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnHeadlineSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }

      //  ...
    }
}
