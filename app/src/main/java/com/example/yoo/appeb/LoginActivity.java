package com.example.yoo.appeb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yoo.appeb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    RequestQueue requestQueueLA;
    RequestQueue requestQueueDelete;
    String showURL = "http://webcolima.com/wsecomapping/allUsers.php";
    String addURL = "http://webcolima.com/wsecomapping/delProd.php";

    String mail;
    String pass;
    String acceso;
    String idUser;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Button btnLogin;
    private EditText inputUser,inputPassword;
    SharedPreferences prefs;



    String LOGIN_URL= "http://192.168.1.66:8080/OpenDoor/login.php";

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         prefs = getSharedPreferences("MisPreferencias",getApplicationContext().MODE_PRIVATE);
        String usuario = prefs.getString("User", "0");
        /*if (usuario != "0"){
            openProfile(view);
        }*/

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.log_in_button);

        btnLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        userLogin();
        //openProfile(view);
    }//Login

    //Logueo
    private void userLogin() {

        mail = mEmailView.getText().toString();
        pass = mPasswordView.getText().toString();

        //Toast.makeText(LoginActivity.this,"si llama al redData",Toast.LENGTH_LONG ).show();
        //Toast.makeText(LoginActivity.this,"mail ingresado"+mail,Toast.LENGTH_LONG ).show();
        //Toast.makeText(LoginActivity.this,"pass ingresado"+pass,Toast.LENGTH_LONG ).show();


        requestQueueLA = Volley.newRequestQueue(LoginActivity.this);

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

                        if (correo.equals(mail) && contrasenia.equals(pass)){
                            acceso ="correcto";
                            idUser =id_usuario;
                            //Toast.makeText(LoginActivity.this,"id del usuario :) "+idUser,Toast.LENGTH_LONG ).show();
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
                    String dateI = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                    String dateF = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("User", idUser);
                    editor.putString("FI", dateI);
                    editor.putString("FF", dateF);
                    editor.putString("TipoGrafica", "Barras");
                    editor.commit();
                    openProfile(view);
                }else {
                    registrar(mail,pass);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
            }
        });
        requestQueueLA.add(jsonObjectRequest);

    }
    //Envio de datos del perfil
    private  void openProfile(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        /*ntent.putExtra(KEY_USERNAME,username);*/
        intent.putExtra("User",idUser);
        LoginActivity.this.startActivity(intent);
    }

    private void registrar(String mail, String pass) {
    }
}