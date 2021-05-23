package com.example.mvvm;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class Todo_Viewmodel  extends ViewModel {

    public MutableLiveData<String>  Title = new MutableLiveData<>();
    public MutableLiveData<String> Content = new MutableLiveData<>();
    public MutableLiveData<Boolean> Is_checked = new MutableLiveData<Boolean>();


    private List<TodoModel> todos;
    private TodoModel current_todo;
    private Context context;

    public Todo_Viewmodel()
    {
       // todos = load du lieu tu database
     }


    public Todo_Viewmodel(List<TodoModel> todos, Context context) {
        this.todos = todos;
        this.context = context;
        current_todo = todos.get(0);
    }


    public TodoModel getCurrent_todo() {
        return current_todo;
    }

    public void setCurrent_todo(TodoModel current_todo) {
        this.current_todo = current_todo;
    }

    public void OnClick_Add_item()
    {
        //insert
    }
    public void OnClick_Item(int Position)
    {
        current_todo = todos.get(Position);
        //open detail_view
    }
    public void onClick_Checked()
    {
        current_todo.isDone = !current_todo.isDone;
        if ( current_todo.isDone == false)
        {
            SpannableStringBuilder spanBuilder = new SpannableStringBuilder(current_todo.name);
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spanBuilder.setSpan( strikethroughSpan, 0, current_todo.name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            current_todo.name = spanBuilder.toString();
        }
    }


}
