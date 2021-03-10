package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Character;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface CharacterDao {

    @Insert
    void insert(Character character);

    @Update
    void update(Character character);

    @Delete
    void delete(Character character);

    @Query("DELETE FROM character_table")
    void deleteAllCharacters();

    @Query("SELECT * FROM character_table WHERE player_id LIKE :player_id")
    Flowable<List<Character>> getAllCharactersByPlayer(int player_id);

    @Query("DELETE FROM character_table WHERE player_id==:player_id")
    void deleteAllCharactersUnderPlayer(int player_id);

}
