package com.example.mvvm;

public class TodoModel {
    public String Name;
    public String Content;
    public boolean IsDone;

    public TodoModel()
    {
        Name = "Default Title";
        Content = "Default Content";
        IsDone = false;
    }

    public TodoModel(String name, String content, boolean isDone) {
        Name = name;
        Content = content;
        IsDone = isDone;
    }
}
