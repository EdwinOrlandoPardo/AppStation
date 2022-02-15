package com.apps.project.appstation.fragments;


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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Historial extends Fragment {

    AdapterListaDatosAntena adapterDatosAntena;
    ListView listView;

    ArrayList<DatosAntena> listDatos;


    public Historial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_tab4, container, false);
        listDatos = new ArrayList<>();

        listView = (ListView) v.findViewById(R.id.tab4_list);
        listDatos = Utils.DatosAntena;
        if (listDatos!= null) {
            adapterDatosAntena = new AdapterListaDatosAntena(listDatos, v.getContext());
        }else{
          Toast.makeText(v.getContext(), "Datos antenas home :" + listDatos.size(), Toast.LENGTH_SHORT).show();
        }


        listView.setAdapter(adapterDatosAntena);
        return v;
    }


}
