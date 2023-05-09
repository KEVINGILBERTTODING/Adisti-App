package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.adisti.Model.BidangManfaatPerusahaanModel;

import java.util.List;

public class SpinnerBidangManfaatPerusahaanAdapter extends ArrayAdapter<BidangManfaatPerusahaanModel> {

   public SpinnerBidangManfaatPerusahaanAdapter(@NonNull Context context, List<BidangManfaatPerusahaanModel> bidangManfaatPerusahaanModel){
            super(context, android.R.layout.simple_spinner_item, bidangManfaatPerusahaanModel);
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(getItem(position).getNamaBidang());
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setText(getItem(position).getNamaBidang());
            return view;
        }


    public String getNamaBidang(int position) {
       return getItem(position).getNamaBidang();
    }

    public Integer getBidangManfaatId(int position) {
        return getItem(position).getBidangManfaatId();
    }





    }
