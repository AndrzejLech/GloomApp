package com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Player;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository.PlayerRepository;

import java.util.List;

import io.reactivex.Flowable;

public class PlayerActivityViewModel extends AndroidViewModel {

    private PlayerRepository playerRepository;


    public PlayerActivityViewModel(@NonNull Application application) {
        super(application);
        playerRepository = new PlayerRepository(application);
    }

    //Get all Player under the specific Player
    public Flowable<List<Player>> getAllPlayers(int player_id){
        return playerRepository.getAllPlayers(player_id);
    }

    public int listGetCount(int player_id){
        return Integer.parseInt(playerRepository.getAllPlayers(player_id).count().toString());
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return playerRepository.getIsLoading();
    }
    //Insert Player
    public void insert(Player player){
        playerRepository.insertPlayer(player);
    }

    //Update Player
    public void update(Player player){
        playerRepository.updatePlayer(player);
    }

    //Delete Player
    public void delete(Player player){
        playerRepository.deletePlayer(player);
    }

    //Delete All Player
    public void deleteAllPlayersByParty( int player_id){
        playerRepository.deleteAllPlayersByParty(player_id);
    }
}
