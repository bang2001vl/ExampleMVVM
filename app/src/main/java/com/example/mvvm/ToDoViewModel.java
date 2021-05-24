package com.example.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Ignore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
            notifyPropertyChanged(BR.selectedTodo_Index);
        }
    }

    public void addTodo(@NotNull TodoModel todoModel)
    {
        if(!this.todoList.contains(todoModel))
        {
            this.todoList.add(todoModel);
            notifyPropertyChanged(BR.todoList);
        }
    }

    public void removeTodo(@NotNull TodoModel todoModel)
    {
        if(this.todoList.contains(todoModel))
        {
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
}
