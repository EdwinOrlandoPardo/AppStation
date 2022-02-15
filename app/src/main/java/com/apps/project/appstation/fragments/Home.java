package com.apps.project.appstation.fragments;


import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.project.appstation.Adapters.AdapterListaDatosAntena;
import com.apps.project.appstation.Objects.DatosAntena;
import com.apps.project.appstation.R;
import com.apps.project.appstation.Utils.Utils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.kobjects.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    AdapterListaDatosAntena adapterDatosAntena;
    ListView listView;

    ArrayList<DatosAntena>  listDatos;

    LineChart line ;
    String mainData;



    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);

        listDatos = new ArrayList<>();

        listView = (ListView) v.findViewById(R.id.tab1_list);
        listDatos = Utils.DatosAntena;
        adapterDatosAntena = new AdapterListaDatosAntena(listDatos,v.getContext());

        //Toast.makeText(v.getContext(), "Datos antenas home :" + Utils.DatosAntena.size(), Toast.LENGTH_SHORT).show();

        listView.setAdapter(adapterDatosAntena);

        line = (LineChart) v.findViewById(R.id.lc_graphic);

        if(listDatos.size() !=0){
            LineData data = new LineData(getXAxisValues(),getDataSets());
            line.setData(data);
            line.setDescription("My Chart");
            line.animateXY(2000, 2000);
            line.invalidate();
        }



        return v;




  }


    public ArrayList<LineDataSet> getDataSets(){

        ArrayList data = null;

        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<Entry> humedadentries = new ArrayList<>();

        listDatos = new ArrayList<>();

        listDatos = Utils.DatosAntena;

        if (listDatos.size()<0){
            return  null;
        }else{

            for (int i =0;i<=9;i++){
                humedadentries.add(new Entry(Float.parseFloat(listDatos.get(i).getHumedad()),i));
                entries.add(new Entry(Float.parseFloat(listDatos.get(i).getTemperatura()),i));
            }

          /*  humedadentries.add(new Entry(Float.parseFloat(listDatos.get(0).getHumedad()),0));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(1).getHumedad()),1));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(2).getHumedad()),2));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(3).getHumedad()),3));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(4).getHumedad()),4));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(5).getHumedad()),5));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(6).getHumedad()),6));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(7).getHumedad()),7));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(8).getHumedad()),8));
            humedadentries.add(new Entry(Float.parseFloat(listDatos.get(9).getHumedad()),9));


            entries.add(new Entry(Float.parseFloat(listDatos.get(0).getTemperatura()),0));
            entries.add(new Entry(Float.parseFloat(listDatos.get(1).getTemperatura()),1));
            entries.add(new Entry(Float.parseFloat(listDatos.get(2).getTemperatura()),2));
            entries.add(new Entry(Float.parseFloat(listDatos.get(3).getTemperatura()),3));
            entries.add(new Entry(Float.parseFloat(listDatos.get(4).getTemperatura()),4));
            entries.add(new Entry(Float.parseFloat(listDatos.get(5).getTemperatura()),5));
            entries.add(new Entry(Float.parseFloat(listDatos.get(6).getTemperatura()),6));
            entries.add(new Entry(Float.parseFloat(listDatos.get(7).getTemperatura()),7));
            entries.add(new Entry(Float.parseFloat(listDatos.get(8).getTemperatura()),8));
            entries.add(new Entry(Float.parseFloat(listDatos.get(9).getHumedad()),9));*/


            LineDataSet set = new LineDataSet(entries,"Temperatura");
            LineDataSet set2 = new LineDataSet(humedadentries,"Humedad");
            set.setColor(Color.rgb( 155,0, 0));
            set2.setColor(Color.rgb(0,0,125));
            data = new ArrayList();
            data.add(set);
            data.add(set2);


        }



        return  data;
    }




   private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        xAxis.add("JUL");
        xAxis.add("AGT");
        xAxis.add("SEP");
        xAxis.add("OCT");
        return xAxis;
    }

}
