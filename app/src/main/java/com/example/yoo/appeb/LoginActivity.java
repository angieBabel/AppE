package com.example.yoo.appeb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
import com.example.yoo.appeb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    RequestQueue requestQueueLA;
    RequestQueue requestQueueDelete;
    String showURL = "http://webcolima.com/wsecomapping/allUsers.php";
    String addURL = "http://webcolima.com/wsecomapping/delProd.php";

    String mail;
    String pass;
    String acceso;
    String idUser;
    String nombree;
    String apellidoo;
    String correoo;
    TextView signbutton;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private Button btnLogin;
    private EditText inputUser,inputPassword;
    SharedPreferences prefs;
    CallbackManager callbackManager;
    LoginButton loginButton;


    String LOGIN_URL= "http://192.168.1.66:8080/OpenDoor/login.php";

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) view.findViewById(R.id.login_FB);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
         prefs = getSharedPreferences("MisPreferencias",getApplicationContext().MODE_PRIVATE);
        String usuario = prefs.getString("User", "0");
        if (!usuario.equals("0")){
            //Toast.makeText(LoginActivity.this,"si entra con diferente de 0 :7 "+usuario,Toast.LENGTH_LONG ).show();
            openProfile(view);
        }

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        signbutton= (TextView) findViewById(R.id.registro);
        //TextView modelTextview = (TextView)findViewById(R.id.modelEdit);
        signbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(v);

            }
        });

       /* btnLogin = (Button) findViewById(R.id.log_in_button);

        btnLogin.setOnClickListener(this);*/
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }


    @Override
    public void onClick(View view) {
        userLogin();
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
                            nombree =nombre;
                            apellidoo=apellido;
                            correoo=correo;
                            Toast.makeText(LoginActivity.this,"Bienvenido "+nombre+" "+apellido,Toast.LENGTH_LONG ).show();
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
                }else {
                    signin(view);
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

    private void signin(View v) {
        Toast.makeText(LoginActivity.this,"lo inento",Toast.LENGTH_LONG ).show();
        Intent intent = new Intent(LoginActivity.this, registrar.class);
        //ntent.putExtra(KEY_USERNAME,username);
        //intent.putExtra("User",idUser);
        LoginActivity.this.startActivity(intent);

    }

    @Override
    public void onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}