package com.example.yoo.appeb;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class RazonesFragment extends Fragment {
    ProgressDialog PD;
    RequestQueue requestQueueLA;
    String showURL = "http://webcolima.com/wsecomapping/razones.php";
    int CP;
    int TV;
    SharedPreferences prefs;
    String user, fi, ff;

    Date FECHAI; //date2 es el 31 de octubre de 2001
    Date FECHAF;
    SimpleDateFormat sdf;
    public RazonesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = getActivity().getSharedPreferences("MisPreferencias",getActivity().MODE_PRIVATE);
        user = prefs.getString("User", "0");
        fi = prefs.getString("FI", "0");
        ff = prefs.getString("FF", "0");

        /*  try{

                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            //sdf = new SimpleDateFormat("yyyy-MM-dd");

                            Date FECHAI = formatter.parse(fi);
                            Date FECHAF = formatter.parse(ff);
                            Date fechaDB = formatter.parse(d+"/"+m+"/"+a);



        if (usuario.equals(user) && FECHAI.compareTo(fechaDB) <= 0 && FECHAF.compareTo(fechaDB) >= 0&& FECHAI.compareTo(fechaDB) <= 0 && FECHAF.compareTo(fechaDB) >= 0 */

        return inflater.inflate(R.layout.fragment_razones, container, false);
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        // FECHAI.compareTo(fechaDB) <= 0 && FECHAF.compareTo(fechaDB) >= 0

        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        requestQueueLA = Volley.newRequestQueue(getActivity());

        PD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,showURL,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String mp = producto.getString("mp");
                        String usuario = producto.getString("id_usuario");
                        String fecha = producto.getString("fecha");
                        String a,m,d;
                        String[] fech=fecha.split("-");
                        a = fech[0];
                        m = fech[1];
                        d = fech[2];
                        //Toast.makeText(getContext(),"entra "+d+"/"+m+"/"+a,Toast.LENGTH_LONG ).show();

                        try{

                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            //sdf = new SimpleDateFormat("yyyy-MM-dd");

                            Date FECHAI = formatter.parse(fi);
                            Date FECHAF = formatter.parse(ff);
                            Date fechaDB = formatter.parse(d+"/"+m+"/"+a);

                            /*Toast.makeText(getContext(),"fechaDB "+fechaDB,Toast.LENGTH_LONG ).show();
                            Toast.makeText(getContext(),"fechaIN "+FECHAI,Toast.LENGTH_LONG ).show();*/


                            if (usuario.equals(user) && FECHAI.compareTo(fechaDB) <= 0 && FECHAF.compareTo(fechaDB) >= 0/*&& FECHAI.compareTo(fechaDB) <= 0 && FECHAF.compareTo(fechaDB) >= 0*/ ){

                               /* if (FECHAI.compareTo(fechaDB) <= 0){
                                    Toast.makeText(getContext(),"si es mayor que la fecha de inicio",Toast.LENGTH_LONG ).show();
                                    Toast.makeText(getContext(),fechaDB.toString(),Toast.LENGTH_LONG ).show();

                                }
                                if (FECHAF.compareTo(fechaDB) >= 0){
                                    Toast.makeText(getContext(),"si es menor igual la fecha",Toast.LENGTH_LONG ).show();
                                    Toast.makeText(getContext(),fechaDB.toString(),Toast.LENGTH_LONG ).show();

                                }*/

                                //Toast.makeText(getContext(),"Si hayo usuario "+user+" ss "+fechaDB,Toast.LENGTH_LONG ).show();
                                if (mp.equals("1")){
                                    CP=CP+1;
                                }
                                TV=TV+1;

                            };

                        }catch (ParseException e1){
                            Toast.makeText(getContext(),"error "+e1,Toast.LENGTH_LONG ).show();
                        }

                    } // for loop ends

                    PD.dismiss();
                    fillText();

                } catch (JSONException e) {
                    Toast.makeText(getContext(),"error "+e,Toast.LENGTH_LONG ).show();
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
    }

    private void fillText() {
        Double RCC;
        Double PCC;
        TextView RC = (TextView) getView().findViewById(R.id.RotacionCobros);
        TextView PC = (TextView) getView().findViewById(R.id.PeriodoCredito);

        DecimalFormat twoDForm = new DecimalFormat("#.##");
        if (CP!=0){
            RCC=Double.valueOf(twoDForm.format((double)TV/(double)CP));
            PCC=Double.valueOf(twoDForm.format(365/RCC));
        }else {
            TextView aviso = (TextView) getView().findViewById(R.id.aviso);
            aviso.setVisibility(View.VISIBLE);
            RCC=0.0;
            PCC=0.0;
        }
        RC.setText(RCC+" veces se crean y cobran las cuentas por cobrar");
        PC.setText("La empresa tarda "+PCC+" días en trasformar en efectivo las ventas realizadas. ");
    }

     /*<TextView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="X veces se crean y cobran las cuentas por cobrar "
    android:id="@+id/RotacionCobros"
    android:scrollIndicators="top"
    android:layout_marginTop="15dp" /> */


    /*<TextView
    android:text=
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/PeriodoCredito"
    android:layout_marginTop="15dp" />*/



}
