package com.example.adisti.PengajuFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adisti.Model.NotificationModel;
import com.example.adisti.PengajuAdapter.NotificationAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuNotificationFragment extends Fragment {
    TextView tvEmpty;
    RecyclerView rvNotifikasi;
    ExtendedFloatingActionButton fabDeleteNotifikasi;
    LinearLayoutManager linearLayoutManager;
    List<NotificationModel>notificationModelList;
    NotificationAdapter notificationAdapter;
    SharedPreferences sharedPreferences;
    String userid;
    PengajuInterface pengajuInterface;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengaju_notification, container, false);
        init(view);
        getAllNotification();

        return view;
    }

    private void init(View view) {
        tvEmpty = view.findViewById(R.id.tvEmpty);
        rvNotifikasi = view.findViewById(R.id.rvNotifikasi);
        fabDeleteNotifikasi = view.findViewById(R.id.btnDeleteNotifikasi);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("user_id", null);
        pengajuInterface = DataApi.getClient().create(PengajuInterface.class);
    }

    private void getAllNotification (){
        Dialog progressBar = new Dialog(getContext());
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setContentView(R.layout.dialog_progress_bar);
        progressBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressBar.show();

        pengajuInterface.getAllNotification(userid).enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                if (response.isSuccessful() && response.body().size() > 0) {
                    notificationModelList = response.body();
                    tvEmpty.setVisibility(View.GONE);
                    notificationAdapter = new NotificationAdapter(getContext(), notificationModelList);
                    linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvNotifikasi.setLayoutManager(linearLayoutManager);
                    rvNotifikasi.setAdapter(notificationAdapter);
                    rvNotifikasi.setHasFixedSize(true);
                    progressBar.dismiss();

                }else {
                    tvEmpty.setVisibility(View.VISIBLE);
                    progressBar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvEmpty.setText("Tidak ada koneksi internet.");
                progressBar.dismiss();
                Dialog dialogNoConnection = new Dialog(getContext());
                dialogNoConnection.setContentView(R.layout.dialog_no_connection);
                dialogNoConnection.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogNoConnection.setCanceledOnTouchOutside(false);
                Button btnRefresh = dialogNoConnection.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       getAllNotification();
                        dialogNoConnection.dismiss();
                    }
                });
                dialogNoConnection.show();

            }
        });



    }
}