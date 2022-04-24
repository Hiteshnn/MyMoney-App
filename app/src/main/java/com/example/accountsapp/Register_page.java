package com.example.accountsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountsapp.Model.ToDoModel;
import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Register_page extends AppCompatActivity {
    private DatabaseHandler db;
    private TextView tv;
    private EditText username,password,confirmpassword;
    private Button register_button;
    private Boolean a=false;
    List<String> usernameslist = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        tv=(TextView)findViewById(R.id.rtv);
        username=(EditText)findViewById(R.id.un);
        password=(EditText)findViewById(R.id.p1);
        confirmpassword=(EditText)findViewById(R.id.p2);
        register_button=(Button)findViewById(R.id.login);
        //tv.setMovementMethod(LinkMovementMethod.getInstance());
        db = new DatabaseHandler(this);
        db.openDatabase();

        usernameslist=db.getAllUserNames();
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Register_page.this,Login_page.class);
                startActivity(i);
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Type username",Toast.LENGTH_LONG).show();
                }
                else {
                    for(String i:usernameslist){
                        if(i.equals(username.getText().toString())){
                            a=true;
                        }
                    }
                    if(a==true){
                        Toast.makeText(getApplicationContext(),"Username already exists!!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(password.getText().toString().isEmpty()||confirmpassword.getText().toString().isEmpty()){
                            Toast.makeText(getApplicationContext(),"Type password and confirm password",Toast.LENGTH_LONG).show();
                        }
                        else {
                            if(password.getText().toString().equals(confirmpassword.getText().toString())){
                                ToDoModel item=new ToDoModel();
                                item.setUsername(username.getText().toString());
                                item.setPassword(password.getText().toString());
                                item.setLogged_in("Yes");
                                db.insertEntry3(item);
                                Intent i=new Intent(Register_page.this,MainActivity.class);
                                i.putExtra("username",username.getText().toString());
                                //i.putExtra("password",password.getText().toString());
                                startActivity(i);
                                finish();
                                db.updateLogged_in("Yes",username.getText().toString());
                                Toast.makeText(getApplicationContext(),"Logged in as "+username.getText().toString(),Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Passwords are not matching!!",Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                }
            }
        });

        }

    }









