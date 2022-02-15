package com.apps.project.appstation.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.project.appstation.Adapters.AdaptadorAlarma;
import com.apps.project.appstation.Adapters.AdapterListaAntena;
import com.apps.project.appstation.Objects.MisAntenas;
import com.apps.project.appstation.R;
import com.apps.project.appstation.Utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Antenas extends Fragment {

    ListView listMisantenas;
    AdapterListaAntena adaptermisantenas;

    public static ArrayList<MisAntenas> arrayListAntenas;

    public Antenas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        listMisantenas = (ListView) v.findViewById(R.id.lv_misAntenas);
        arrayListAntenas = new ArrayList<>();
        arrayListAntenas = Utils.listaAntenas;
        if (arrayListAntenas!= null) {
            adaptermisantenas = new AdapterListaAntena(v.getContext(),arrayListAntenas);
            listMisantenas.setAdapter(adaptermisantenas);
        }else{
            Toast.makeText(v.getContext(), "No hay datos en :" + arrayListAntenas.size(), Toast.LENGTH_SHORT).show();
        }

        return v;
    }


}
