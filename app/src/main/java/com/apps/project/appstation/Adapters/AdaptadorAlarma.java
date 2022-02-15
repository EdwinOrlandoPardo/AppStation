package com.apps.project.appstation.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apps.project.appstation.Objects.POJORegistro;
import com.apps.project.appstation.R;

import java.util.ArrayList;

/**
 * Created by Edwin on 12/09/2015.
 */
public class AdaptadorAlarma extends BaseAdapter {

    Context contextoAdapter;
    ArrayList<POJORegistro> listaAdapter;

    public AdaptadorAlarma(Context contextoAdapter, ArrayList<POJORegistro> listaAdapter) {
        this.contextoAdapter = contextoAdapter;
        this.listaAdapter = listaAdapter;
    }

    @Override
    public int getCount() {
        return listaAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Cabecera{

        TextView tvAlarma, tvTempeMax, tvTempeMin, tvHumeMax, tvHumeMin ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cabecera myCabecera =  new Cabecera();

        if(convertView == null ){
            convertView= View.inflate(contextoAdapter, R.layout.activity_adaptador_alarma, null);

            myCabecera.tvAlarma = (TextView)convertView.findViewById(R.id.tvAlarma);
            myCabecera.tvTempeMax = (TextView)convertView.findViewById(R.id.tvTempeMax);
            myCabecera.tvTempeMin = (TextView)convertView.findViewById(R.id.tvTempeMin);
            myCabecera.tvHumeMax = (TextView)convertView.findViewById(R.id.tvHumeMax);
            myCabecera.tvHumeMin = (TextView)convertView.findViewById(R.id.tvHumeMim);


            convertView.setTag(myCabecera);
        }else{

            myCabecera =(Cabecera)convertView.getTag();
        }

        myCabecera.tvAlarma.setText(listaAdapter.get(position).getNameAlarma());
        myCabecera.tvTempeMax.setText(listaAdapter.get(position).getTempeMax());
        myCabecera.tvTempeMin.setText(listaAdapter.get(position).getTempeMin());
        myCabecera.tvHumeMax.setText(listaAdapter.get(position).getHumeMax());
        myCabecera.tvHumeMin.setText(listaAdapter.get(position).getHumeMin());

        return convertView;
    }
}
