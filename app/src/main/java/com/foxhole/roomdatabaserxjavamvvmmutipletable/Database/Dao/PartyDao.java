package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface PartyDao {

    @Insert
    void insert(Party party);

    @Update
    void update(Party party);

    @Delete
    void delete(Party party);

    @Query("DELETE FROM party_table")
    void deleteAllParty();

    @Query("SELECT * FROM party_table")
    Flowable<List<Party>> getAllParties();
}
