package com.neeraj.android.todo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.neeraj.android.todo.R;
import com.neeraj.android.todo.adapters.TodoAdapter;
import com.neeraj.android.todo.data.Todo;
import com.neeraj.android.todo.utils.DateUtils;

import java.util.Calendar;

import io.realm.Realm;

import static com.neeraj.android.todo.utils.Constants.TODO;

public class EditTodoFragment extends Fragment{

    private Spinner prioritySpinner;
    private EditText note, title;
    private String date;
    private DatePicker datePicker;
    Calendar calendar;
    private Todo mTodo;
    private Realm realm;
    private long time;

    public EditTodoFragment() {
        // Required empty public constructor
    }

    public static EditTodoFragment newInstance(Todo todo) {
        EditTodoFragment fragment = new EditTodoFragment();
        if (todo != null) {
            Bundle args = new Bundle();
            args.putParcelable(TODO, todo);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        if (getArguments() != null) {
            mTodo = getArguments().getParcelable(TODO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_todo, container, false);
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.priorities));

        title = (EditText) view.findViewById(R.id.todo_title_edittext);
        note = (EditText) view.findViewById(R.id.todo_content_edittext);
        datePicker = (DatePicker) view.findViewById(R.id.todo_date_picker);
        prioritySpinner = (Spinner) view.findViewById(R.id.todo_priority);

        prioritySpinner.setAdapter(priorityAdapter);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        time = Long.MAX_VALUE - calendar.getTimeInMillis();

        if (mTodo != null) {
            populateView();
        } else {
            date = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
            populateDatePicker(calendar);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    private void populateView() {
        title.setText(mTodo.getTitle(), TextView.BufferType.EDITABLE);
        note.setText(mTodo.getContent(), TextView.BufferType.EDITABLE);
        prioritySpinner.setSelection(mTodo.getPriority());
        populateDatePicker(DateUtils.getCalendar(date));
    }

    private void populateDatePicker(Calendar calendar) {
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "-" + (month + 1) + "-" + year;
                    }
                });
    }

    public boolean checkFields() {
        if (TextUtils.isEmpty(title.getText()) && TextUtils.isEmpty(note.getText())) {
            Toast.makeText(getContext(), R.string.todo_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (mTodo != null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        final Todo todo = realm.where(Todo.class).equalTo("title", mTodo.getTitle()).findFirst();
                        todo.deleteFromRealm();
                    }
                });
            }
            mTodo = new Todo(title.getText().toString(),
                    note.getText().toString(),
                    date,
                    prioritySpinner.getSelectedItemPosition(),
                    time);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(mTodo);
                }
            });
        }
        return true;
    }

}
