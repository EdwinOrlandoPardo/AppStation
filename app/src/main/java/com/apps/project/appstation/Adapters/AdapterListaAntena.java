package com.apps.project.appstation.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.apps.project.appstation.Objects.MisAntenas;
import com.apps.project.appstation.R;

import java.util.ArrayList;

/**
 * Created by Edwin on 01/09/2015.
 */
public class AdapterListaAntena extends BaseAdapter{


    Context contexMisAntenas;
    ArrayList<MisAntenas> listaAdpaterAntenas;

    public AdapterListaAntena(Context contexMisAntenas ,ArrayList<MisAntenas> listaAdpaterAntenas) {
        this.contexMisAntenas = contexMisAntenas;
        this.listaAdpaterAntenas = listaAdpaterAntenas;
    }

    @Override
    public int getCount() {
        return listaAdpaterAntenas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAdpaterAntenas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Handler handler = new Handler();
        if (convertView == null){
            convertView= View.inflate(contexMisAntenas, R.layout.list_row, null);

            handler.nomantena = (TextView) convertView.findViewById(R.id.tv_antenaNom);
            convertView.setTag(handler);
        }else{
            handler = (Handler) convertView.getTag();
        }
        handler.nomantena.setText(listaAdpaterAntenas.get(position).getNombreAntena());
        return convertView;
    }

    public static class Handler{

        TextView nomantena;
    }

}
