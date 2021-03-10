package com.gloom.gloomapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gloom.gloomapp.Model.Character;
import com.gloom.gloomapp.R;
import com.gloom.gloomapp.Utils.DataConverter;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharactersViewHolder> {

    private List<Character> characterList;

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public CharacterAdapter.CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_character_layout, null);
        CharacterAdapter.CharactersViewHolder charactersViewHolder = new CharacterAdapter.CharactersViewHolder(view);
        return charactersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.CharactersViewHolder holder, int position) {

        Character character = characterList.get(position);
        holder.mTitleView.setText(character.getName());
        holder.mImageView.setImageBitmap(DataConverter.convertByteArray2Image(character.getImage()));
        holder.mClazzView.setText(character.getClazz());
    }

    public Character getCharacterAt(int position) {
        Character character = characterList.get(position);
        character.setUid(characterList.get(position).getUid());
        return character;
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public class CharactersViewHolder extends RecyclerView.ViewHolder {

        public TextView mTitleView;
        public ImageView mImageView;
        public TextView mClazzView;

        public CharactersViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.partyImage);
            mTitleView = itemView.findViewById(R.id.partyName);
            mClazzView = itemView.findViewById(R.id.CclassName);
        }
    }
}
