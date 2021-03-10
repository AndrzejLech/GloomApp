package com.foxhole.roomdatabaserxjavamvvmmutipletable.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Player;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.R;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Utils.DataConverter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {

    private List<Player> playerList;
    private OnPlayerClickListener onPlayerClickListener;
    private MaterialCardView mCardView;

    public PlayersAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setItemClickListener(PlayersAdapter.OnPlayerClickListener onPlayerClickListener) {
        this.onPlayerClickListener = onPlayerClickListener;
    }

    @NonNull
    @Override
    public PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_player_layout, null);
        PlayersViewHolder playersViewHolder = new PlayersViewHolder(view, onPlayerClickListener);
        return playersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersViewHolder holder, int position) {

        Player movie = playerList.get(position);
        holder.mTitleView.setText(movie.getName());
        holder.mImageView.setImageBitmap(DataConverter.convertByteArray2Image(movie.getImage()));
    }

    public Player getPlayerAt(int position) {
        Player movie = playerList.get(position);
        movie.setUid(playerList.get(position).getUid());
        return movie;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnPlayerClickListener onPlayerClickListener;
        public TextView mTitleView;
        public ImageView mImageView;


        public PlayersViewHolder(@NonNull View itemView, OnPlayerClickListener onPlayerClickListener) {
            super(itemView);
            this.onPlayerClickListener = onPlayerClickListener;
            mImageView = itemView.findViewById(R.id.partyImage);
            mTitleView = itemView.findViewById(R.id.partyName);
            mCardView = itemView.findViewById(R.id.select_image);
            mCardView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Player currentPlayer = playerList.get(position);
            onPlayerClickListener.onPlayerClick(currentPlayer);
        }
    }

    public interface OnPlayerClickListener {
        void onPlayerClick(Player player);
    }
}
