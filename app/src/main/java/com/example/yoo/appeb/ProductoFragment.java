package com.example.yoo.appeb;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoFragment extends Fragment  implements NavigationView.OnNavigationItemSelectedListener {

    //Root URL of our web service
    //public static final String ROOT_URL = "http://localhost:8080/EasyBWS/";

    //Strings to bind with intent will be used to send data to other activity
    /*public static final String KEY_PRODUCT_ID= "key_product_id";
    public static final String KEY_USUARIO_ID = "key_usuario_id";
    public static final String KEY_NOMBRE = "key_nombre";
    public static final String KEY_PRECIO= "key_precio";*/

    public static final String productoURL = "http://localhost:8080/EasyBWS/productos.php";
    //List view to show data
    private ListView listView;
    //List of type products this list will store type Book which is our data model
    private Response<List<producto>> productoss;
    private TextView text;
    public ProductoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto, container, false);
        text = (TextView) view.findViewById(R.id.Productos);

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
        //Initializing the listview
        listView = (ListView)getView().findViewById(R.id.listViewProductos);

        //Calling the method that will fetch data
        getProduct();

        //Setting onItemClickListener to listview
        //listView.setOnItemClickListener((AdapterView.OnItemClickListener) this);
    }




    private void getProductos() {
        //While the app fetched data we are displaying a progress dialog
        //final ProgressDialog loading = ProgressDialog.show(getContext(),"Fetching Data","Please wait...",false,false);

        /*GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
        Call<List<Contributor>> call = gitHubService.repoContributors(“square”, “retrofit”);
        List<Contributor> result = call.execute().body();*/

        //ProductoAPI productoAPI = ProductoAPI.retrofit.create(ProductoAPI.class);
        //final retrofit2.Call<List<producto>> call = productoAPI.productsList("");
        //List<producto> result = call.execute().body();
        /*ProductoAPI productApi = ProductoAPI.retrofit.create(ProductoAPI.class);
        retrofit2.Call<List<producto>> call = productApi.productsList("1");*/



        /*call.enqueue(new Callback<List<producto>>() {
            @Override
            public void onResponse(retrofit2.Call<List<producto>> call, Response<List<producto>> response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                //Storing the data in our list
                //productoss = response;

                try {
                    List<producto> productoss = call.execute().body();
                    //String result = call.execute().body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Calling a method to show the list
                showList();

            }

            @Override
            public void onFailure(retrofit2.Call<List<producto>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Toast por defecto", Toast.LENGTH_SHORT).show();

            }
        });*/
    }
    private void getProducts(){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

    }
    public void postDataSoft() {
        try{

            String simpleUrl = "http://192.168.0.86:8383/testapp/soft.php?appsinstalled="+URLEncoder.encode(strAppsInstaladas)+"&appsuninstalled="+URLEncoder.encode(strAppsDesinstaladas)+"&appsrunning="+URLEncoder.encode(strAppsDesinstaladas)+"&fecha_rec="+URLEncoder.encode(strDate);

            simpleUrl=simpleUrl.replace(" ","%20");
            //INSERT INTO informacion_soft (appsInstalled, appsUnistalled,appsRunning,fechaRecoleccion) VALUES ('facebook','twitter','youtube','00-00-11")
            //http://192.168.0.86:8383/testapp/soft.php?appsinstalled=facebook,office mobule,com.packagame.name&appsuninstalled=x&appsrunning=x&fecha_rec=x

            //String simpleUrl = "http://192.168.0.86:8383/testapp/savedata.php";
            //http://localhost:8383/testapp/savedata.php?id=1&macAddres=11:22:33:44:55&tipo=laptop&fabricante=IUSA&versionos=4.2.2&nummodelo=1234
            //  String encodedurl = URLEncoder.encode(simpleUrl, "UTF-8");

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpget = new HttpGet(simpleUrl);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream webs = entity.getContent();


        }catch(Exception e) {
            Log.e("Error in connection3: ", e.toString());
        }

    }
    public void getProductss(String token) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(productoURL);
        //httpGet.setHeader("Content-type", "application/json");
        //httpGet.addHeader("Authorization", "Token " + token);

        InputStream inputStream = null;
        String result = null;
        try {
            // Add your data
            // Execute HTTP Get Request
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        try{
            this.getBoxesFromJSonArray(new JSONArray(result));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getProduct() throws IOException {
        URL productoURL = new URL("http://localhost:8080/EasyBWS/productos.php")
        //URL url = new URL("http://monstalkers.hostoi.com/data/get_all_comments.php");
        HttpURLConnection urlConnection = (HttpURLConnection) productoURL.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            showList(in);
            // Acciones a realizar con el flujo de datos
        }
        finally {
                urlConnection.disconnect();
            }



    }
    //Our method to show list
    private void showList(InputStream in){
        //String array to store all the book names
        String[] items = new String[productoss.body().size()];
        //String[] items = new String[productoss.size()];

        //Traversing through the whole list to get all the names
        for(int i=0; i<productoss.body().size(); i++){
            //Storing names to string array
            items[i] = productoss.body().get(i).getNombre();
        }

        //Creating an array adapter for list view
        ArrayAdapter adapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_producto,items);
        /*(this,R.layout.simple_list,items);*/

        //Setting adapter to listview
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


}
