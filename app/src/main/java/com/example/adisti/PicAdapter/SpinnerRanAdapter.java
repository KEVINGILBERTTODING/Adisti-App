package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.RanModel;

import java.util.List;

public class SpinnerRanAdapter extends ArrayAdapter<RanModel> {

   public SpinnerRanAdapter(@NonNull Context context, List<RanModel>ranModel){
            super(context, android.R.layout.simple_spinner_item,ranModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaRan());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaRan());
            return view;
        }


    public String getNamaRan(int position) {
       return getItem(position).getNamaRan();
    }

    public String getRanId(int position) {
        return getItem(position).getRanId();
    }





    }
