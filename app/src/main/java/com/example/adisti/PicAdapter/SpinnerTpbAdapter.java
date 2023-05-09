package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.PilarModel;
import com.example.adisti.Model.TpbModel;

import java.util.List;

public class SpinnerTpbAdapter extends ArrayAdapter<TpbModel> {

   public SpinnerTpbAdapter(@NonNull Context context, List<TpbModel> pilarModel){
            super(context, android.R.layout.simple_spinner_item, pilarModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaTpb());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaTpb());
            return view;
        }


    public String getNamaTpb(int position) {
       return getItem(position).getNamaTpb();
    }

    public Integer getTpbId(int position) {
        return getItem(position).getTpbId();
    }





    }
