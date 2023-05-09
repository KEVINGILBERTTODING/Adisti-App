package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.IndikatorBidangManfaatPerusahaanModel;
import com.example.adisti.Model.KategoriPemohonBantuanModel;

import java.util.List;

public class SpinnerIndikatorBidangManfaatAdapter extends ArrayAdapter<IndikatorBidangManfaatPerusahaanModel> {

   public SpinnerIndikatorBidangManfaatAdapter(@NonNull Context context, List<IndikatorBidangManfaatPerusahaanModel> indikatorBidangManfaat){
            super(context, android.R.layout.simple_spinner_item, indikatorBidangManfaat);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaIndikator());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaIndikator());
            return view;
        }


    public String getNamaIndikator(int position) {
       return getItem(position).getNamaIndikator();
    }

    public Integer getIndikatorId(int position) {
        return getItem(position).getIndikatorId();
    }





    }
