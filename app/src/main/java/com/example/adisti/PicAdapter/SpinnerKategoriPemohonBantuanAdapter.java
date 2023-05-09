package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.KategoriPemohonBantuanModel;

import java.util.List;

public class SpinnerKategoriPemohonBantuanAdapter extends ArrayAdapter<KategoriPemohonBantuanModel> {

   public SpinnerKategoriPemohonBantuanAdapter(@NonNull Context context, List<KategoriPemohonBantuanModel> kategoripemohonbantuan){
            super(context, android.R.layout.simple_spinner_item, kategoripemohonbantuan);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getPemohonBantuan());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getPemohonBantuan());
            return view;
        }


    public String getNamaKategoriPemohonBantuan(int position) {
       return getItem(position).getPemohonBantuan();
    }

    public Integer getKategoriPemohonBantuanId(int position) {
        return getItem(position).getPemohonBantuanId();
    }





    }
