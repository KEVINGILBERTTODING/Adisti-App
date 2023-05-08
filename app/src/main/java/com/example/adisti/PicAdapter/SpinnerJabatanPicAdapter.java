package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.JabatanPicModel;

import java.util.List;

public class SpinnerJabatanPicAdapter extends ArrayAdapter<JabatanPicModel> {

   public SpinnerJabatanPicAdapter(@NonNull Context context, List< JabatanPicModel > jabatanPicModel){
            super(context, android.R.layout.simple_spinner_item, jabatanPicModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaJabatan());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaJabatan());
            return view;
        }


    public String getNamaJabatanPic(int position) {
       return getItem(position).getNamaJabatan();
    }







    }
