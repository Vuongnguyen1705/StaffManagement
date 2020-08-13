package com.example.staffmanagement.Model.LocalDb.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.staffmanagement.Model.LocalDb.Database.Ultils.ConstString;


@Entity(tableName = ConstString.STATE_REQUEST_TABLE_NAME)
public class StateRequest {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ConstString.STATE_REQUEST_COL_ID)
    private int Id;

    @ColumnInfo(name = ConstString.STATE_REQUEST_COL_NAME)
    private String Name;

    public StateRequest(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public StateRequest() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
