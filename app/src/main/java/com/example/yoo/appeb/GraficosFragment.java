package com.example.yoo.appeb;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraficosFragment extends Fragment {
    String urlVP = "http://webcolima.com/wsecomapping/graficasVP.php";
    String urlVM = "http://webcolima.com/wsecomapping/graficasVM.php";
    String urlGP = "http://webcolima.com/wsecomapping/graficasGP.php";
    String urlGM = "http://webcolima.com/wsecomapping/graficasGM.php";

    SharedPreferences prefs;
    String user;
    String tipoG;
    ProgressDialog PD;
    String fi, ff;
    ArrayList<Entry> entries;
    ArrayList<String> labels;
    RequestQueue requestQueueLA;

    public GraficosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graficos, container, false);
        //text = (TextView) view.findViewById(R.id.Productos);
        super.onCreate(savedInstanceState);
        return view;
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        SharedPreferences prefs = getActivity().getSharedPreferences("MisPreferencias",getActivity().MODE_PRIVATE);
        String usuario = prefs.getString("User", "0");
        String tipoGrafica = prefs.getString("TipoGrafica", "0");
        user = usuario;
        tipoG=tipoGrafica;
        fi = prefs.getString("FI", "0");
        ff = prefs.getString("FF", "0");
        Toast.makeText(getContext(),tipoG,Toast.LENGTH_LONG ).show();

        entries = new ArrayList<>();
        labels = new ArrayList<String>();
        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        LoadVM();
        LoadVP();
        LoadGP();
        LoadGM();

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Ventas del periodo"));
        tabs.addTab(tabs.newTab().setText("Ventas por mes"));
        tabs.addTab(tabs.newTab().setText("Gastos del periodo"));
        tabs.addTab(tabs.newTab().setText("Gastos por mes"));
        //tabs.setCurrentTab(0);
        tabs.setOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        // adaptador.getCount();
                        if (tab.getPosition()==0){
                            //VP
                            Toast.makeText(getContext(),"VP",Toast.LENGTH_LONG ).show();
                            //readContado();
                        }else if (tab.getPosition()==1){
                            //VM
                            Toast.makeText(getContext(),"VM",Toast.LENGTH_LONG ).show();
                            //readCredito();
                        }else if (tab.getPosition()==2){
                            //GP
                            Toast.makeText(getContext(),"GP",Toast.LENGTH_LONG ).show();
                            //readCredito();
                        }else if (tab.getPosition()==3){
                            //GM
                            Toast.makeText(getContext(),"GM",Toast.LENGTH_LONG ).show();
                            //readCredito();
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

        /*switch(tipoGrafica){
            case "Barras":

                break;
            case "Lineal":

                break;
            case "Pastel":

                break;
        }*/


        /*PieChart pieChart = (PieChart)getView().findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        PieDataSet dataset = new PieDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85); // 85 is the quality of the image*/

     }



    private void  LoadVM(){
        String a,m,d,aa,mm,dd;
        String[] fech=fi.split("/");
        d = fech[0];
        m = fech[1];
        a = fech[2];
        String[] fechh=ff.split("/");
        dd = fechh[0];
        mm = fechh[1];
        aa = fechh[2];

        String newurl = urlVP+"?id_usuario="+user+"&fechaInicio="+a+"-"+m+"-"+d+"&fechaFin="+aa+"-"+mm+"-"+dd;
        requestQueueLA = Volley.newRequestQueue(getActivity());

        PD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,newurl,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaGastos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String nombre = producto.getString("nombreproducto");
                        String cantidad = producto.getString("cantidad");
                        String usuario = producto.getString("id_usuario");
                        //Toast.makeText(getContext(),nombre+" , "+cantidad,Toast.LENGTH_LONG ).show();


                        entries.add(new Entry(9f,0));
                        labels.add(nombre);
                    } // for loop ends
                    //adapter.notifyDataSetChanged();
                    //ad.notifyDataSetChanged();

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
        Toast.makeText(getContext(),entries.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
        Toast.makeText(getContext(),labels.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
        //getView().findViewById(R.id.pieVP).setVisibility(View.VISIBLE);
        /*PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        PieChart pieChart = (PieChart)getView().findViewById(R.id.pieVP);
        pieChart.setDescription("Description");
        pieChart.setData(data);

        pieChart.animateY(5000);*/
        //lineVP
    }
    private void  LoadVP(){

    }
    private void  LoadGP(){

    }
    private void  LoadGM(){

    }

}
//((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.VISIBLE);