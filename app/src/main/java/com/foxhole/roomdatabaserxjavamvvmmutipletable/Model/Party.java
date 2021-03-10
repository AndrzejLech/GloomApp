package com.foxhole.roomdatabaserxjavamvvmmutipletable.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "party_table")
public class Party {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    private String reputation;

    public Party(String name, byte[] image, String reputation) {
        this.name = name;
        this.image = image;
        this.reputation = reputation;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
            this.reputation = reputation;
    }
}
