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

    public void setListData(List<TodoModel> list) {
        if (binding != null) {
            binding.getBinding().setTodoList(list);
        }
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.setBinding(new ToDoViewModel());

        Todo_Adapter adapter = new Todo_Adapter(binding.getBinding().getTodoList(), getContext());
        binding.ListviewItem.setAdapter(adapter);

        binding.ListviewItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                binding.getBinding().setSelectedTodo_Index(position);
                showContextMenu(view);
                return true;
            }
        });

        binding.getBinding().addOnPropertyChangedCallback(observer);

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
        StorageManager.deleteTodo(getContext(), binding.getBinding().getSelectedTodoModel());
        binding.getBinding().removeTodo(binding.getBinding().getSelectedTodoModel());
    }

    public void editSelectedTodo()
    {
        SecondFragment_EditMode secondFragment_editMode = new SecondFragment_EditMode();
        secondFragment_editMode.viewModel = binding.getBinding();
        secondFragment_editMode.showNow(getActivity().getSupportFragmentManager(), "editTodo");
    }

    final private Observable.OnPropertyChangedCallback observer = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.todoList) {
                Todo_Adapter adapter = ((Todo_Adapter) binding.ListviewItem.getAdapter());
                adapter.setTodolist(binding.getBinding().getTodoList());
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getTodoAll();
    }

    private void getTodoAll()
    {
        StorageManager storageManager = new StorageManager(getContext());
        SingleObserver<List<TodoModel>> selectAllObserver = new SingleObserver<List<TodoModel>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {/*Empty*/}

            @Override
            public void onSuccess(@NotNull List<TodoModel> todoModels) {
                Log.d("MY_TAG", "SUCCESS: Read from database");
                setListData(todoModels);
                storageManager.recycle();
            }

            @Override
            public void onError(@NotNull Throwable e) {
                Log.e("MY_TAG", "ERROR: Cannot read from database");
                e.printStackTrace();
                storageManager.recycle();
            }
        };
        storageManager.db.todoDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(selectAllObserver);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getBinding().removeOnPropertyChangedCallback(observer);
        binding = null;
    }

}