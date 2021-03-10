package com.foxhole.roomdatabaserxjavamvvmmutipletable.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "player_table")
public class Player {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private int party_id;

    private String name;

    public Player(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setParty_id(int party_id) {
        this.party_id = party_id;
    }

    public byte[] getImage() {
        return image;
    }

    public int getParty_id() {
        return party_id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}

