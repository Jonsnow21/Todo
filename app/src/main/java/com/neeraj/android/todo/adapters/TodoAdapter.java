package com.neeraj.android.todo.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neeraj.android.todo.R;
import com.neeraj.android.todo.data.Todo;

import java.util.List;

/**
 * Created by neeraj on 19/2/17.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context context;
    private List<Todo> todoList;
    private TodoListener todoListener;

    public TodoAdapter(Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
        this.todoListener = (TodoListener) context;
    }

    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodoViewHolder holder, int position) {
        holder.todoTitle.setText(todoList.get(position).getTitle());
        holder.todoContent.setText(todoList.get(position).getContent());
        holder.dueDate.setText(todoList.get(position).getDueDate());
        holder.dueDate.setText(todoList.get(position).getDueDate());
        switch (todoList.get(position).getPriority()) {
            case 0:
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityLow));
                break;
            case 1:
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityMedium));
                break;
            case 2:
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.priorityHigh));
                break;
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoListener.openTodo(todoList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView dueDate;
        TextView todoTitle;
        TextView todoContent;

        public TodoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.single_item_card);
            todoContent = (TextView) itemView.findViewById(R.id.todo_content);
            todoTitle = (TextView) itemView.findViewById(R.id.todo_title);
            dueDate = (TextView) itemView.findViewById(R.id.todo_due_date);
        }
    }

    public interface TodoItemTouchHelper {
        void onDismiss(int position);
    }

    public interface TodoListener {

        void openTodo(Todo todo, int position);

        void todoDeleted(List<Todo> todoList);
    }
}