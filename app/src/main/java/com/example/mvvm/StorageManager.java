package com.example.mvvm;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Update;

import java.util.List;

public class StorageManager {
    private Context mContext;
    public AppDatabase db;

    public StorageManager(Context context)
    {
        mContext = context;
        db = Room.databaseBuilder(mContext.getApplicationContext(), AppDatabase.class, "test_room_db").build();
    }

    public void recycle()
    {
        db.close();
        db = null;
        mContext = null;
    }
}
