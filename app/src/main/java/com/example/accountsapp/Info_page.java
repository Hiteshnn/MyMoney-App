package com.example.accountsapp;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.accountsapp.Adapters.TodoAdapter2;
import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Info_page extends AppCompatActivity {


    private DatabaseHandler db;
    private List<ToDoModel> taskList;
    private TodoAdapter2 tasksAdapter;
    private RecyclerView tasksRecyclerView;
    private Button back;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);
        Objects.requireNonNull(getSupportActionBar()).hide();





        back=(Button) findViewById(R.id.back_button);
        db = new DatabaseHandler(this);
        db.openDatabase();
        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        final String username = i.getStringExtra("username");
        String place=i.getStringExtra("Place");
        taskList = db.getAllDetails(name,username);

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView2);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TodoAdapter2(db, Info_page.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        tasksAdapter.setTasks(taskList);

        Collections.reverse(taskList);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Info_page.this,MainActivity.class);
                j.putExtra("username",username);
                startActivity(j);
                finish();

            }
        });






    }
}
