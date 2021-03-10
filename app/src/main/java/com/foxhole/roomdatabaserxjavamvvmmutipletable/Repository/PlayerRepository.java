package com.foxhole.roomdatabaserxjavamvvmmutipletable.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.CharacterDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.PartyDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.PlayerDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.PartyDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.PlayerDatabase;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Character;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Player;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class PlayerRepository {

    private static final String TAG = "PlayerRepository";

    private PartyDao partyDao;
    private PlayerDao playerDao;
    private CharacterDao characterDao;
    private Flowable<List<Party>> allParties;
    private Flowable<List<Player>> allPlayers;
    private Flowable<List<Character>> allCharacters;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public PlayerRepository(Application application){
        PlayerDatabase playerDatabase = PlayerDatabase.getInstance(application);
        partyDao = playerDatabase.partyDao();
        playerDao = playerDatabase.playerDao();
        characterDao = playerDatabase.characterDao();


    }

    //Get all Party
    public Flowable<List<Party>> getAllParty(){
        return partyDao.getAllParties();
    }

    //Get Loading State
    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    //Get all Player under the specific Party
    public Flowable<List<Player>> getAllPlayers(int party_id) {
        return playerDao.getAllPlayersByParty(party_id);
    }

    public Flowable<List<Character>> getAllCharacters(int player_id){
        return characterDao.getAllCharactersByPlayer(player_id);
    }

    //Insert Party
    public void insertParty(final Party party){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                partyDao.insert(party);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Called");
                isLoading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage());
            }
        });
    }

    //Update Party
    public void updateParty(final Party party){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                partyDao.update(party);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called");
                    }
                });
    }

    //Delete Party
    public void deleteParty(final Party party){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                partyDao.delete(party);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: Called");
                        deleteAllPlayersByParty(party.getUid());
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());
                    }
                });
    }

    //Delete all Party
    public void deleteAllParty(){

        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                partyDao.deleteAllParty();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        deleteAllPlayers();
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }


    //Insert Player
    public void insertPlayer(final Player player){

        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                playerDao.insert(player);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Update Player
    public void updatePlayer(final Player player){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                playerDao.update(player);
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Delete Player
    public void deletePlayer(final Player player){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                playerDao.delete(player);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Player
    public void deleteAllPlayers(){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                playerDao.deleteAllPlayers();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Players By Party
    public void deleteAllPlayersByParty(final int party_id){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                playerDao.deleteAllPlayersUnderParty(party_id);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }
    //////Characters
    //Insert Character
    public void insertCharacter(final Character character){

        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                characterDao.insert(character);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Update Player
    public void updateCharacter(final Character character){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                characterDao.update(character);
            }
        }).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: Called"+e.getMessage());
                    }
                });
    }

    //Delete Player
    public void deleteCharacter(final Character character){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                characterDao.delete(character);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Player
    public void deleteAllCharacters(){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                characterDao.deleteAllCharacters();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }

    //Delete all Players By Party
    public void deleteAllCharactersByPlayer(final int player_id){
        isLoading.setValue(true);
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                characterDao.deleteAllCharactersUnderPlayer(player_id);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: Called");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: Called");
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }


}
