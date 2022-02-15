package com.apps.project.appstation.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apps.project.appstation.Objects.DatosAntena;
import com.apps.project.appstation.R;
import com.apps.project.appstation.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeison on 05/09/2015.
 */
public class AdapterListaDatosAntena extends BaseAdapter{

    Context context;

    ArrayList<DatosAntena> datos=new ArrayList<>();

    public AdapterListaDatosAntena(ArrayList<DatosAntena> datosAntenas, Context context) {
        this.datos = datosAntenas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int i) {
        return datos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        TextView fecha;
        TextView term;
        TextView hum;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();
        if (view==null){
            view=View.inflate(context,R.layout.adapter_datos_antena,null);
            holder.term=(TextView)view.findViewById(R.id.item_temperatura);
            holder.fecha=(TextView)view.findViewById(R.id.item_fecha);
            holder.hum=(TextView)view.findViewById(R.id.item_humedad);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

        holder.term.setText(datos.get(i).getTemperatura());
        holder.fecha.setText(datos.get(i).getFecha());
        holder.hum.setText(datos.get(i).getHumedad());

        return view;
    }
}
