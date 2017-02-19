package com.neeraj.android.todo.data;

import io.realm.RealmObject;

/**
 * Created by neeraj on 19/2/17.
 */

public class Todo extends RealmObject {

    private String title;
    private String content;
    private String dueDate;
    private int priority;

    public Todo() {}

    public Todo(String title, String content, String date, int priority) {
        this.title = title;
        this.content = content;
        this.dueDate = date;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}