package com.foxhole.roomdatabaserxjavamvvmmutipletable.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.CharacterDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.PartyDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Database.Dao.PlayerDao;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Character;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Party;
import com.foxhole.roomdatabaserxjavamvvmmutipletable.Model.Player;

@Database(entities = {Party.class, Player.class, Character.class}, version = 3, exportSchema = false)
public abstract class PlayerDatabase extends RoomDatabase {

    private static PlayerDatabase instance;

    public abstract PartyDao partyDao();

    public abstract PlayerDao playerDao();

    public abstract CharacterDao characterDao();

    public static synchronized PlayerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PlayerDatabase.class, "player_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
