package com.neeraj.android.todo.data;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by neeraj on 19/2/17.
 */

public class Todo extends RealmObject implements Parcelable {

    private String title;
    private String content;
    private String dueDate;
    private int priority;
    private long createdTime;

    public Todo() {}

    public Todo(String title, String content, String date, int priority, long createdTime) {
        this.title = title;
        this.content = content;
        this.dueDate = date;
        this.priority = priority;
        this.createdTime = createdTime;
    }

    protected Todo(Parcel in) {
        title = in.readString();
        content = in.readString();
        dueDate = in.readString();
        priority = in.readInt();
        createdTime = in.readLong();
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

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

    public long getCreatedTime() {
        return createdTime;
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

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(dueDate);
        parcel.writeInt(priority);
        parcel.writeLong(createdTime);
    }
}