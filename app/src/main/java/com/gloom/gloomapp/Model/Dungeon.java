package com.gloom.gloomapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dungeon_table")
public class Dungeon {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;

    public Dungeon(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
