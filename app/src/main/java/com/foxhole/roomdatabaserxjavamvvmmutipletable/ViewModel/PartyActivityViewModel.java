package com.foxhole.roomdatabaserxjavamvvmmutipletable.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository.PlayerRepository;

import java.util.List;

import io.reactivex.Flowable;

public class PartyActivityViewModel extends AndroidViewModel {

    private PlayerRepository partyRepository;

    public PartyActivityViewModel(@NonNull Application application) {
        super(application);

        partyRepository = new PlayerRepository(application);
    }

    //Get all Party
    public Flowable<List<Party>> getAllParty() {
        return partyRepository.getAllParty();
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading() {
        return partyRepository.getIsLoading();
    }

    //Insert Party
    public void insert(Party party) {
        partyRepository.insertParty(party);
    }

    //Update Party
    public void updateParty(Party party) {
        partyRepository.updateParty(party);
    }

    //Delete Party
    public void deleteParty(Party party) {
        partyRepository.deleteParty(party);
    }

    //Delete All Party
    public void deleteAllParty() {
        partyRepository.deleteAllParty();
    }

}
