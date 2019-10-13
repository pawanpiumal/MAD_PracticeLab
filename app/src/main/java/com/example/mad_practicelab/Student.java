package com.example.mad_practicelab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mad_practicelab.Database.DBHandler;

import java.util.List;

public class Student extends AppCompatActivity {
    ListView listView;
    TextView textViewWelcome;
    String Username;

    public static final String CHANNEL_ID_NOTIFICATION = "com.example.courseapp.channelid";

    public static final int NOTIFICATION_ID = 12345;
    public static final String SEND_INTENT_SUBJECT = "com.example.courseapp.subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        createNotificationChannel();
        Intent intent2 = new Intent(this, Message.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent2.putExtra(SEND_INTENT_SUBJECT,"GET LAST");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_NOTIFICATION)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("You have a new Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());




        Intent intent =getIntent();
        Username = intent.getStringExtra(LoginActivity.INTENT_SEND_USERNAME);
        listView = findViewById(R.id.list);
        textViewWelcome = findViewById(R.id.textViewWelcome);

        textViewWelcome.setText("Welcome "+ Username);


        DBHandler dbHandler = new DBHandler(getApplicationContext());
        List list = dbHandler.MessageList();
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,list);
        listView.setBackgroundColor(Color.GREEN);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String Subject = (String) adapterView.getItemAtPosition(position);


                Intent intent1 = new Intent(getApplicationContext(),Message.class);
                intent1.putExtra(SEND_INTENT_SUBJECT, Subject);
                startActivity(intent1);


            }
        });

    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_NOTIFICATION, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
