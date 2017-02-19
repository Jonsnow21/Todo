package com.neeraj.android.todo.activities;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.neeraj.android.todo.R;
import com.neeraj.android.todo.adapters.TodoAdapter;
import com.neeraj.android.todo.data.Todo;
import com.neeraj.android.todo.utils.ItemOffsetDecoration;

import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.LandingAnimator;

public class MainActivity extends AppCompatActivity implements TodoAdapter.TodoListener, TodoAdapter.TodoItemTouchHelper {

    private FloatingActionButton add;
    private TextView noTodo;
    private RecyclerView recyclerView;
    private Realm realm;
    private List<Todo> todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        add = (FloatingActionButton) findViewById(R.id.fab_add_todo);
        noTodo = (TextView) findViewById(R.id.no_todo);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        todoList = realm.where(Todo.class).findAll();
        checkIfNoTodo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }

    private void checkIfNoTodo() {
        if (todoList.size() == 0) {
            noTodo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noTodo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            setUpRecyclerView();
        }
    }

    private void setUpRecyclerView() {
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new LandingAnimator());
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        todoAdapter = new TodoAdapter(this, todoList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(todoAdapter);
    }

    @Override
    public void onDismiss(int position) {
        final Todo todo = realm.where(Todo.class).equalTo("title", todoList.get(position).getTitle()).findFirst();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                todo.deleteFromRealm();
            }
        });
        todoList.remove(position);
        todoAdapter.notifyItemRemoved(position);
        todoDeleted(todoList);
    }

    @Override
    public void updateTodo(Todo todo, @Nullable Integer index) {

    }

    @Override
    public void openTodo(Todo todo, int index) {

    }

    @Override
    public void todoDeleted(List<Todo> todoList) {
        checkIfNoTodo();
    }
}