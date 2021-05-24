package com.example.mvvm;

import android.content.Context;
import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Update;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StorageManager {
    private Context mContext;
    public AppDatabase db;

    public StorageManager(Context context)
    {
        mContext = context;
        db = Room.databaseBuilder(mContext.getApplicationContext(), AppDatabase.class, "test_room_db").build();
    }

    public static void createTodo(Context context, TodoModel todoModel)
    {
        StorageManager storageManager = new StorageManager(context);
        final CompletableObserver createObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("MY_TAG", "SUCCESS: Write to database");
                storageManager.recycle();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.e("MY_TAG", "ERROR: Cannot write to database");
                e.printStackTrace();
                storageManager.recycle();
            }
        };
        storageManager.db.todoDao().insert(todoModel)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(createObserver);
    }

    public static void deleteTodo(Context context, TodoModel todoModel)
    {
        StorageManager storageManager = new StorageManager(context);
        final CompletableObserver createObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("MY_TAG", "SUCCESS: Write to database");
                storageManager.recycle();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.e("MY_TAG", "ERROR: Cannot write to database");
                e.printStackTrace();
                storageManager.recycle();
            }
        };
        storageManager.db.todoDao().delete(todoModel)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(createObserver);
    }

    public void recycle()
    {
        db.close();
        db = null;
        mContext = null;
    }
}
