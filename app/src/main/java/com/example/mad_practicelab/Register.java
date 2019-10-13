package com.example.mad_practicelab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mad_practicelab.Database.DBHandler;

import java.lang.reflect.Type;

public class Register extends AppCompatActivity {

    EditText editTextUserName;
    EditText editTextPassword;
    RadioButton radioButtonStudent;
    RadioButton radioButtonTeacher;
    Button btnRegister;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




         editTextUserName = findViewById(R.id.editTextUserName);
         editTextPassword = findViewById(R.id.editTextPassword);
         radioButtonStudent = findViewById(R.id.radioButtonStudent);
         radioButtonTeacher = findViewById(R.id.radioButtonTeacher);
         btnRegister = findViewById(R.id.buttonRegister);
         radioGroup = findViewById(R.id.RadioGroup);

         radioGroup.check(R.id.radioButtonStudent);



         btnRegister.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String UserName = editTextUserName.getText().toString().trim();
                 String Password = editTextPassword.getText().toString().trim();
                 boolean student = radioButtonStudent.isChecked();
                 boolean teacher = radioButtonTeacher.isChecked();

                 //don't need to check radio button since it is default's checked
                 if(TextUtils.isEmpty(UserName)||TextUtils.isEmpty(Password)){
                         Toast.makeText(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT).show();
                 }else {
                     DBHandler dbHandler = new DBHandler(getApplicationContext());


                     String Type = null;
                     if (teacher) {
                         Type = "Teacher";
                     } else if (student) {
                         Type = "Student";
                     } else {
                         Type = null;
                     }
                     if (Type != null) {
                         boolean status = dbHandler.Register(UserName, Password, Type);
                         if(status){
                             Intent intent = new Intent(Register.this, LoginActivity.class);
                             startActivity(intent);
                         }else{
                             Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                         }
                     }else{
                         Toast.makeText(getApplicationContext(), "Type is Empty", Toast.LENGTH_SHORT).show();
                     }

                 }


             }
         });


    }
}
