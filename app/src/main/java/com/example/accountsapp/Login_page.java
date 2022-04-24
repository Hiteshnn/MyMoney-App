package com.example.accountsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Login_page extends AppCompatActivity {

    private EditText username,password;
    private Button login;
    private TextView tv,fp;
    private DatabaseHandler db;
    private List<ToDoModel> list=new ArrayList<>();
    private Boolean a=false,b=false,c=false;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        tv=(TextView)findViewById(R.id.ltv);
        username=(EditText)findViewById(R.id.un);
        password=(EditText)findViewById(R.id.p1);
        login=(Button)findViewById(R.id.login);
        fp=(TextView)findViewById(R.id.fp);

        db = new DatabaseHandler(this);
        db.openDatabase();

        list=db.getLoginDetails();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Type username and password",Toast.LENGTH_LONG).show();
                }
                else{
                    for(ToDoModel i:list){
                        if(i.getUsername().equals(username.getText().toString())) {
                            a = true;
                            if (i.getPassword().equals(password.getText().toString())) {
                                b = true;
                            }
                        }
                    }
                    if(a==false&&b==false){
                        Toast.makeText(getApplicationContext(),"Username does not exists",Toast.LENGTH_LONG).show();
                    }
                    else if(a==true&&b==false){
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_LONG).show();
                    }
                    else if(a==true&&b==true){
                        db.updateLogged_in("Yes",username.getText().toString());

                        Intent j=new Intent(Login_page.this,MainActivity.class);
                        j.putExtra("username",username.getText().toString());
                        //j.putExtra("password",password.getText().toString());
                        startActivity(j);
                        finish();
                        Toast.makeText(getApplicationContext(),"Logged in as "+username.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        //tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login_page.this,Register_page.class);
                startActivity(i);
            }
        });
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Type username",Toast.LENGTH_LONG).show();
                }
                else {
                    for (ToDoModel i : list) {
                        if (i.getUsername().equals(username.getText().toString())) {
                            c = true;
                        }
                    }
                    if (c == false) {
                        Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG).show();

                    } else {
                        Intent i = new Intent(Login_page.this, forgot_password.class);
                        i.putExtra("username", username.getText().toString());
                        startActivity(i);
                        finish();
                    }
                }
            }
        });



    }
}
