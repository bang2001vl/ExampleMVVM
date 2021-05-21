package com.example.mvvm;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Database(entities = {TodoModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();

    public static final String TABLE_TODO = "TABLE_TODO";
    @Dao
    public interface TodoDao
    {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Completable insert(TodoModel... todoModels);

        @Update
        Completable update(TodoModel... todoModels);

        @Delete
        Completable delete(TodoModel... todoModels);

        @Query("SELECT * FROM " + TABLE_TODO)
        public Single<List<TodoModel>> getAll();
    }
}
