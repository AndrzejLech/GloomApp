package com.gloom.gloomapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gloom.gloomapp.Model.Party;
import com.gloom.gloomapp.R;
import com.gloom.gloomapp.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.AdapterViewHolder> {

    private List<Party> partyList;
    private OnPartyClickListener onPartyClickListener;

    public PartyAdapter(List<Party> partyList) {
        this.partyList = partyList;
    }

    public void setItemClickListener(OnPartyClickListener onPartyClickListener) {
        this.onPartyClickListener = onPartyClickListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.party_item_layout, null);
        AdapterViewHolder adapterViewHolder = new AdapterViewHolder(view, onPartyClickListener);
        return adapterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        Party singleParty = partyList.get(position);
        holder.mImageView.setImageBitmap(DataConverter.convertByteArray2Image(singleParty.getImage()));
        holder.mPartyText.setText(singleParty.getName());
        holder.mReputationText.setText(singleParty.getReputation());
    }

    public Party getPartyAt(int position) {
        Party party = partyList.get(position);
        party.setUid(partyList.get(position).getUid());
        return party;
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mReputationText;
        private ImageView mImageView;
        private TextView mPartyText;
        private MaterialCardView mCardView;
        private OnPartyClickListener onPartyClickListener;

        public AdapterViewHolder(@NonNull View itemView, OnPartyClickListener onPartyClickListener) {
            super(itemView);
            this.onPartyClickListener = onPartyClickListener;
            mReputationText = itemView.findViewById(R.id.repuration);
            mImageView = itemView.findViewById(R.id.partyImage);
            mPartyText = itemView.findViewById(R.id.text_view);
            mCardView = itemView.findViewById(R.id.card_view);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Party currentParty = partyList.get(position);
            onPartyClickListener.onPartyClick(currentParty);
        }
    }

    public interface OnPartyClickListener {
        void onPartyClick(Party party);
    }
}
