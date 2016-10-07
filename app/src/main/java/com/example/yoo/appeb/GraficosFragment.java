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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<BarEntry> barentries = new ArrayList<>();
    //ArrayList<BarEntry> barentries = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();

    int cont;

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
        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        LoadVP();
        /*LoadVM();
        LoadVP();
        LoadGP();
        LoadGM();*/

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Ventas del periodo"));
        tabs.addTab(tabs.newTab().setText("Ventas por mes"));
        tabs.addTab(tabs.newTab().setText("Gastos del periodo"));
        tabs.addTab(tabs.newTab().setText("Gastos por mes"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tabs.setCurrentTab(0);
        tabs.setOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        getView().findViewById(R.id.barVP).setVisibility(View.GONE);
                        getView().findViewById(R.id.lineVP).setVisibility(View.GONE);
                        getView().findViewById(R.id.pieVP).setVisibility(View.GONE);

                        getView().findViewById(R.id.barVM).setVisibility(View.GONE);
                        getView().findViewById(R.id.lineVM).setVisibility(View.GONE);
                        getView().findViewById(R.id.pieVM).setVisibility(View.GONE);

                        getView().findViewById(R.id.barGP).setVisibility(View.GONE);
                        getView().findViewById(R.id.lineGP).setVisibility(View.GONE);
                        getView().findViewById(R.id.pieGP).setVisibility(View.GONE);

                        getView().findViewById(R.id.barGM).setVisibility(View.GONE);
                        getView().findViewById(R.id.lineGM).setVisibility(View.GONE);
                        getView().findViewById(R.id.pieGM).setVisibility(View.GONE);

                        // adaptador.getCount();
                        if (tab.getPosition()==0){
                            //VP
                            LoadVP();
                            Toast.makeText(getContext(),"VP",Toast.LENGTH_LONG ).show();
                            //readContado();
                        }else if (tab.getPosition()==1){
                            //VM
                            LoadVM();
                            Toast.makeText(getContext(),"VM",Toast.LENGTH_LONG ).show();
                            //readCredito();
                        }else if (tab.getPosition()==2){
                            //GP
                            LoadGP();
                            Toast.makeText(getContext(),"GP",Toast.LENGTH_LONG ).show();
                            //readCredito();
                        }else if (tab.getPosition()==3){
                            //GM
                            LoadGM();
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
            barentries

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



    private void  LoadVP(){
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
        cont=0;
        barentries.clear();
        entries.clear();
        labels.clear();
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
                        cont++;
                        switch(tipoG) {
                            case "Barras":
                                barentries.add(new BarEntry(Integer.parseInt(cantidad),cont));
                                break;
                            case "Lineal":
                                entries.add(new Entry(Integer.parseInt(cantidad),cont));
                                break;
                            case "Pastel":
                                entries.add(new Entry(Integer.parseInt(cantidad),cont));

                                break;
                        }
                        labels.add(nombre);
                    } // for loop ends
                    //adapter.notifyDataSetChanged();
                    //ad.notifyDataSetChanged();

                    PD.dismiss();
                    /*Toast.makeText(getContext(),entries.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
                    Toast.makeText(getContext(),labels.size()+" tamanio del labels",Toast.LENGTH_LONG ).show();*/
                    LVPTG();
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
        //lineVP
    }
    private void  LoadVM(){
        requestQueueLA = Volley.newRequestQueue(getActivity());


        PD.show();
        cont=0;
        barentries.clear();
        entries.clear();
        labels.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,urlVM,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaGastos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String mes = producto.getString("mes");
                        String total = producto.getString("total");
                        String usuario = producto.getString("id_usuario");
                        //Toast.makeText(getContext(),nombre+" , "+cantidad,Toast.LENGTH_LONG ).show();
                        cont++;
                        switch(tipoG) {
                            case "Barras":
                                barentries.add(new BarEntry(Integer.parseInt(total),cont));
                                break;
                            case "Lineal":
                                entries.add(new Entry(Integer.parseInt(total),cont));
                                break;
                            case "Pastel":
                                entries.add(new Entry(Integer.parseInt(total),cont));

                                break;
                        }
                        labels.add(mes);
                    } // for loop ends
                    //adapter.notifyDataSetChanged();
                    //ad.notifyDataSetChanged();

                    PD.dismiss();
                    /*Toast.makeText(getContext(),entries.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
                    Toast.makeText(getContext(),labels.size()+" tamanio del labels",Toast.LENGTH_LONG ).show();*/
                    LVMTG();
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

    }
    private void  LoadGP(){
        String a,m,d,aa,mm,dd;
        String[] fech=fi.split("/");
        d = fech[0];
        m = fech[1];
        a = fech[2];
        String[] fechh=ff.split("/");
        dd = fechh[0];
        mm = fechh[1];
        aa = fechh[2];

        String newurl = urlGP+"?id_usuario="+user+"&fechaInicio="+a+"-"+m+"-"+d+"&fechaFin="+aa+"-"+mm+"-"+dd;
        requestQueueLA = Volley.newRequestQueue(getActivity());


        PD.show();
        cont=0;
        barentries.clear();
        entries.clear();
        labels.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,newurl,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaGastos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String nombre = producto.getString("nombreconcepto");
                        String cantidad = producto.getString("cantidad");
                        //String usuario = producto.getString("id_usuario");
                        //Toast.makeText(getContext(),nombre+" , "+cantidad,Toast.LENGTH_LONG ).show();
                        cont++;

                        switch(tipoG) {
                            case "Barras":
                                barentries.add(new BarEntry(Integer.parseInt(cantidad),cont));
                                break;
                            case "Lineal":
                                entries.add(new Entry(Integer.parseInt(cantidad),cont));
                                break;
                            case "Pastel":
                                entries.add(new Entry(Integer.parseInt(cantidad),cont));

                                break;
                        }
                        labels.add(nombre);
                    } // for loop ends
                    //adapter.notifyDataSetChanged();
                    //ad.notifyDataSetChanged();

                    PD.dismiss();
                    /*Toast.makeText(getContext(),entries.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
                    Toast.makeText(getContext(),labels.size()+" tamanio del labels",Toast.LENGTH_LONG ).show();*/
                    LGPTG();

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

    }
    private void  LoadGM(){
        String a,m,d,aa,mm,dd;
        String[] fech=fi.split("/");
        d = fech[0];
        m = fech[1];
        a = fech[2];
        String[] fechh=ff.split("/");
        dd = fechh[0];
        mm = fechh[1];
        aa = fechh[2];

        //String newurl = urlVP+"?id_usuario="+user+"&fechaInicio="+a+"-"+m+"-"+d+"&fechaFin="+aa+"-"+mm+"-"+dd;
        requestQueueLA = Volley.newRequestQueue(getActivity());


        PD.show();
        cont=0;
        barentries.clear();
        entries.clear();
        labels.clear();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,urlGM,new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //listaGastos.add("Producto          Precio");
                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String mes = producto.getString("mes");
                        String total = producto.getString("totalgasto");
                        String usuario = producto.getString("id_usuario");
                        //Toast.makeText(getContext(),nombre+" , "+cantidad,Toast.LENGTH_LONG ).show();
                        cont++;

                        switch(tipoG) {
                            case "Barras":
                                barentries.add(new BarEntry(Integer.parseInt(total),cont));
                                break;
                            case "Lineal":
                                entries.add(new Entry(Integer.parseInt(total),cont));
                                break;
                            case "Pastel":
                                entries.add(new Entry(Integer.parseInt(total),cont));

                                break;
                        }
                        labels.add(mes);
                    } // for loop ends
                    //adapter.notifyDataSetChanged();
                    //ad.notifyDataSetChanged();

                    PD.dismiss();
                    /*Toast.makeText(getContext(),entries.size()+" tamanio del entries",Toast.LENGTH_LONG ).show();
                    Toast.makeText(getContext(),labels.size()+" tamanio del labels",Toast.LENGTH_LONG ).show();*/
                    LGMTG();

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

    }


    private void LVPTG(){
        switch(tipoG){
            case "Barras":
                getView().findViewById(R.id.barVP).setVisibility(View.VISIBLE);
                BarDataSet dataSet = new BarDataSet(barentries,"Productos vendidos en el periodo");
                BarData data = new BarData(labels, dataSet);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                BarChart barChart = (BarChart)getView().findViewById(R.id.barVP);
                barChart.setDescription("Description");
                barChart.setData(data);
                barChart.animateY(1000);
                break;
            case "Lineal":
                getView().findViewById(R.id.lineVP).setVisibility(View.VISIBLE);
                LineDataSet linedataSet = new LineDataSet(entries,"Productos vendidos en el periodo");
                LineData linedata = new LineData(labels, linedataSet);
                linedataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                LineChart lineChart = (LineChart)getView().findViewById(R.id.lineVP);
                lineChart.setDescription("Description");
                lineChart.setData(linedata);
                lineChart.animateY(1000);
                break;
            case "Pastel":
                getView().findViewById(R.id.pieVP).setVisibility(View.VISIBLE);
                PieDataSet dataset = new PieDataSet(entries, "Productos vendidos en el periodo");
                PieData dataPie = new PieData(labels, dataset);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                PieChart pieChart = (PieChart)getView().findViewById(R.id.pieVP);
                pieChart.setDescription("Description");
                pieChart.setData(dataPie);
                pieChart.animateY(1000);

                break;
        }
    }
    private void LVMTG(){
        switch(tipoG){
            case "Barras":
                getView().findViewById(R.id.barVM).setVisibility(View.VISIBLE);
                BarDataSet dataSet = new BarDataSet(barentries,"Productos vendidos en el periodo");
                BarData data = new BarData(labels, dataSet);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                BarChart barChart = (BarChart)getView().findViewById(R.id.barVM);
                barChart.setDescription("Description");
                barChart.setData(data);
                barChart.animateY(1000);
                break;
            case "Lineal":
                getView().findViewById(R.id.lineVM).setVisibility(View.VISIBLE);
                LineDataSet linedataSet = new LineDataSet(entries,"Productos vendidos en el periodo");
                LineData linedata = new LineData(labels, linedataSet);
                linedataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                LineChart lineChart = (LineChart)getView().findViewById(R.id.lineVM);
                lineChart.setDescription("Description");
                lineChart.setData(linedata);
                lineChart.animateY(1000);
                break;
            case "Pastel":
                getView().findViewById(R.id.pieVM).setVisibility(View.VISIBLE);
                PieDataSet dataset = new PieDataSet(entries, "Productos vendidos en el periodo");
                PieData dataPie = new PieData(labels, dataset);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                PieChart pieChart = (PieChart)getView().findViewById(R.id.pieVM);
                pieChart.setDescription("Description");
                pieChart.setData(dataPie);
                pieChart.animateY(1000);

                break;
        }
    }
    private void LGPTG(){
        switch(tipoG){
            case "Barras":
                getView().findViewById(R.id.barGP).setVisibility(View.VISIBLE);
                BarDataSet dataSet = new BarDataSet(barentries,"Productos vendidos en el periodo");
                BarData data = new BarData(labels, dataSet);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                BarChart barChart = (BarChart)getView().findViewById(R.id.barGP);
                barChart.setDescription("Description");
                barChart.setData(data);
                barChart.animateY(1000);
                break;
            case "Lineal":
                getView().findViewById(R.id.lineGP).setVisibility(View.VISIBLE);
                LineDataSet linedataSet = new LineDataSet(entries,"Productos vendidos en el periodo");
                LineData linedata = new LineData(labels, linedataSet);
                linedataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                LineChart lineChart = (LineChart)getView().findViewById(R.id.lineGP);
                lineChart.setDescription("Description");
                lineChart.setData(linedata);
                lineChart.animateY(1000);
                break;
            case "Pastel":
                getView().findViewById(R.id.pieGP).setVisibility(View.VISIBLE);
                PieDataSet dataset = new PieDataSet(entries, "Productos vendidos en el periodo");
                PieData dataPie = new PieData(labels, dataset);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                PieChart pieChart = (PieChart)getView().findViewById(R.id.pieGP);
                pieChart.setDescription("Description");
                pieChart.setData(dataPie);
                pieChart.animateY(1000);

                break;
        }
    }
    private void LGMTG(){
        switch(tipoG){
            case "Barras":
                getView().findViewById(R.id.barGM).setVisibility(View.VISIBLE);
                BarDataSet dataSet = new BarDataSet(barentries,"Productos vendidos en el periodo");
                BarData data = new BarData(labels, dataSet);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                BarChart barChart = (BarChart)getView().findViewById(R.id.barGM);
                barChart.setDescription("Description");
                barChart.setData(data);
                barChart.animateY(1000);
                break;
            case "Lineal":
                getView().findViewById(R.id.lineGM).setVisibility(View.VISIBLE);
                LineDataSet linedataSet = new LineDataSet(entries,"Productos vendidos en el periodo");
                LineData linedata = new LineData(labels, linedataSet);
                linedataSet.setColors(ColorTemplate.COLORFUL_COLORS); //
                LineChart lineChart = (LineChart)getView().findViewById(R.id.lineGM);
                lineChart.setDescription("Description");
                lineChart.setData(linedata);
                lineChart.animateY(1000);
                break;
            case "Pastel":
                getView().findViewById(R.id.pieGM).setVisibility(View.VISIBLE);
                PieDataSet dataset = new PieDataSet(entries, "Productos vendidos en el periodo");
                PieData dataPie = new PieData(labels, dataset);
                dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                PieChart pieChart = (PieChart)getView().findViewById(R.id.pieGM);
                pieChart.setDescription("Description");
                pieChart.setData(dataPie);
                pieChart.animateY(1000);

                break;
        }
    }

}
//((LinearLayout) view.findViewById(R.id.deudor)).setVisibility(View.VISIBLE);