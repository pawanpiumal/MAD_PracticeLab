package com.example.mad_practicelab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.mad_practicelab.Database.DBHandler;

public class Message extends AppCompatActivity {

    TextView textViewSubject;
    TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


         textViewSubject = findViewById(R.id.textViewSubject);
         textViewMessage = findViewById(R.id.textViewMessage);


        Intent intent =getIntent();
        if(intent != null) {
            String Subject = intent.getStringExtra(Student.SEND_INTENT_SUBJECT);

            if(TextUtils.equals(Subject,"GET LAST")) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());
                ContentValues values = dbHandler.getLastMessage();

                String Message = values.getAsString(DBHandler.GET_DATA_MESSAGE);
                String Subject2 = values.getAsString(DBHandler.GET_DATA_SUBJECT);
                textViewMessage.setText(Message);
                textViewSubject.setText(Subject2);
            }else {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                ContentValues values = dbHandler.getMessage(Subject);

                String Message = values.getAsString(DBHandler.GET_DATA_MESSAGE);

                textViewMessage.setText(Message);
                textViewSubject.setText(Subject);
            }
        }



    }
}
