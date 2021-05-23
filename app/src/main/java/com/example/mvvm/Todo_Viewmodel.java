package com.example.mvvm;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class Todo_Viewmodel  extends ViewModel {

    public MutableLiveData<String>  Title = new MutableLiveData<>();
    public MutableLiveData<String> Content = new MutableLiveData<>();
    public  MutableLiveData<Boolean> Is_checked = new MutableLiveData<Boolean>();

    private TodoModel todo;
    private Context context;


    public Todo_Viewmodel(TodoModel todo, Context context) {
        this.todo = todo;
        this.context = context;
    }

    public void OnClick_Add_item( int position)
    {
        //insert
    }
    public void OnClick_Item(int Position)
    {
        todo.content = Content.getValue();
        todo.name = Title.getValue();
        todo.isDone = Is_checked.getValue();

        //open detail_view
    }

}
