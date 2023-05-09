package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.PihakPenerimaBantuanModel;

import java.util.List;

public class SpinnerPemohonBantuanAdapter extends ArrayAdapter<PihakPenerimaBantuanModel> {

   public SpinnerPemohonBantuanAdapter(@NonNull Context context, List<PihakPenerimaBantuanModel> penerimaBantuan){
            super(context, android.R.layout.simple_spinner_item, penerimaBantuan);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaPihak());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaPihak());
            return view;
        }


    public String getNamaPihak(int position) {
       return getItem(position).getNamaPihak();
    }





    }
