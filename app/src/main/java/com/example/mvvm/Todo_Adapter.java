package com.example.mvvm;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableList;

import com.example.mvvm.databinding.ItemTodoBinding;

import java.util.List;

public class Todo_Adapter extends BaseAdapter {
    ToDoViewModel viewModel;

    Context context;

    static  class ViewHolder
    {
        TextView ed_title;
        CheckBox cb_todo;
    }

    Todo_Adapter(ToDoViewModel viewModel, Context context)
    {
        this.viewModel = viewModel;
        this.context = context;

        viewModel.addOnPropertyChangedCallback(propertyChangedCallback);
    }
    public int getCount() {
        return viewModel.getTodoList().size();
    }

    @Override
    public Object getItem(int position) {
        return viewModel.getTodoList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        if (convertView == null)
        {
            view = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            view.ed_title = (TextView) convertView.findViewById(R.id.Title);
            view.cb_todo = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        TodoModel model = this.viewModel.getTodoList().get(position);

        convertView.setPadding(5, 5, 5, 5);

        view.cb_todo.setChecked(model.isDone);

        view.cb_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setSelectedTodo_Index(position);
                viewModel.setSelectedTodoModel_IsDone(isChecked);
            }
        });


        if (model.isDone)
        {
            SpannableString Name = new SpannableString(model.name);
            Name.setSpan(new StrikethroughSpan(), 0, (model.name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            view.ed_title.setText(Name);
        }
        else view.ed_title.setText(model.name);

        return convertView;
    }

    Observable.OnPropertyChangedCallback propertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if(propertyId == BR.todoList){
                notifyDataSetChanged();
            }
        }
    };

    public void recycle()
    {
        viewModel.removeOnPropertyChangedCallback(propertyChangedCallback);
        viewModel = null;
    }
}
