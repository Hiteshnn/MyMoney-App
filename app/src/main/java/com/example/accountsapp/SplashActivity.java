package com.example.accountsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private List<ToDoModel> list=new ArrayList<>();
    private String username;
    private Boolean i=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        db = new DatabaseHandler(this);
        db.openDatabase();

        list=db.getLoginDetails();

        for(ToDoModel item:list){
            if(item.getLogged_in().equals("Yes")) {
                username = item.getUsername();
                i = true;
            }
            }
        if(i==false){
            final Intent i = new Intent(SplashActivity.this, Login_page.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(i);
                    finish();
                }
            }, 2000);
        }
        else{
            final Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.putExtra("username",username);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(i);
                    finish();
                }
            }, 2000);

        }



    }
}