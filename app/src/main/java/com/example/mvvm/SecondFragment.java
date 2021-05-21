package com.example.mvvm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mvvm.databinding.FragmentSecondBinding;

import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtTitle.getText().toString();
                String content = binding.edtContent.getText().toString();

                StorageManager storageManager = new StorageManager(getContext());
                storageManager.db.todoDao().insert(new TodoModel(name, content, false))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
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
                });
            }
        });

        StorageManager storageManager = new StorageManager(getContext());
        storageManager.db.todoDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<TodoModel>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onSuccess(@NotNull List<TodoModel> todoModels) {
                Log.d("MY_TAG", "SUCCESS: Read from database");
                for(TodoModel model: todoModels)
                {
                    Log.d("MY_TAG", todoModels.toString());
                }
                storageManager.recycle();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.e("MY_TAG", "ERROR: Cannot read from database");
                e.printStackTrace();
                storageManager.recycle();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}