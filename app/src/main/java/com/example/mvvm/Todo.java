package com.example.mvvm;

public class Todo {
    private String _title;
    private String _content;
    private boolean _isDone;

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public boolean is_isDone() {
        return _isDone;
    }

    public void set_isDone(boolean _isDone) {
        this._isDone = _isDone;
    }

    public Todo(String Title, String Content, Boolean  Isdone  )
    {
        this._content = Content;
        this._isDone = Isdone;
        this._title = Title;
    }


}
