package com.example.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.zip.Inflater;

public class Todo_Adapter extends BaseAdapter {
    List<Todo> Todolist;
    Context context;

    static  class  ViewHoder
    {
        EditText ed_title;
        EditText ed_content;
        CheckBox cb_todo;
    }

    Todo_Adapter(List<Todo> list, Context context)
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
            view.ed_content =(EditText) convertView.findViewById(R.id.Content);
            view.ed_title = (EditText) convertView.findViewById(R.id.Title);
            view.cb_todo = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHoder) convertView.getTag();
        }

        Todo t = this.Todolist.get(position);
        view.ed_title.setText(t.get_title());
        view.ed_content.setText(t.get_content());
        view.cb_todo.setChecked(t.is_isDone());

        return convertView;
    }
}
