package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.LoketModel;

import java.util.List;

public class SpinnerKodeLoketAdapter extends ArrayAdapter<LoketModel> {

   public SpinnerKodeLoketAdapter(@NonNull Context context, List< LoketModel > loketModel){
            super(context, android.R.layout.simple_spinner_item, loketModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaLoket());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaLoket());
            return view;
        }


    public String getLoketId(int position) {
       return getItem(position).getLoketId();
    }
    public String getLoketName (int position) {
       return getItem(position).getNamaLoket();
    }





    }
