package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Player;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface PlayerDao {

    @Insert
    void insert(Player player);

    @Update
    void update(Player player);

    @Delete
    void delete(Player player);

    @Query("DELETE FROM player_table")
    void deleteAllPlayers();

    @Query("SELECT * FROM player_table WHERE party_id LIKE :party_id")
    Flowable<List<Player>> getAllPlayersByParty(int party_id);

    @Query("DELETE FROM player_table WHERE party_id==:party_id")
    void deleteAllPlayersUnderParty(int party_id);
}
