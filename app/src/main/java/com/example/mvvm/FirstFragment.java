package com.example.mvvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm.databinding.FragmentFirstBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.zip.Inflater;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FirstFragment extends Fragment {

    public FragmentFirstBinding binding;

    public FirstFragment() {

    }

    public ToDoViewModel getViewModel() {
        return binding.getBinding();
    }

    @Override
    public View onCreateView( @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.setBinding(new ToDoViewModel());
        Todo_Adapter adapter = new Todo_Adapter(binding.getBinding(), getContext());
        binding.ListviewItem.setAdapter(adapter);
        binding.ListviewItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                binding.getBinding().setSelectedTodo_Index(position);
                showContextMenu(view);
                return true;
            }
        });

        reloadData();
        return binding.getRoot();
    }

    private void showContextMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.item_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_delete) {
                    deleteSelectedTodo();
                } else if (item.getItemId() == R.id.menu_modify) {
                    editSelectedTodo();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void deleteSelectedTodo()
    {
        binding.getBinding().removeTodo(binding.getBinding().getSelectedTodoModel());
    }

    public void editSelectedTodo()
    {
        SecondFragment_EditMode secondFragment_editMode = new SecondFragment_EditMode();
        secondFragment_editMode.viewModel = binding.getBinding();
        secondFragment_editMode.showNow(getActivity().getSupportFragmentManager(), "editTodo");
    }

    public void reloadData() {
        binding.getBinding().getTodoAll();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((Todo_Adapter)binding.ListviewItem.getAdapter()).recycle();
        binding = null;
    }

}