package com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Character;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository.PlayerRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CharacterViewModel extends AndroidViewModel {
    private PlayerRepository characterRepository;


    public CharacterViewModel(@NonNull Application application) {
        super(application);
        characterRepository = new PlayerRepository(application);
    }

    //Get all Player under the specific Player
    public Flowable<List<Character>> getAllCharacters(int character_id){
        return characterRepository.getAllCharacters(character_id);
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return characterRepository.getIsLoading();
    }
    //Insert Player
    public void insert(Character character){
        characterRepository.insertCharacter(character);
    }

    //Update Character
    public void update(Character character){
        characterRepository.updateCharacter(character);
    }

    //Delete Character
    public void delete(Character character){
        characterRepository.deleteCharacter(character);
    }

    //Delete All Character
    public void deleteAllCharactersByPlayer( int player_id){
        characterRepository.deleteAllCharactersByPlayer(player_id);
    }
}
