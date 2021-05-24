package com.example.mvvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm.databinding.FragmentFirstBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;


public class FirstFragment extends Fragment {

    public FragmentFirstBinding binding;
    private Todo_Viewmodel todo_viewmodel;
    private List<TodoModel> todoList;

    ListView listtodo;
    Todo_Adapter todo_adapter;


    public FirstFragment()
    {
        todo_viewmodel = new Todo_Viewmodel();
        todoList = todo_viewmodel.todos.getValue();

    }
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        Context context = this.getContext();

        todo_viewmodel.todos.observe(getViewLifecycleOwner(), todoModels -> {
            if (todoList != null )
            {
                listtodo = binding.ListviewItem;
                todo_adapter = new Todo_Adapter(todoList, context);

                listtodo.setAdapter(todo_adapter);
            }
            todo_adapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}