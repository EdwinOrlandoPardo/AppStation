package com.apps.project.appstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.apps.project.appstation.Objects.LoginPOJO;
import com.apps.project.appstation.Utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCorreo,etContrasena;
    Button btnLogin,btnRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = (EditText) findViewById(R.id.et_Correo);
        etContrasena = (EditText) findViewById(R.id.et_contrasena);

        btnLogin = (Button) findViewById(R.id.btn_Login);
        btnRegistroUsuario = (Button) findViewById(R.id.btn_RegistroUsuario);

        btnLogin.setOnClickListener(this);
        btnRegistroUsuario.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_Login:
                ServicioLogeo login = new ServicioLogeo();
                login.execute();
                break;
            case R.id.btn_RegistroUsuario:
                Intent goRegistro = new Intent(LoginActivity.this,RegistroUsuarioActivity.class);
                startActivity(goRegistro);
                break;

        }
    }

    public class ServicioLogeo extends AsyncTask<String,Integer,Boolean>{

        EditText etcorreo = (EditText) findViewById(R.id.et_Correo);
        EditText etcontrasena = (EditText) findViewById(R.id.et_contrasena);

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "LoginUsuario";
        final String SOAP_ACTION = "http://tempuri.org/LoginUsuario";


        String correo = etcorreo.getText().toString();
        String contrasena = etcontrasena.getText().toString();

        String usucorreo,usucontrasena,usunombre,usuapellido,id;


        LoginPOJO[] loginPOJOs;

        @Override
        protected Boolean doInBackground(String... params) {
            boolean flag= true;


                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("correo",correo);
                request.addProperty("contrasena",contrasena);

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION,sobre);
                SoapObject resultado = (SoapObject) sobre.getResponse();

                loginPOJOs = new LoginPOJO[resultado.getPropertyCount()];

                for (int j =0;j<loginPOJOs.length;j++){
                    SoapObject data = (SoapObject) resultado.getProperty(j);

                    LoginPOJO d = new LoginPOJO();

                    d.id = data.getProperty(0).toString();
                    d.correo = data.getProperty(1).toString();
                    d.contrasena = data.getProperty(2).toString();
                    d.nombre= data.getProperty(3).toString();
                    d.apellido =  data.getProperty(4).toString();

                    //login = new LoginPOJO(usucorreo,usucontrasena,usunombre,usuapellido);
                    loginPOJOs[j] = d;
                }

            }catch (Exception e1){
                e1.printStackTrace();
                flag = false;
            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //String []datos = new String[loginPOJOs.length];
            if (loginPOJOs != null) {
                for (int i = 0; i < loginPOJOs.length; i++) {

                    id = loginPOJOs[i].getId();
                    usucorreo = loginPOJOs[i].getCorreo();
                    usucontrasena = loginPOJOs[i].getContrasena();
                    usunombre = loginPOJOs[i].getNombre();
                    usuapellido = loginPOJOs[i].getApellido();

                }

                if (usucorreo != null && usucontrasena != null) {
                    Utils.idUsuario = Integer.parseInt(id);
                    if (usucorreo.equals(correo) && usucontrasena.equals(contrasena)) {
                        AlertDialog.Builder login = new AlertDialog.Builder(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
                        login.setTitle("LOGIN CORRECTO");
                        login.setMessage("DATOS CORRECTOS");
                        login.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goHome = new Intent(LoginActivity.this, MainActivity.class);
                                goHome.putExtra("nombre", usunombre + " " + usuapellido);
                                goHome.putExtra("correo", correo);
                                goHome.putExtra("idUsuario", id);
                                startActivity(goHome);
                                LoginActivity.this.finish();
                                dialog.cancel();
                            }
                        });
                        login.show();


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.AppCompatAlertDialogStyle);
                        builder.setTitle("EROR LOGEO");
                        builder.setMessage("DATOS INCORRECTOS");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                } else {
               /* Intent goHome = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goHome);*/
                    Toast.makeText(LoginActivity.this, "EROR NO HAY DATOS", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(LoginActivity.this,"ARREGLO VACIO", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
