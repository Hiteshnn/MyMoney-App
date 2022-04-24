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

import com.example.accountsapp.Utils.DatabaseHandler;

import java.util.Objects;

public class forgot_password extends AppCompatActivity {
    private DatabaseHandler db;
    private TextView username;
    private EditText password,confirmpassword;
    private Button login_button;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Objects.requireNonNull(getSupportActionBar()).hide();
        username=(TextView)findViewById(R.id.un);
        password=(EditText)findViewById(R.id.p1);
        confirmpassword=(EditText)findViewById(R.id.p2);
        login_button=(Button)findViewById(R.id.login);
        Intent i=getIntent();
        String username1=i.getStringExtra("username");
        username.setText(username1);
        db = new DatabaseHandler(this);
        db.openDatabase();
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().isEmpty()||confirmpassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Type password and confirm password",Toast.LENGTH_LONG).show();
                }
                else{
                    if(password.getText().toString().equals(confirmpassword.getText().toString())){
                       db.updateLogged_in("Yes",username.getText().toString());
                        db.updatePassword(password.getText().toString(),username.getText().toString());

                        Intent j=new Intent(forgot_password.this,MainActivity.class);
                        j.putExtra("username",username.getText().toString());
                        //j.putExtra("password",password.getText().toString());
                        startActivity(j);
                        finish();

                        Toast.makeText(getApplicationContext(),"Logged in as "+username.getText().toString(),Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Passwords are not matching",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}