package com.example.adisti.PengajuAdapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adisti.Model.NotificationModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.zerobranch.layout.SwipeLayout;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<NotificationModel>notificationModelList;
    PengajuInterface pengajuInterface;

    public NotificationAdapter(Context context, List<NotificationModel> notificationModelList) {
        this.context = context;
        this.notificationModelList = notificationModelList;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_notifiikasi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        if (notificationModelList.get(holder.getAdapterPosition()).getCode() == 1) {
            holder.tvStatus.setText("Proposal Anda diterima!");
            holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_diterima));
        }else {
            holder.tvStatus.setText("Proposal Anda ditolak!");
            holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_ditolak));
        }

        holder.tvTanggal.setText(notificationModelList.get(holder.getAdapterPosition()).getDate());

    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvNotification;
        ImageView ivStatus;
        TextView tvStatus, tvTanggal, tvHapus;
        SwipeLayout swipeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvNotification = itemView.findViewById(R.id.cvMain);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTanggal = itemView.findViewById(R.id.tvDate);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            tvHapus = itemView.findViewById(R.id.tvHapus);
            pengajuInterface = DataApi.getClient().create(PengajuInterface.class);



            swipeLayout.setOnActionsListener(new SwipeLayout.SwipeActionsListener() {
                @Override
                public void onOpen(int direction, boolean isContinuous) {
                    if (direction == SwipeLayout.LEFT){
                        pengajuInterface.deleteNotifcation(Integer.parseInt(notificationModelList.get(getAdapterPosition()).getNotifId()))
                                .enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if (response.isSuccessful() && response.body().getCode() == 200) {
                                            notificationModelList.remove(getAdapterPosition());
                                            notifyDataSetChanged();
                                            notifyItemRangeChanged(getAdapterPosition(), notificationModelList.size());
                                            notifyItemRangeRemoved(getAdapterPosition(), notificationModelList.size());
                                            Toasty.normal(context, "Berhasil meenghapus", Toasty.LENGTH_SHORT).show();

                                        }else {
                                            Toasty.error(context, "Gagal menghapus notification", Toasty.LENGTH_SHORT).show();
                                            tvHapus.setText("Gagal menghapus data");

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                                        Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.dialog_no_connection_close);
                                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        final Button btnOke = dialog.findViewById(R.id.btnOke);
                                        btnOke.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                        tvHapus.setText("Tidak ada koneksi internet");

                                    }
                                });


                    }else {

                    }
                }

                @Override
                public void onClose() {

                }
            });

        }
    }
}
