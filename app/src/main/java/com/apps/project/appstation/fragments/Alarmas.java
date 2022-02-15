package com.apps.project.appstation.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.apps.project.appstation.Adapters.AdaptadorAlarma;
import com.apps.project.appstation.Objects.POJORegistro;
import com.apps.project.appstation.R;
import com.apps.project.appstation.RegistroAlarmas;
import com.apps.project.appstation.Utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Alarmas extends Fragment  {

    ListView lvAlarmas;
    AdaptadorAlarma adapter;
    public static ArrayList<POJORegistro> arraListaAntena ;

    private AlarmaInter clickBorrar ;
    private AlarmaInter clickActualizar ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);

        lvAlarmas = (ListView) v.findViewById(R.id.lvAlarmas);
        arraListaAntena = new ArrayList<>();
        arraListaAntena= Utils.ListaAlarmas;

        if (arraListaAntena != null) {
            adapter = new AdaptadorAlarma(v.getContext(), arraListaAntena);
            lvAlarmas.setAdapter(adapter);
        }else{

            Toast.makeText(v.getContext(), "No hay datos en :"+ arraListaAntena.size(), Toast.LENGTH_SHORT).show();
        }

        lvAlarmas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickBorrar.clicKBorraAlarma(arraListaAntena.get(position).getId());
                return true;

            }
        });

        lvAlarmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickActualizar.clicActualizarAlarma(arraListaAntena.get(position));
            }
        });

        return v;
    }

    public interface AlarmaInter {
        void clicKBorraAlarma(long id);
        void clicActualizarAlarma(POJORegistro listadoDeAlarmas);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        clickBorrar=(AlarmaInter)activity;
        clickActualizar=(AlarmaInter)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        clickBorrar=null;
        clickActualizar=null;
    }

}
