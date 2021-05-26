package com.example.mvvm;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class Todo_Adapter extends BaseAdapter {
    List<TodoModel> Todolist;
    public void setTodolist(List<TodoModel> list){this.Todolist = list;}

    Context context;

    static  class  ViewHoder
    {
        TextView ed_title;
        CheckBox cb_todo;
    }

    Todo_Adapter(List<TodoModel> list, Context context)
    {
        Todolist = list;
        this.context = context;

    }
    public int getCount() {
        return Todolist.size();
    }

    @Override
    public Object getItem(int position) {
        return Todolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder view;
        if (convertView == null)
        {
            view = new ViewHoder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            view.ed_title = (TextView) convertView.findViewById(R.id.Title);
            view.cb_todo = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHoder) convertView.getTag();
        }

        TodoModel t = this.Todolist.get(position);

        view.cb_todo.setChecked(t.isDone);

      /*  if ( t.isDone == false)
        {

            SpannableString Name = new SpannableString(t.name);
            Name.setSpan(new StrikethroughSpan(), 0, (t.name).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.ed_title.setText(Name);
        }
        else*/ view.ed_title.setText(t.name);

        convertView.setPadding(5, 5, 5, 5);

        return convertView;
    }
}
