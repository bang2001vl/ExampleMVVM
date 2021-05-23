package com.example.mvvm;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class Todo_Viewmodel  extends ViewModel {

    public MutableLiveData<String>  Title = new MutableLiveData<>();
    public MutableLiveData<String> Content = new MutableLiveData<>();
    public MutableLiveData<Boolean> Is_checked = new MutableLiveData<Boolean>();


    private List<TodoModel> todos;
    private TodoModel curent_todo;
    private Context context;

    public Todo_Viewmodel()
    {
       // todos = load du lieu tu database
     }


    public Todo_Viewmodel(List<TodoModel> todos, Context context) {
        this.todos = todos;
        this.context = context;
    }

    public void OnClick_Add_item()
    {
        //insert
    }
    public void OnClick_Item(int Position)
    {
        curent_todo = todos.get(Position);
        //open detail_view
    }

}
