package com.example.accountsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.accountsapp.Adapters.ToDoAdapter;
import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
    private SearchView searchView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;
    private String username,password;
    private Button logout;

    private List<ToDoModel> taskList;
    public List<ToDoModel> taskListTemp=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();
        Intent i = getIntent();
        username = i.getStringExtra("username");

        searchView= findViewById(R.id.searchView);
        searchView.clearFocus();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db,MainActivity.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllNames(username);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        fab = findViewById(R.id.fab);
        logout=(Button)findViewById(R.id.logout);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                taskListTemp.clear();
                for(ToDoModel item:taskList){
                    if(item.getName().toLowerCase().contains(newText.toLowerCase())){
                        taskListTemp.add(item);
                    }
                }
                tasksAdapter.setTasks(taskListTemp);
                return true;
            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*db.updateLogged_in("No", username);
                Intent i = new Intent(MainActivity.this, Login_page.class);
                startActivity(i);*/
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.updateLogged_in("No", username);
                                Intent i = new Intent(MainActivity.this, Login_page.class);
                                startActivity(i);
                                finish();
                                dialog.dismiss();
                            }
                        });


                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
                /*Intent i = new Intent(MainActivity.this,Info_page.class);
                startActivity(i);*/
            }
        });

    }
    public String getUsername(){
        return username;
    }




    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllNames(username);
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}

