package com.apps.project.appstation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Splahs extends AppCompatActivity {
    ProgressBar pbSplahs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahs);
        pbSplahs = (ProgressBar) findViewById(R.id.progressBar);
        new TareaSplahs().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splahs, menu);
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

    public class TareaSplahs extends AsyncTask<String,Integer,Void>{

        Boolean msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbSplahs.setMax(100);
            pbSplahs.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int pos = values[0];
            pbSplahs.setProgress(pos);
        }

        @Override
        protected Void doInBackground(String... params) {
         for (int i = 0; i<4;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i*25);
            }
            return null;
        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
                if(isNetworkAvailable()== true) {
                    Intent goLogin = new Intent(Splahs.this, LoginActivity.class);
                    Splahs.this.startActivity(goLogin);
                    Splahs.this.finish();
                }else {
                    Toast.makeText(Splahs.this,"NO HAY CONEXION INTERNET",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
