package com.neeraj.android.todo.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.neeraj.android.todo.R;
import com.neeraj.android.todo.adapters.TodoAdapter;
import com.neeraj.android.todo.data.Todo;
import com.neeraj.android.todo.fragments.EditTodoFragment;
import com.neeraj.android.todo.fragments.ViewTodoFragment;

import static com.neeraj.android.todo.utils.Constants.EDITFRAG_TAG;
import static com.neeraj.android.todo.utils.Constants.PURPOSE;
import static com.neeraj.android.todo.utils.Constants.TODO;
import static com.neeraj.android.todo.utils.Constants.VIEWFRAG_TAG;
import static com.neeraj.android.todo.utils.Constants.VIEW_TODO;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private Fragment fragment;
    private Todo mTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = getIntent();
        String purpose = intent.getStringExtra(PURPOSE);
        if (purpose.equals(VIEW_TODO)) {
            mTodo = intent.getParcelableExtra(TODO);
            fab.setImageResource(R.drawable.ic_mode_edit_white_24dp);
            fragment = ViewTodoFragment.newInstance(mTodo);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame, fragment, VIEWFRAG_TAG)
                    .commit();
        } else {
            openEditFragment(null);
        }

        fab.setOnClickListener(fabClickListener);
    }

    private void openEditFragment(Todo todo) {
        fab.setImageResource(R.drawable.ic_done_white_24dp);
        fragment = EditTodoFragment.newInstance(todo);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame, fragment, EDITFRAG_TAG)
                .commit();
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (fragment instanceof ViewTodoFragment) {
                openEditFragment(mTodo);
            } else {
                EditTodoFragment editTodoFragment = (EditTodoFragment) getSupportFragmentManager().findFragmentByTag(EDITFRAG_TAG);
                if (editTodoFragment.checkFields()) {
                    finish();
                }
            }
        }
    };
}
