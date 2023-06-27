package com.example.adisti.PtsAdapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adisti.Model.ProposalModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.PtsFragment.PtsDetailHasilSurveyFragment;
import com.example.adisti.PtsFragment.PtsDetailSurveyFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PengajuInterface;
import com.zerobranch.layout.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PtsHasilSurveyAdapter extends RecyclerView.Adapter<PtsHasilSurveyAdapter.ViewHolder> {
    Context context;
    List<ProposalModel>proposalModelList;
    PengajuInterface pengajuInterface;


    public PtsHasilSurveyAdapter(Context context, List<ProposalModel> proposalModelList) {
        this.context = context;
        this.proposalModelList = proposalModelList;
    }

    @NonNull
    @Override
    public PtsHasilSurveyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_proposal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PtsHasilSurveyAdapter.ViewHolder holder, int position) {
        holder.tvNamaProposal.setText(proposalModelList.get(holder.getAdapterPosition()).getBantuanDiajukan());
        holder.tvNoProposal.setText(proposalModelList.get(holder.getAdapterPosition()).getNoProposal());

        if (proposalModelList.get(holder.getAdapterPosition()).getStatus().equals("")) {

            if (proposalModelList.get(holder.getAdapterPosition()).getVerified().equals("1")) {
                holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_proposal_diterima));
            } else if (proposalModelList.get(holder.getAdapterPosition()).getVerified().equals("2")) {
                holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_menunggu));
            }else {
                holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_ditolak));
            }
        }else  if (proposalModelList.get(holder.getAdapterPosition()).getStatus().equals("Diterima")) {

            holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_proposal_verifikasi));

        }else {

            holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_ditolak));

        }

        if (proposalModelList.get(holder.getAdapterPosition()).getVerified().equals("1")) {
            holder.swipeLayout.setEnabledSwipe(false);
        }else if (proposalModelList.get(holder.getAdapterPosition()).getVerified().equals("0")) {
            holder.swipeLayout.setEnabledSwipe(false);
        }
        else {
            holder.swipeLayout.setEnabledSwipe(true);

        }

    }

    @Override
    public int getItemCount() {
        return proposalModelList.size();
    }

    public void filter(ArrayList<ProposalModel>filterList) {
        proposalModelList = filterList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaProposal, tvNoProposal, tvHapus;
        ImageView ivStatusProposal;
        CardView cvNoProposal, cvMain;
        SwipeLayout swipeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaProposal = itemView.findViewById(R.id.tvNamaProposal);
            tvNoProposal = itemView.findViewById(R.id.tvNoProposal);
            ivStatusProposal = itemView.findViewById(R.id.ivStatus);
            cvNoProposal = itemView.findViewById(R.id.cvNoProposal);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            tvHapus = itemView.findViewById(R.id.tvHapus);
            cvMain = itemView.findViewById(R.id.cvMain);
            pengajuInterface = DataApi.getClient().create(PengajuInterface.class);

            swipeLayout.setOnActionsListener(new SwipeLayout.SwipeActionsListener() {
                @Override
                public void onOpen(int direction, boolean isContinuous) {
                    if (direction == SwipeLayout.LEFT) {

                        pengajuInterface.deleteProposal(proposalModelList.get(getAdapterPosition()).getProposalId())
                                .enqueue(new Callback<ResponseModel>() {
                                    @Override
                                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                        ResponseModel responseModel = response.body();
                                        if(response.isSuccessful() && responseModel.getCode() == 200) {
                                            proposalModelList.remove(getAdapterPosition());
                                            notifyDataSetChanged();
                                            notifyItemRangeChanged(getAdapterPosition(), proposalModelList.size());
                                            notifyItemRangeRemoved(getAdapterPosition(), proposalModelList.size());
                                            Toasty.normal(context, "Berhasil menghapus proposal", Toasty.LENGTH_SHORT).show();

                                        }else {
                                            Toasty.error(context, "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
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

                    }
                }

                @Override
                public void onClose() {

                }
            });
            cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new PtsDetailHasilSurveyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("proposal_id", proposalModelList.get(getAdapterPosition()).getProposalId());
                    bundle.putString("no_urut_proposal", proposalModelList.get(getAdapterPosition()).getProposalId());
                    fragment.setArguments(bundle);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null).replace(R.id.framePts, fragment).commit();
                }
            });



        }


    }


}
