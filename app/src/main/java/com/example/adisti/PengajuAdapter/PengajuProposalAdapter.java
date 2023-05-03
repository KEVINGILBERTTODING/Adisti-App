package com.example.adisti.PengajuAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adisti.Model.ProposalModel;
import com.example.adisti.R;

import java.util.List;

public class PengajuProposalAdapter extends RecyclerView.Adapter<PengajuProposalAdapter.ViewHolder> {
    Context context;
    List<ProposalModel>proposalModelList;


    public PengajuProposalAdapter(Context context, List<ProposalModel> proposalModelList) {
        this.context = context;
        this.proposalModelList = proposalModelList;
    }

    @NonNull
    @Override
    public PengajuProposalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_proposal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengajuProposalAdapter.ViewHolder holder, int position) {
        holder.tvNamaProposal.setText(proposalModelList.get(holder.getAdapterPosition()).getBantuanDiajukan());
        holder.tvNoProposal.setText(proposalModelList.get(holder.getAdapterPosition()).getNoProposal());

        if (proposalModelList.get(holder.getAdapterPosition()).getStatus().equals("")) {
//            holder.cvNoProposal.setCardBackgroundColor(context.getColor(R.color.yelllow));
            holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_menunggu));
        }else  if (proposalModelList.get(holder.getAdapterPosition()).getStatus().equals("Diterima")) {
//            holder.cvNoProposal.setCardBackgroundColor(context.getColor(R.color.green));
            holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_diterima));

        }else {
//            holder.cvNoProposal.setCardBackgroundColor(context.getColor(R.color.red));
            holder.ivStatusProposal.setImageDrawable(context.getDrawable(R.drawable.ic_ditolak));


        }

    }

    @Override
    public int getItemCount() {
        return proposalModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaProposal, tvNoProposal;
        ImageView ivStatusProposal;
        CardView cvNoProposal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaProposal = itemView.findViewById(R.id.tvNamaProposal);
            tvNoProposal = itemView.findViewById(R.id.tvNoProposal);
            ivStatusProposal = itemView.findViewById(R.id.ivStatus);
            cvNoProposal = itemView.findViewById(R.id.cvNoProposal);
        }
    }
}
