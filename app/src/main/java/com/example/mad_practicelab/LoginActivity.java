package com.example.mad_practicelab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad_practicelab.Database.DBHandler;

public class LoginActivity extends AppCompatActivity {

    public static final String INTENT_SEND_USERNAME = "com.example.courseapp.username";

    EditText editTextUserName;
    EditText editTextPassword;

    Button btnLogin;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


         editTextUserName = findViewById(R.id.editTextUserName);
         editTextPassword = findViewById(R.id.editTextPassword);
         btnLogin = findViewById(R.id.btnLogin);
         btnRegister = findViewById(R.id.btnRegister);


         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String Username = editTextUserName.getText().toString().trim();
                 String Password = editTextPassword.getText().toString().trim();

                 if(TextUtils.isEmpty(Username)||TextUtils.isEmpty(Password)){
                     Toast.makeText(getApplicationContext(),"Fill all the Fields",Toast.LENGTH_SHORT).show();
                 }else{
                     DBHandler dbHandler = new DBHandler( getApplicationContext());
                     String Type = dbHandler.Login(Username,Password);
                     if(Type == null){
                         Toast.makeText(getApplicationContext(),"Wrong Credentials",Toast.LENGTH_SHORT).show();
                     }else{
                         if(TextUtils.equals(Type,"Student")) {
                             Intent intent = new Intent(LoginActivity.this,Student.class);
                             intent.putExtra(INTENT_SEND_USERNAME,Username);
                             startActivity(intent);
                         }else if(TextUtils.equals(Type,"Teacher")){
                             Intent intent = new Intent(LoginActivity.this,Teacher.class);
                             intent.putExtra(INTENT_SEND_USERNAME,Username);
                             startActivity(intent);
                         }
                     }
                 }
             }
         });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);


            }
        });



    }
}
