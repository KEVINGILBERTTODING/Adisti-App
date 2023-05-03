package com.example.adisti.PicAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adisti.Model.ProposalModel;
import com.example.adisti.R;

import java.util.List;

public class PicProposalAdapter extends RecyclerView.Adapter<PicProposalAdapter.ViewHolder> {
    Context context;
    List<ProposalModel>proposalModelList;


    public PicProposalAdapter(Context context, List<ProposalModel> proposalModelList) {
        this.context = context;
        this.proposalModelList = proposalModelList;
    }

    @NonNull
    @Override
    public PicProposalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_proposal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicProposalAdapter.ViewHolder holder, int position) {
        holder.tvNamaProposal.setText(proposalModelList.get(holder.getAdapterPosition()).getBantuanDiajukan());

    }

    @Override
    public int getItemCount() {
        return proposalModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaProposal;
        ImageView ivStatusProposal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaProposal = itemView.findViewById(R.id.tvNamaProposal);
        }
    }
}
