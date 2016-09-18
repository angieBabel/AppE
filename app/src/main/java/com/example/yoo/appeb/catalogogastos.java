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

import com.android.volley.RequestQueue;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class catalogogastos extends Fragment {
    RequestQueue requestQueueLA;
    String showURL = "http://webcolima.com/wsecomapping/catalogogastos.php";
    ArrayList<String> listaGastos= new ArrayList<String>();
    ArrayAdapter<String> ad;
    ListView lista;
    ProgressDialog PD;
    String user= "1";
    public static final String KEY_datos="datos";
    String datos;



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


}
