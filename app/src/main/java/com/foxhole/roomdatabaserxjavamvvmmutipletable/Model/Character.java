package com.foxhole.roomdatabaserxjavamvvmmutipletable.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "character_table")
public class Character {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;
    private String clazz;
    private String player_id;

    public Character(String name, byte[] image, String clazz) {
        this.name = name;
        this.image = image;
        this.clazz = clazz;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public byte[] getImage() {
        return image;
    }


    public String getPlayer_id() {
        return player_id;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }
}
