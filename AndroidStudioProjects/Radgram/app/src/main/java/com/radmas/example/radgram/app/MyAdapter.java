package com.radmas.example.radgram.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

/**
 * Created by raddev01 on 21/03/14.
 */

public class MyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] names;
    private String [] tel_numbers;
    private int[] images;

    public MyAdapter (Context context, String[] names, String[] tel_num, int[] images){
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.names= names;
        this.tel_numbers = tel_num;
        this.images= images;

    }

    @Override public View getView (int position, View recicledView, ViewGroup parent){
       if (recicledView == null){
           recicledView = inflater.inflate(R.layout.element2, null);
       }
       //nombres
       TextView text = (TextView) recicledView.findViewById(R.id.nombre);
       text.setText(names[position]);
       //imagenes del contacto
       ImageView image = (ImageView) recicledView.findViewById(R.id.foto);
       image.setImageResource(images[position]);
       //numeros de telefono
       TextView text2 = (TextView) recicledView.findViewById(R.id.telefono);
       text2.setText(tel_numbers[position]);
       return recicledView;
    }

    @Override public int getCount(){
        return names.length;
    }

    @Override public Object getItem (int position){
        return names[position];
    }

    @Override public long getItemId(int position){
        return position;
    }

}






