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
import android.widget.Switch;
import android.widget.TextView;

import com.apps.project.appstation.R;
import com.apps.project.appstation.Utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegistrarEstacion extends AppCompatActivity implements View.OnClickListener {

    Button RegistroEstacion,CancelarEstacion;
    TextView tvTitulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estacion);

        tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        tvTitulo.setText("Registrar Estacion");

        RegistroEstacion = (Button) findViewById(R.id.btn_registrarAntena);
        CancelarEstacion = (Button) findViewById(R.id.btn_cancelarAntena);

        RegistroEstacion.setOnClickListener(this);
        CancelarEstacion.setOnClickListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_registrar_estacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      /*  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
       switch (id){
           case R.id.btn_registrarAntena:
               ServicioRegistroAntena serviceRegistrio = new ServicioRegistroAntena();
               serviceRegistrio.execute();
               break;
           case R.id.btn_cancelarAntena:
               Intent goHome= new Intent(RegistrarEstacion.this,MainActivity.class);
               startActivity(goHome);
               break;
       }
    }



    public class ServicioRegistroAntena extends AsyncTask<String,Integer,Boolean>{

        EditText codAntena = (EditText) findViewById(R.id.et_codAntena);
        EditText nombreAntena = (EditText) findViewById(R.id.et_nombreAntena);

        String codigo = codAntena.getText().toString();
        String nombre = nombreAntena.getText().toString();
        int id = Utils.idUsuario;

        int idusu = id;

        String res = null;

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "RegistroAntena";
        final String SOAP_ACTION = "http://tempuri.org/RegistroAntena";



        @Override
        protected Boolean doInBackground(String... params) {
            boolean flag= true;

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("idantena",codigo);
            request.addProperty("nombre",nombre);
            request.addProperty("idusuario",idusu);

            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try{
                transporte.call(SOAP_ACTION,sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                res = resultado.toString();

            }catch (Exception e){
                flag= false;
            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean){

                if (res.equals("1")){
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(RegistrarEstacion.this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("REGISTRO ANTENA");
                    builder.setMessage("SE HA REGISTRADO EXITOSAMENTE");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent goHome= new Intent(RegistrarEstacion.this,MainActivity.class);
                            startActivity(goHome);
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(RegistrarEstacion.this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("NO REGISTRO ANTENA");
                    builder.setMessage("ERROR NO SE HA REGISTRADO");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }

            }
        }
    }
}
