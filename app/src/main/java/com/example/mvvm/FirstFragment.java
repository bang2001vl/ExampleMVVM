package com.example.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm.databinding.FragmentFirstBinding;

import java.util.List;
import java.util.zip.Inflater;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private List<Todo> todoList;
    ListView listtodo;
    Todo_Adapter todo_adapter;


    static FirstFragment New_FirstFragment (List<Todo> list)
    {
        FirstFragment  f = new FirstFragment();
        f.todoList = list;
        return f;
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        if (todoList != null )
        {
            listtodo = view.findViewById(R.id.Listview_item);
            todo_adapter = new Todo_Adapter(todoList, this.getContext());
      /*      listtodo.setAdapter(todo_adapter);*/
            listtodo.post(new Runnable() {
                public void run() {
                    listtodo.setAdapter(todo_adapter);
                }
            });
        }

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


/*
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}