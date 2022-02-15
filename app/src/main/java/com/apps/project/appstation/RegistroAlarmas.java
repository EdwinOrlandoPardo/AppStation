package com.apps.project.appstation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.project.appstation.Adapters.AdaptadorAlarma;
import com.apps.project.appstation.Objects.POJORegistro;
import com.apps.project.appstation.Utils.Utils;
import com.apps.project.appstation.fragments.Alarmas;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class RegistroAlarmas extends AppCompatActivity {

    NumberPicker nPTempeMax, nPTempeMin, nPHumedMax, nPHumedMin;
    Button btnEnviar, btnListar;

    EditText etNombreAlarma;
    boolean flagGuarda = true;

    ListView lvAlarmas;
    int idAlarma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alarmas);

        idElementos();

        nPTempeMax.setMaxValue(100);
        nPTempeMax.setMinValue(0);
        nPTempeMax.setWrapSelectorWheel(true);

        nPTempeMin.setMaxValue(100);
        nPTempeMin.setMinValue(0);
        nPTempeMin.setWrapSelectorWheel(true);

        nPHumedMax.setMaxValue(100);
        nPHumedMax.setMinValue(0);
        nPHumedMax.setWrapSelectorWheel(true);

        nPHumedMin.setMaxValue(100);
        nPHumedMin.setMinValue(0);
        nPHumedMin.setWrapSelectorWheel(true);



        if (getIntent().getExtras() != null) {

            flagGuarda = false;
            POJORegistro alarmaActualizar = (POJORegistro) getIntent().getExtras().getSerializable("objetoActualiza");
            idAlarma=alarmaActualizar.getId();
            etNombreAlarma.setText(alarmaActualizar.getNameAlarma());
            nPTempeMax.setValue(Integer.parseInt(alarmaActualizar.getTempeMax()));
            nPTempeMin.setValue(Integer.parseInt(alarmaActualizar.getTempeMin()));
            nPHumedMax.setValue(Integer.parseInt(alarmaActualizar.getHumeMax()));
            nPHumedMin.setValue(Integer.parseInt(alarmaActualizar.getHumeMin()));
            btnEnviar.setText("Actualizar");
        }

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irListar = new Intent(RegistroAlarmas.this, MainActivity.class);
                startActivity(irListar);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hilo que realiza el registro de los datos en la Base de datos

                if (flagGuarda) {
                    HiloWebInsertar insertarWeb = new HiloWebInsertar();
                    insertarWeb.execute();
                    Toast.makeText(RegistroAlarmas.this, "Guardado Correctamente", Toast.LENGTH_LONG).show();
                    Intent goListaAlarmas = new Intent(RegistroAlarmas.this, MainActivity.class);
                    startActivity(goListaAlarmas);

                } else {
                    HiloWebActualizar actualizaDato = new HiloWebActualizar();
                    actualizaDato.execute();
                    Toast.makeText(RegistroAlarmas.this, "Actualizado Correctamente", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void idElementos() {

        nPTempeMax = (NumberPicker) findViewById(R.id.nPickerMaxTempe);
        nPTempeMin = (NumberPicker) findViewById(R.id.nPickerMinTempe);
        nPHumedMax = (NumberPicker) findViewById(R.id.nPickerMaxHume);
        nPHumedMin = (NumberPicker) findViewById(R.id.nPickerMinHume);
        btnEnviar = (Button) findViewById(R.id.buttonToast);
        btnListar = (Button) findViewById(R.id.btnListar);
        etNombreAlarma = (EditText) findViewById(R.id.et_nombreAlarma);
        lvAlarmas =(ListView) findViewById(R.id.lvAlarmas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro_alarmas, menu);
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


    private class HiloWebInsertar extends AsyncTask<String, Integer, Boolean> {


        String nombreAlarma = etNombreAlarma.getText().toString();
        String valorTempeMax = Integer.toString(nPTempeMax.getValue());  //Integer.toString(nPTempeMax.getValue());
        String valorTempeMin = Integer.toString(nPTempeMin.getValue());
        String valorHumeMax = Integer.toString(nPHumedMax.getValue());
        String valorHumeMin = Integer.toString(nPHumedMin.getValue());
        int idAntena = Utils.idAntena;

        String respuestaStr = null;

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "registroAlarma";
        final String SOAP_ACTION = "http://tempuri.org/registroAlarma";

        @Override
        protected Boolean doInBackground(String... params) {

            boolean flagResul = true;

            //---------------------------------------------------
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("nomalarma", nombreAlarma);
                request.addProperty("temperaturaMax", valorTempeMax);
                request.addProperty("temperaturaMin", valorTempeMin);
                request.addProperty("humedadMax", valorHumeMax);
                request.addProperty("humedadMin", valorHumeMin);
                request.addProperty("IdAntena", idAntena);

                //---------------------------------------------------

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                //---------------------------------------------------

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuestaStr = resultado.toString();
            } catch (Exception e1) {

                e1.printStackTrace();
                respuestaStr = "Error Garrafal";
                flagResul = false;
            }

            return flagResul;

        }
    }

    //---------------------------------------  HILO ACTUALIZAR DATOS   ---------------------------------

    public class HiloWebActualizar extends AsyncTask<String, Integer, Boolean> {


        String nombreAlarma = etNombreAlarma.getText().toString();
        String valorTempeMax= Integer.toString(nPTempeMax.getValue());  //Integer.toString(nPTempeMax.getValue());
        String valorTempeMin= Integer.toString(nPTempeMin.getValue());
        String valorHumeMax = Integer.toString(nPHumedMax.getValue());
        String valorHumeMin = Integer.toString(nPHumedMin.getValue());


        String respuestaStr=null;

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "actualizarAlarma";
        final String SOAP_ACTION = "http://tempuri.org/actualizarAlarma";

        @Override
        protected Boolean doInBackground(String... params) {

            boolean flagResul = true;

            //---------------------------------------------------
            try {
                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("idAlarma", idAlarma);
                request.addProperty("nombreAlarma",nombreAlarma);
                request.addProperty("temperaturaMax", valorTempeMax);
                request.addProperty("temperaturaMin", valorTempeMin);
                request.addProperty("humedadMax", valorHumeMax);
                request.addProperty("humedadMin", valorHumeMin);

                //---------------------------------------------------

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                //---------------------------------------------------

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuestaStr=resultado.toString();}

            catch (Exception e1) {

                e1.printStackTrace();
                respuestaStr = "Error Garrafal";
                flagResul = false;
            }

            return flagResul;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            //tvServicio.setText(respuestaStr);
        }
    }



}
