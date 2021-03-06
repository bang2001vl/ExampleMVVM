package com.example.mvvm;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableList;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ToDoViewModel extends BaseObservable {
    private List<TodoModel> todoList;
    private int selectedTodo_Index;

    @Bindable
    public List<TodoModel> getTodoList(){return todoList;}

    @Bindable
    public int getSelectedTodo_Index(){return selectedTodo_Index;}

    @Bindable({"todoList", "selectedTodo_Index"})
    public TodoModel getSelectedTodoModel()
    {
        return getTodoList().get(getSelectedTodo_Index());
    }

    @Bindable({"todoList", "selectedTodo_Index"})
    public String getSelectedTodoModel_Name()
    {
        return getSelectedTodoModel().name;
    }

    @Bindable({"todoList", "selectedTodo_Index"})
    public String getSelectedTodoModel_Content()
    {
        return getSelectedTodoModel().content;
    }

    @Bindable({"todoList", "selectedTodo_Index"})
    public boolean getSelectedTodoModel_IsDone()
    {
        return getSelectedTodoModel().isDone;
    }

    public void setTodoList(List<TodoModel> list)
    {
        if(todoList != list)
        {
            this.todoList = list;
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void setSelectedTodo_Index(int index)
    {
        if(selectedTodo_Index != index)
        {
            this.selectedTodo_Index = index;
            Log.i("Selected todo changed:", String.valueOf(index));
            notifyPropertyChanged(BR.selectedTodo_Index);
        }
    }

    public void addTodo(@NotNull TodoModel todoModel)
    {
        if(!this.todoList.contains(todoModel))
        {
            if (!todoModel.name.equals("")) StorageManager.createTodo(todoModel);
            this.todoList.add(todoModel);
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void removeTodo(@NotNull TodoModel todoModel)
    {
        if(this.todoList.contains(todoModel))
        {
            StorageManager.deleteTodo(todoModel);
            this.todoList.remove(todoModel);
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void replaceTodo(@NotNull TodoModel todoModel, int index)
    {
        if(index < todoList.size() && !todoModel.equals(todoList.get(index)))
        {
            todoModel.copyTo(todoList.get(index));
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void setSelectedTodoModel_Name(String name)
    {
        if(!this.getSelectedTodoModel().name.equals(name))
        {
            this.getSelectedTodoModel().name = name;
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void setSelectedTodoModel_Content(String content)
    {
        if(!this.getSelectedTodoModel().content.equals(content))
        {
            this.getSelectedTodoModel().content = content;
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void setSelectedTodoModel_IsDone(boolean isDone)
    {
        if(this.getSelectedTodoModel().isDone != isDone)
        {
            StorageManager.createTodo(getSelectedTodoModel());
            this.getSelectedTodoModel().isDone = isDone;
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void notifyTodoListChanged()
    {
        notifyPropertyChanged(BR.todoList);
    }


    public ToDoViewModel()
    {
        this.todoList = new ArrayList<>();
    }

    @Ignore
    public ToDoViewModel(@NotNull List<TodoModel> list)
    {
        this.todoList = list;
        this.selectedTodo_Index = 0;
    }

    public void getTodoAll()
    {
        StorageManager storageManager = new StorageManager();
        SingleObserver<List<TodoModel>> selectAllObserver = new SingleObserver<List<TodoModel>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {/*Empty*/}

            @Override
            public void onSuccess(@NotNull List<TodoModel> todoModels) {
                Log.d("MY_TAG", "SUCCESS: Read from database");
                setTodoList(todoModels);
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
}
