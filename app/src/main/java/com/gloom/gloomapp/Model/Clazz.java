package com.gloom.gloomapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clazz_table")
public class Clazz {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;

    public Clazz(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
