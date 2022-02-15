package com.apps.project.appstation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.project.appstation.Utils.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class RegistroUsuarioActivity extends AppCompatActivity {

    TextView tvTitulo;
    Button btnAceptar,btnCancelar;

    String nombre,apellido,direcion,correo,contrasena,img;
   int  telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        tvTitulo = (TextView) findViewById(R.id.tv_titulo);
        tvTitulo.setText("Registro Usuario");



        btnAceptar = (Button) findViewById(R.id.btn_registro);
        btnCancelar = (Button) findViewById(R.id.btn_cancelar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicioRegistroUsuario registro = new ServicioRegistroUsuario();
                registro.execute();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goLogin = new Intent(RegistroUsuarioActivity.this, LoginActivity.class);
                RegistroUsuarioActivity.this.startActivity(goLogin);
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_usuario, menu);
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

    public class ServicioRegistroUsuario extends AsyncTask<String,Integer,Boolean>{

        EditText etNombre = (EditText) findViewById(R.id.et_nombre);
        EditText etApellido = (EditText) findViewById(R.id.et_apellido);
        EditText etTelefono = (EditText) findViewById(R.id.et_telefono);
        EditText etDireccion = (EditText) findViewById(R.id.et_direccion);
        EditText etCorreo = (EditText) findViewById(R.id.et_Correo);
        EditText etContrasena = (EditText) findViewById(R.id.et_reContrasena);

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "NuevoUsuario";
        final String SOAP_ACTION = "http://tempuri.org/NuevoUsuario";

        String res= null;

        String nombre = etNombre.getText().toString();
        String apellido = etApellido.getText().toString();
        int   telefono = Integer.parseInt(etTelefono.getText().toString());
        String direcion = etDireccion.getText().toString();
        String correo = etCorreo.getText().toString();
        String contrasena = etContrasena.getText().toString();
        String img = "imagen.jpg";

        String completonombre= nombre+" "+apellido;

        @Override
        protected Boolean doInBackground(String... params) {
            boolean flag = true;
            try {
                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("nombre",nombre);
                request.addProperty("apellido",apellido);
                request.addProperty("tel",telefono);
                request.addProperty("direccion", direcion);
                request.addProperty("correo",correo);
                request.addProperty("contrasena", contrasena);
                request.addProperty("imagen",img);

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                res = resultado.toString();

            }catch (Exception e){
                Log.e("ERROR :" ,e.getMessage());
                flag = false;

            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean){
                if (res.equals("1")){
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(RegistroUsuarioActivity.this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("REGISTRO USUARIO");
                    builder.setMessage("SE HA REGISTRADO EXITOSAMENTE");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent goHome= new Intent(RegistroUsuarioActivity.this,MainActivity.class);
                            goHome.putExtra("nombre",completonombre);
                            goHome.putExtra("correo",correo);
                            startActivity(goHome);
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else{
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(RegistroUsuarioActivity.this, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("REGISTRO NO USUARIO");
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
