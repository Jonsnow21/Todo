package com.neeraj.android.todo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.neeraj.android.todo.R;
import com.neeraj.android.todo.data.Todo;


import static com.neeraj.android.todo.utils.Constants.TODO;

public class ViewTodoFragment extends Fragment {

    private Todo mTodo;
    private TextView title, note, dueDate;
    private ScrollView scrollView;

    public ViewTodoFragment() {
        // Required empty public constructor
    }

    public static ViewTodoFragment newInstance(Todo todo) {
        ViewTodoFragment fragment = new ViewTodoFragment();
        Bundle args = new Bundle();
        args.putParcelable(TODO, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTodo = getArguments().getParcelable(TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_todo, container, false);

        scrollView = (ScrollView) view.findViewById(R.id.view_todo);
        title = (TextView) view.findViewById(R.id.todo_title_text);
        note = (TextView) view.findViewById(R.id.todo_content_text);
        dueDate = (TextView) view.findViewById(R.id.todo_date_text);

        setData();

        return view;
    }

    private void setData() {
        title.setText(mTodo.getTitle());
        note.setText(mTodo.getContent());
        dueDate.setText(mTodo.getDueDate());
        switch (mTodo.getPriority()) {
            case 0:
                scrollView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.priorityLow));
                break;
            case 1:
                scrollView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.priorityMedium));
                break;
            case 2:
                scrollView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.priorityHigh));
                break;
        }
    }

}
