package com.example.mvvm;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = AppDatabase.TABLE_TODO)
public class TodoModel {
    @NotNull
    @PrimaryKey
    public String name;

    public String content;
    public boolean isDone;

    public TodoModel()
    {
        name = "Default Title";
        content = "Default content";
        isDone = false;
    }

    @Ignore
    public TodoModel(@NotNull String name, String content, boolean isDone) {
        this.name = name;
        this.content = content;
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "Name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", isDone=" + isDone +
                '}';
    }

    public void copyTo(TodoModel other)
    {
        other.name = this.name;
        other.content = this.content;
        other.isDone = this.isDone;
    }

    public static TodoModel fromString(String str){
        int start = str.indexOf("Name='")+6;
        int end = str.indexOf('\'', start);
        String name = str.substring(start, end);

        start = str.indexOf(", content='")+11;
        end = str.indexOf('\'', start);
        String content = str.substring(start, end);

        start = str.indexOf(", isDone=")+9;
        end = str.indexOf('}', start);
        boolean isDone = Boolean.getBoolean(str.substring(start, end));

        return new TodoModel(name, content, isDone);
    }
}
