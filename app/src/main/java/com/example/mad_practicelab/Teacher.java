package com.example.mad_practicelab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad_practicelab.Database.DBHandler;

public class Teacher extends AppCompatActivity {

    EditText editTextSubject;
    EditText editTextMessage;
    Button btnSend;
    TextView textViewWelcome;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

         editTextSubject = findViewById(R.id.editTextSubject);
         editTextMessage = findViewById(R.id.editTextMessage);
         btnSend = findViewById(R.id.buttonSend);
         textViewWelcome  = findViewById(R.id.textViewWelcome);


         Intent intent = getIntent();
         Username = intent.getStringExtra(LoginActivity.INTENT_SEND_USERNAME);


         btnSend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String Subject = editTextSubject.getText().toString().trim();
                 String Message = editTextMessage.getText().toString().trim();

                 if(TextUtils.isEmpty(Subject)|| TextUtils.isEmpty(Message)){
                     Toast.makeText(getApplicationContext(), "Fill all the Fields", Toast.LENGTH_SHORT).show();
                 }else{
                     DBHandler dbHandler = new DBHandler(getApplicationContext());

                     boolean status = dbHandler.SaveMessage(Username,Subject,Message);
                     if(status){
                         Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                     }else{
                         Toast.makeText(getApplicationContext(), "Message not Sent", Toast.LENGTH_SHORT).show();
                     }

                 }

             }
         });


    }
}
