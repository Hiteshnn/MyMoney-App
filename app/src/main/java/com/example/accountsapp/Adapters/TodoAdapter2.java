package com.example.accountsapp.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountsapp.Info_page;
import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.R;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.List;

public class TodoAdapter2 extends RecyclerView.Adapter<TodoAdapter2.ViewHolder> {

    private List<ToDoModel> todoList;
    private DatabaseHandler db;
    private Info_page activity;

    public TodoAdapter2(DatabaseHandler db, Info_page activity) {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getDate()+"\n"+"Amount  : "+item.getNet_amount()+"\n"+"Status     : "+item.getStatus()+"\nNotes      : "+item.getNote());

    }



    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.textView_row);
        }
    }
}
