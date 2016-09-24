package com.example.yoo.appeb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

//import com.example.KissPK.appeb.R;

public class registrar extends AppCompatActivity implements View.OnClickListener{


    RequestQueue requestQueueDelete;
    RequestQueue requestQueueLA;
    String signURL = "http://webcolima.com/wsecomapping/signin.php";
    String showURL = "http://webcolima.com/wsecomapping/allUsers.php";
    EditText nombre;
    EditText apellido;
    EditText pass;
    EditText email;
    Button send;
    String nombree;
    String apellidoo;
    String correoo;
    String acceso;
    String idUser;
    SharedPreferences prefs;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);


        prefs = getSharedPreferences("MisPreferencias",getApplicationContext().MODE_PRIVATE);

        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);


        send = (Button) findViewById(R.id.sign_in_button);

        send.setOnClickListener(this);

        /*TextView register= (TextView) findViewById(R.id.registro);
        register.setOnClickListener((View.OnClickListener) this);*/
    }
    public void onClick(View view) {

        registrar();
    }//Login

    private void registrar() {
        requestQueueDelete = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, signURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplication(),"Bienvenido "+nombre.getText().toString()+" "+apellido.getText().toString(),Toast.LENGTH_LONG ).show();
                userLogin();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("nombre", nombre.getText().toString());
                parameters.put("apellido", apellido.getText().toString());
                parameters.put("email", email.getText().toString());
                parameters.put("pass", pass.getText().toString());
                return parameters;
                // idC+","+rubro+", "+nombre+", "+costo
            }
        };
        requestQueueDelete.add(request);
    }
    private void userLogin() {

        final String maill = email.getText().toString();
        final String ppass =pass.getText().toString();

        //Toast.makeText(LoginActivity.this,"si llama al redData",Toast.LENGTH_LONG ).show();
        //Toast.makeText(LoginActivity.this,"mail ingresado"+mail,Toast.LENGTH_LONG ).show();
        //Toast.makeText(LoginActivity.this,"pass ingresado"+pass,Toast.LENGTH_LONG ).show();


        requestQueueLA = Volley.newRequestQueue(registrar.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,showURL,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray alumnos = response.getJSONArray("all");
                    for (int i = 0; i < alumnos.length(); i++) {

                        JSONObject producto = alumnos.getJSONObject(i);
                        String id_usuario = producto.getString("id_usuario");
                        String nombre = producto.getString("nombre");
                        String apellido = producto.getString("apellido");
                        String correo = producto.getString("correo");
                        String contrasenia = producto.getString("contrasenia");

//                        Toast.makeText(LoginActivity.this,id_usuario+","+correo+","+contrasenia,Toast.LENGTH_LONG ).show();

                        if (correo.equals(maill) && contrasenia.equals(ppass)){
                            acceso ="correcto";
                            idUser =id_usuario;
                            nombree =nombre;
                            apellidoo=apellido;
                            correoo=correo;
                            Toast.makeText(registrar.this,"Bienvenido "+nombre+" "+apellido,Toast.LENGTH_LONG ).show();
                            //Toast.makeText(LoginActivity.this,"Acceso "+acceso,Toast.LENGTH_LONG ).show();
                            i = alumnos.length();
                            //
                        }
                    } // for loop ends
                    //llamarMain();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (acceso=="correcto"){

                    Calendar c1 = GregorianCalendar.getInstance();
                    c1.add(Calendar.MONTH, -1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    String dateI =  sdf.format(c1.getTime());
                    String dateF = new SimpleDateFormat("dd/MM/yy").format(new Date());

                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putString("User", idUser);
                    editor.putString("Nombre", nombree);
                    editor.putString("Apellido", apellidoo);
                    editor.putString("Correo", correoo);
                    editor.putString("FI", dateI);
                    editor.putString("FF", dateF);
                    editor.putString("TipoGrafica", "Barras");
                    editor.commit();
                    openProfile(view);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(registrar.this,error.toString(),Toast.LENGTH_LONG ).show();
            }
        });
        requestQueueLA.add(jsonObjectRequest);

    }

    //Envio de datos del perfil
    private  void openProfile(View view){
        Intent intent = new Intent(registrar.this, MainActivity.class);
        /*ntent.putExtra(KEY_USERNAME,username);*/
        //intent.putExtra("User",idUser);
        registrar.this.startActivity(intent);
    }

}