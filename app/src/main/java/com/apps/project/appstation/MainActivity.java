package com.apps.project.appstation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.project.appstation.Adapters.AdapterViewPager;
import com.apps.project.appstation.Objects.DatosAntena;
import com.apps.project.appstation.Objects.MisAntenas;
import com.apps.project.appstation.Objects.POJORegistro;
import com.apps.project.appstation.Utils.Utils;
import com.apps.project.appstation.fragments.Alarmas;

import org.kobjects.util.Util;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Alarmas.AlarmaInter{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    Spinner spListaAntenas;
    int idselect ;

    String titles []= {"Home","Antenas","Alarmas","Historial"};
    int numeroTabs = 4;
    AdapterViewPager adapter;
    ActionBarDrawerToggle mdrawerToggle;
    TextView name,email,titulo;
    String nombre,correo;
    Long id;

    ViewPager viewPager;

    ArrayList<String>listaIdAntenas;

    MainActivity.ServicioDatosAntena antena ;
    MainActivity.hiloListarAlarma listarAlarma;
    MainActivity.ServicioMisAntenas listaUsuarioAntena;
    MainActivity.HiloNotificaciones notificacionesalarma;




    public static final int NOTIFICACION_ID =1;
    private Timer timer;
    int respuestaStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titulo = (TextView) findViewById(R.id.tv_titulo);


        name = (TextView) findViewById(R.id.tv_headerName);
        nombre = getIntent().getStringExtra("nombre");


        email = (TextView) findViewById(R.id.tv_correo);
        correo = getIntent().getStringExtra("correo");

       // id = getIntent().getStringExtra("idUsuario");




        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);        //añade nuetro toolbar a la actividad

        adapter = new AdapterViewPager(getSupportFragmentManager(),titles,numeroTabs);/*GetFragmentManager -> nos retorna un fragmentManager
                                                                                       FragmentManager -> nos ayuda a interractuar con nu
                                                                                       nuestros fragments*/

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.wg_tablayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//Indica el tipo de pstanas que queremos utilizar hay fijas y DESLIZABLES
        tabLayout.setupWithViewPager(viewPager);


        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView != null) {
            name.setText(nombre);
            email.setText(correo);
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        timer = new Timer();
        timerNotificaciones tNoti = new timerNotificaciones();
        timer.scheduleAtFixedRate(tNoti,0,60000);


        listaIdAntenas=new ArrayList<>();

        listarAlarma = new hiloListarAlarma();
        listarAlarma.execute();

        listaUsuarioAntena = new ServicioMisAntenas();
        listaUsuarioAntena.execute();

        antena = new ServicioDatosAntena();
        antena.execute();

        notificacionesalarma = new HiloNotificaciones();
        notificacionesalarma.execute();

       // LlenarSpinner();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }



    private void setupNavigationDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.item_navigation_drawer_set_alarmas:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent goRegistroAlarmas = new Intent(MainActivity.this, RegistroAlarmas.class);
                                startActivity(goRegistroAlarmas);
                                return true;
                            case R.id.item_navigation_drawer_set_estaciones:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent goRegistroAntenas = new Intent(MainActivity.this, RegistrarEstacion.class);
                                goRegistroAntenas.putExtra("idusuario", id);
                                startActivity(goRegistroAntenas);
                                return true;
                            case R.id.item_navigation_drawer_perfil:
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                Intent goPerfil = new Intent(MainActivity.this, PerfilActivity.class);
                                goPerfil.putExtra("Correo", correo);
                                startActivity(goPerfil);
                                return true;
                            case R.id.item_navigation_drawer_Info:
                                menuItem.setChecked(true);
                                Toast.makeText(MainActivity.this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                });
    }


    @Override
    public void clicKBorraAlarma(long id) {
        HiloBorrarAlarma borrandoHilo = new HiloBorrarAlarma(id);
        borrandoHilo.execute();
        listarAlarma = new hiloListarAlarma();
        listarAlarma.execute();
        Toast.makeText(this,id+"exeecute Delete",Toast.LENGTH_LONG).show();
    }

    @Override
    public void clicActualizarAlarma(POJORegistro listadoAlarmas) {

        Intent goRegisttroAlarma=new Intent(MainActivity.this, RegistroAlarmas.class);
        goRegisttroAlarma.putExtra("objetoActualiza", listadoAlarmas);
        startActivity(goRegisttroAlarma);

    }


    public class hiloListarAlarma extends AsyncTask<String,Integer,Boolean>{


        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "ListarAlarmas";
        final String SOAP_ACTION = "http://tempuri.org/ListarAlarmas";

        ArrayList<POJORegistro> miAlarmas= new ArrayList<>();
        int idAntena,idalarma;
        String nombreALarma,temMax,temMin,humMax,humMin;
        POJORegistro d;
        POJORegistro[] registrosPOJOs;
        int idListarAlarma = Utils.idAntena;

        //-----------------------BACKGROUND ----------------------

        @Override
        protected Boolean doInBackground(String... params) {
            boolean flag= true;

            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("idAlarma",idListarAlarma);
//--------------------------------------------------------------------------------------------------

            SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            sobre.dotNet = true;
            sobre.setOutputSoapObject(request);

//--------------------------------------------------------------------------------------------------

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {

                transporte.call(SOAP_ACTION, sobre);
                SoapObject resultado = (SoapObject) sobre.getResponse();
                registrosPOJOs = new POJORegistro[resultado.getPropertyCount()];

                for (int j =0;j<registrosPOJOs.length;j++){
                    SoapObject data = (SoapObject) resultado.getProperty(j);
                    idalarma = Integer.parseInt(data.getProperty(0).toString());
                    nombreALarma = data.getProperty(1).toString();
                    temMax = data.getProperty(2).toString();
                    temMin= data.getProperty(3).toString();
                    humMax =  data.getProperty(4).toString();
                    humMin =  data.getProperty(5).toString();
                    idAntena = Integer.parseInt(data.getProperty(6).toString());

                    d = new POJORegistro(idalarma,nombreALarma,temMax,temMin,humMax,humMin);
                    miAlarmas.add(d);
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
            if (miAlarmas != null) {
                Utils.ListaAlarmas=miAlarmas;
                if(viewPager.getCurrentItem()==0){
                    viewPager.setAdapter(adapter);
                }
            }else{
                Toast.makeText(MainActivity.this, "NO hay datos :" + miAlarmas, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ServicioDatosAntena extends AsyncTask<String,Integer,Boolean>{

        String fecha,temperatura,humedad;
        String metodo="ConsultarStation";
        String soapAction= Utils.soapAction+"ConsultarStation";
        int antenas = Utils.idAntena;



        ArrayList<DatosAntena> datosAntena =new ArrayList<DatosAntena>();

        @Override
        protected Boolean doInBackground(String... strings) {

                SoapObject soapObject = new SoapObject(Utils.NAMESPACE, metodo);
                Log.e("soap ","Antenas id : "+antenas);
                soapObject.addProperty("idantena",antenas);
                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(soapObject);

                HttpTransportSE transportSE = new HttpTransportSE(Utils.URL);
            try {
                transportSE.call(soapAction, soapEnvelope);

                SoapObject soapObject1 = (SoapObject) soapEnvelope.getResponse();

                int i = soapObject1.getPropertyCount();

                for (int j = 0; j < i; j++) {
                    SoapObject soapObject2 = (SoapObject) soapObject1.getProperty(j);
                    Log.e("soap ","soap "+soapObject2.toString());

                    temperatura = soapObject2.getProperty(0).toString();
                    humedad = soapObject2.getProperty(1).toString();
                    fecha = soapObject2.getProperty(2).toString();
                    //idantena = soapObject2.getProperty(3).toString();


                    datosAntena.add(new DatosAntena(temperatura,humedad,fecha));
                }
                Log.e("No hay datos",temperatura.toString());
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(datosAntena != null) {
                Utils.DatosAntena = datosAntena;
                //validacion pos cargarlos fragment
                if(viewPager.getCurrentItem()== 0){
                    viewPager.setAdapter(adapter);
                }
            }else{
                Toast.makeText(MainActivity.this, "no hay datos en : " + datosAntena, Toast.LENGTH_LONG).show();
            }

        }
    }

    public class ServicioMisAntenas extends AsyncTask<String,Integer,Boolean>{
        boolean flag= true;
        ArrayList<MisAntenas> usuAntenas =new ArrayList<>();
        ArrayList<String> listaantenasmain =new ArrayList<>();


        //String id = getIntent().getStringExtra("idUsuario");

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "MisAntenas";
        final String SOAP_ACTION = "http://tempuri.org/MisAntenas";
        MisAntenas [] misAntenases;
        MisAntenas a;

        int idAntena;
        String nombreAntena;

        int idusu = Utils.idUsuario;

        @Override
        protected Boolean doInBackground(String... params) {

            SoapObject soapObject = new SoapObject(NAMESPACE,METHOD_NAME);
            soapObject.addProperty("idusu",idusu);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(soapObject);

            HttpTransportSE transportSE = new HttpTransportSE(URL);

            try{
                transportSE.call(SOAP_ACTION, soapEnvelope);
                SoapObject resultado = (SoapObject) soapEnvelope.getResponse();

                misAntenases = new MisAntenas[resultado.getPropertyCount()];

                for (int j =0;j<misAntenases.length;j++){
                    SoapObject data = (SoapObject) resultado.getProperty(j);
                    idAntena = Integer.parseInt(data.getProperty(0).toString());
                    nombreAntena = data.getProperty(1).toString();


                    a = new MisAntenas(idAntena,nombreAntena);
                    usuAntenas.add(a);
                    listaantenasmain.add(String.valueOf(idAntena));

                }

            }catch (Exception e){
                e.printStackTrace();
                flag = false;
            }

            return flag;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (usuAntenas!= null) {
                Utils.listaAntenas=usuAntenas;
                Utils.idantena=listaantenasmain;
                 if(viewPager.getCurrentItem()==0){
                    viewPager.setAdapter(adapter);
                }

                LlenarSpinner();

            }else{
                Toast.makeText(MainActivity.this, "NO hay datos :" +usuAntenas, Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void LlenarSpinner(){

        for (int i =0;i<Utils.listaAntenas.size();i++) {
            listaIdAntenas.add(Utils.listaAntenas.get(i).getNombreAntena());
        }

        ArrayAdapter<String> adapterDrop = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(), R.layout.appbar_filter_title, listaIdAntenas);

        adapterDrop.setDropDownViewResource(R.layout.appbar_filter_list);
        spListaAntenas = (Spinner) findViewById(R.id.sp_antenas);
        spListaAntenas.setAdapter(adapterDrop);

        spListaAntenas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    Utils.nombreAntena = parent.getItemAtPosition(position).toString();

                    if(Utils.listaAntenas.get(position).getNombreAntena().equals(Utils.nombreAntena)){
                       Utils.idAntena = Utils.listaAntenas.get(position).getIdAntena();
                        //Toast.makeText(MainActivity.this, "Antena id :" + Utils.idAntena, Toast.LENGTH_SHORT).show();
                    }

                antena = new ServicioDatosAntena();
                antena.execute();
                listarAlarma = new hiloListarAlarma();
                listarAlarma.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //-----------------------Notificaciones App-----------------------

    //-----------------------TIMER CLASS---------------------

    class timerNotificaciones extends TimerTask {


        @Override
        public void run() {

            /*HiloNotificaciones hilonoti =  new HiloNotificaciones();
            hilonoti.execute();*/


            if (respuestaStr==1) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);

                Intent goApp =  new Intent(MainActivity.this, MainActivity.class);
                PendingIntent accionNoti = PendingIntent.getActivity(MainActivity.this, 0, goApp, 0);
                builder.setContentIntent(accionNoti);




                builder.setSmallIcon(R.drawable.adver);
                builder.setAutoCancel(true);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cloud));
                builder.setContentTitle("Advertencia");
                builder.setContentText("Una de las variebles esta fuera de rango");


                NotificationManager notificacionAction = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificacionAction.notify(NOTIFICACION_ID, builder.build());
            }
        }

    }

    //--------------------HILO  NOTIFICACIONES ---------------------------------
    private class HiloNotificaciones extends AsyncTask<String, Integer, Boolean> {


        int idAntena = Utils.idAntena;
        String respuesta= null;



        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "notificacionFull";
        final String SOAP_ACTION = "http://tempuri.org/notificacionFull";

        @Override
        protected Boolean doInBackground(String... params) {

            boolean flagResul = true;

            //---------------------------------------------------
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("idenAntena", idAntena);

                //---------------------------------------------------

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                //---------------------------------------------------

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);
                SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
                respuesta =  resultado.toString();
                respuestaStr = Integer.parseInt(resultado.toString());
            } catch (Exception e1) {

                e1.printStackTrace();
                respuesta = "Error Garrafal";
                flagResul = false;
            }

            return flagResul;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Toast.makeText(MainActivity.this, ""+respuesta, Toast.LENGTH_LONG).show();
        }
    }


    //--------------------HILO BORRAR ALARMA---------------------------------
    public    class HiloBorrarAlarma extends AsyncTask<String, Integer, Boolean> {
        long id;
        public HiloBorrarAlarma(long id) {
            this.id = id;
        }

        String respuestaStr=null;

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = Utils.URL;
        final String METHOD_NAME = "eliminarAlarma";
        final String SOAP_ACTION = "http://tempuri.org/eliminarAlarma";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Toast.makeText(RegistroAlarmas.this, posis+"", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean flagResul = true;


            //---------------------------------------------------
            try {
                SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("idAlarma",id);

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
                //Toast.makeText(RegistroAlarmas.this,"Error Garrafal"+respuestaStr,Toast.LENGTH_LONG).show();
                flagResul = false;
            }

            return flagResul;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            // Toast.makeText(RegistroAlarmas.this, respuestaStr,Toast.LENGTH_LONG).show();
        }
    }




}
