package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.PilarModel;

import java.util.List;

public class SpinnerPilarAdapter extends ArrayAdapter<PilarModel> {

   public SpinnerPilarAdapter(@NonNull Context context, List<PilarModel> pilarModel){
            super(context, android.R.layout.simple_spinner_item, pilarModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaPilar());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaPilar());
            return view;
        }


    public String getNamaPilar(int position) {
       return getItem(position).getNamaPilar();
    }

    public Integer getPilarId(int position) {
        return getItem(position).getPilarId();
    }





    }
