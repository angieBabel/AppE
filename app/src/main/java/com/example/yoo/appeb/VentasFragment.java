package com.example.yoo.appeb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.List;
import java.util.Map;

public class VentasFragment extends Fragment {
    RequestQueue requestQueueLA;
    RequestQueue requestQueueDelete;
    String showContado = "http://webcolima.com/wsecomapping/ventas.php";
    String showAdeudo = "http://webcolima.com/wsecomapping/adeudos.php";
    String deleteURL = "http://webcolima.com/wsecomapping/delVenta.php";

    ArrayList<String> listaProductos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;
    String datosR;
    String tipoVenta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ventas, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabVenta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ddVenta fragment = new ddVenta();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //si hay datos enviados
        datosR = getArguments() != null ? getArguments().getString("datos") : "email@email.com";

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);

        tabs.addTab(tabs.newTab().setText("Contado"));
        tabs.addTab(tabs.newTab().setText("Crédito"));

        tabs.setOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        PD = new ProgressDialog(getContext());
                        PD.setMessage("Loading.....");
                        PD.setCancelable(false);
                        ad.clear();
                        ad.notifyDataSetChanged();
                        if (tab.getText() == "Contado"){
                            readContado();

                        }else if (tab.getText() == "Crédito"){
                            readCredito();
                        };


                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        // ...
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        // ...
                    }
                }
        );

        lista = (ListView)getView().findViewById(R.id.listViewVentas);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                datos = (String) lista.getItemAtPosition(position);
                //Toast.makeText(this,datos,Toast.LENGTH_LONG).show();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                if (tipoVenta=="contado"){
                    builder.setMessage("Seleccione una opción")
                            .setTitle("")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener()  {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.i("Dialogos", "Confirmacion Eliminar.");
                                    String[] data = datos.split(",");
                                    eliminar(data[0]);

                                }
                            });

                }else if (tipoVenta=="credito"){
                    builder.setMessage("Seleccione una opción")
                            .setTitle("")
                            .setPositiveButton("Editar", new DialogInterface.OnClickListener()  {
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.i("Dialogos", "Confirmacion Agregar.");

                                    editventa fragment = new editventa();
                                    Bundle args = new Bundle();
                                    args.putString("datos", datos);
                                    fragment.setArguments(args);
                                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                                            getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                                    fragmentTransaction.commit();
                                }
                            });

                }


                builder.show();

            }
        });
        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        if (datosR == "credito"){
            readCredito();
        }else {
            readContado();
        }

    }

    public void readContado(){
        tipoVenta="contado";
        requestQueueLA = Volley.newRequestQueue(getActivity());

        PD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,showContado,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                listaProductos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String id_usuario = producto.getString("id_usuario");
                        String idventa = producto.getString("idventa");
                        String nombreproducto = producto.getString("nombreproducto");
                        String precioproducto = producto.getString("precioproducto");
                        String cantidad = producto.getString("cantidad");
                        String modopago = producto.getString("modopago");
                        String fechaventa = producto.getString("fechaventa");
                        String totalventa = producto.getString("totalventa");
                        String idProducto = producto.getString("idProducto");
                        if (id_usuario.equals(user)){
                            if (modopago.equals("0")){
                                modopago="Contado";
                            }else {
                                modopago="Crédito";
                            }
                            listaProductos.add(idventa +","+nombreproducto +","+precioproducto +","+cantidad +","+modopago +","+fechaventa +","+totalventa +","+idProducto);
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

        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaProductos);
        lista.setAdapter(ad);

    }

    public void readCredito(){
        tipoVenta="credito";
        requestQueueLA = Volley.newRequestQueue(getActivity());

        PD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,showAdeudo,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                listaProductos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String id_usuario = producto.getString("id_usuario");
                        String nombreproducto = producto.getString("nombreproducto");
                        String deudor = producto.getString("deudor");
                        String deuda = producto.getString("deuda");
                        String abono = producto.getString("abono");
                        String abono_periodo = producto.getString("abono_periodo");
                        String fechaventa = producto.getString("fechaventa");
                        String idAdeudo = producto.getString("idAdeudo");
                        if (id_usuario.equals(user)){

                            listaProductos.add(idAdeudo +","+nombreproducto +","+deudor +","+deuda +","+fechaventa +","+abono +","+abono_periodo);
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

        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaProductos);
        lista.setAdapter(ad);

    }

    public void eliminar(final String s){
        requestQueueDelete = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, deleteURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Registro eliminado con éxito",Toast.LENGTH_LONG ).show();
                ad.clear();
                ad.notifyDataSetChanged();
                //ReadDataFromDB();
                readContado();
                ad.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("idVenta", s);
                return parameters;
                // idC+","+rubro+", "+nombre+", "+costo
            }
        };
        requestQueueDelete.add(request);

    }

}

/*
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


*//**
 * A simple {@link Fragment} subclass.
 *//*
public class VentasFragment extends Fragment {

    RequestQueue requestQueueLA;
    RequestQueue requestQueueDelete;
    String showURL = "http://webcolima.com/wsecomapping/ventas.php";
    String deleteURL = "http://webcolima.com/wsecomapping/delVenta.php";

    ArrayList<String> listaProductos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;
    String idProd;


    public VentasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ventas, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabVenta);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ddVenta fragment = new ddVenta();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        lista = (ListView)getView().findViewById(R.id.listViewVentas);
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
                                String[] data = datos.split(",");
                               // Toast.makeText(getContext(),data[0] ,Toast.LENGTH_LONG ).show();
                                eliminar(data[0]);

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
                        String id_usuario = producto.getString("id_usuario");
                        String idventa = producto.getString("idventa");
                        String nombreproducto = producto.getString("nombreproducto");
                        String precioproducto = producto.getString("precioproducto");
                        String cantidad = producto.getString("cantidad");
                        String modopago = producto.getString("modopago");
                        String fechaventa = producto.getString("fechaventa");
                        String totalventa = producto.getString("totalventa");
                        String idProducto = producto.getString("idProducto");
                        if (id_usuario.equals(user)){
                            if (modopago=="0"){
                                modopago="Contado";
                            }else {
                                modopago="Crédito";
                            }
                            listaProductos.add(idventa +","+nombreproducto +","+precioproducto +","+cantidad +","+modopago +","+fechaventa +","+totalventa +","+idProducto);
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

        ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listaProductos);
        lista.setAdapter(ad);


    }
    public void eliminar(final String s){
        /*Toast.makeText(getContext(),"xgo vivo",Toast.LENGTH_LONG ).show();*/
        requestQueueDelete = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, deleteURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(),"Producto eliminado con éxito",Toast.LENGTH_LONG ).show();
                ad.clear();
                ad.notifyDataSetChanged();
                ReadDataFromDB();
                ad.notifyDataSetChanged();
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
                parameters.put("idVenta", s);
                return parameters;
                // idC+","+rubro+", "+nombre+", "+costo
            }
        };
        requestQueueDelete.add(request);

    }
}*/
