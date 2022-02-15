package com.apps.project.appstation;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.project.appstation.Objects.UsuarioPOJO;
import com.apps.project.appstation.Utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView Nombre,Apellido,Telefono,Direccion,Email;
    PerfilActivity.ServicioDatosPerfil datosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sttings);

        Nombre = (TextView) findViewById(R.id.tv_nombre);
        Apellido = (TextView) findViewById(R.id.tv_apellido);
        Telefono = (TextView) findViewById(R.id.tv_tel);
        Direccion = (TextView) findViewById(R.id.tv_direccion);
        Email = (TextView) findViewById(R.id.tv_email);



        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        PerfilActivity.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        datosUsuario = new ServicioDatosPerfil();
        datosUsuario.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sttings, menu);
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

    public class ServicioDatosPerfil extends AsyncTask<String,Integer,Boolean>{

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD = "PerfilUsuario";
        final String SOAP_ACTION = "http://tempuri.org/PerfilUsuario";

        int iddelusuario= Utils.idUsuario;

        UsuarioPOJO[]misDatosPerfil;

        String nom,ape,tel,dir,ema;


        @Override
        protected Boolean doInBackground(String... params) {
            Boolean flag = true;

                SoapObject peticion = new SoapObject(NAMESPACE,METHOD);
                peticion.addProperty("idusuario", iddelusuario);
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(peticion);

                HttpTransportSE tranporte = new HttpTransportSE(URL);
            try{
                tranporte.call(SOAP_ACTION,sobre);

                SoapObject resultado = (SoapObject)sobre.getResponse();

                misDatosPerfil = new UsuarioPOJO[resultado.getPropertyCount()];

                for (int j =0;j<misDatosPerfil.length;j++){
                    SoapObject data = (SoapObject) resultado.getProperty(j);

                    UsuarioPOJO d = new UsuarioPOJO();

                    d.Nombre = data.getProperty(0).toString();
                    d.Apellido = data.getProperty(1).toString();
                    d.Telefono = data.getProperty(2).toString();
                    d.Direccion = data.getProperty(3).toString();
                    d.Email = data.getProperty(4).toString();

                    misDatosPerfil[j] = d;


                }


            }catch (Exception e){
                flag = false;

            }
            return flag ;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (misDatosPerfil != null){
                for (int i =0;i<misDatosPerfil.length;i++) {
                    Nombre.setText(nom = misDatosPerfil[i].getNombre());
                    Apellido.setText(ape = misDatosPerfil[i].getApellido());
                    Telefono.setText(tel = misDatosPerfil[i].getTelefono());
                    Direccion.setText(dir = misDatosPerfil[i].getDireccion());
                    Email.setText(ema = misDatosPerfil[i].getEmail());
                }

            }else {
                Toast.makeText(PerfilActivity.this,"NO DATOS",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
