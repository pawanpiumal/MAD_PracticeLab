package com.example.mad_practicelab.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UScript;

import androidx.annotation.Nullable;

import com.example.mad_practicelab.Message;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABSE_NAME = "courseapp.db";

    public DBHandler(@Nullable Context context) {
        super(context, DATABSE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String CREATE_TABLE_USER = "CREATE TABLE "+CourseApp.UserTable.TABLE_NAME +" ( " +
                CourseApp.UserTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CourseApp.UserTable.COLUMN_NAME_NAME + " TEXT, "+
                CourseApp.UserTable.COLUMN_NAME_PASSWORD + " TEXT, "+
                CourseApp.UserTable.COLUMN_NAME_TYPE + " TEXT )";

        String CREATE_TABLE_MESSAGE = "CREATE TABLE "+CourseApp.MessageTable.TABLE_NAME +" ( " +
                CourseApp.MessageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CourseApp.MessageTable.COLUMN_NAME_USER + " TEXT, "+
                CourseApp.MessageTable.COLUMN_NAME_MESSAGE + " TEXT, "+
                CourseApp.MessageTable.COLUMN_NAME_SUBJECT + " TEXT )";


        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(CREATE_TABLE_MESSAGE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CourseApp.UserTable.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CourseApp.MessageTable.TABLE_NAME);
    }



    public boolean Register ( String Username, String Password, String Type){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CourseApp.UserTable.COLUMN_NAME_NAME, Username);
        values.put(CourseApp.UserTable.COLUMN_NAME_PASSWORD, Password);
        values.put(CourseApp.UserTable.COLUMN_NAME_TYPE, Type);


        long id =  db.insert(CourseApp.UserTable.TABLE_NAME,null,values);

        db.close();
        if(id < 0){
            return false;
        }
        else{
            return true;
        }


    }


    public boolean SaveMessage(String Username, String Subject, String Message){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CourseApp.MessageTable.COLUMN_NAME_USER, Username);
        values.put(CourseApp.MessageTable.COLUMN_NAME_MESSAGE, Message);
        values.put(CourseApp.MessageTable.COLUMN_NAME_SUBJECT, Subject);


        long id =  db.insert(CourseApp.MessageTable.TABLE_NAME,null,values);

        db.close();

        if(id < 0){
            return false;
        }
        else{
            return true;
        }

    }

    public List MessageList(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CourseApp.MessageTable.TABLE_NAME,
                new String[]{CourseApp.MessageTable.COLUMN_NAME_SUBJECT},
                null,
                null,
                null,
                null,
                CourseApp.MessageTable.COLUMN_NAME_SUBJECT+ " ASC");
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String Subject = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.MessageTable.COLUMN_NAME_SUBJECT));
            list.add(Subject);
        }

        cursor.close();
        db.close();
        return list;



    }

    public static final String GET_DATA_SUBJECT = "com.example.courseapp.subject";
    public static final String GET_DATA_MESSAGE = "com.example.courseapp.message";


    public ContentValues getMessage(String Subject){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CourseApp.MessageTable.TABLE_NAME,
                null,
                CourseApp.MessageTable.COLUMN_NAME_SUBJECT + " =?",
                new String[]{Subject},
                null,
                null,
                null);
        ContentValues contentValues = new ContentValues();

        while (cursor.moveToNext()){
            String SubjectDB = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.MessageTable.COLUMN_NAME_SUBJECT));
            String Message = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.MessageTable.COLUMN_NAME_MESSAGE));

            contentValues.put(GET_DATA_MESSAGE,Message);
            contentValues.put(GET_DATA_SUBJECT,SubjectDB);


        }

        cursor.close();
        db.close();
        return contentValues;

    }

    public String Login(String Username, String Password){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CourseApp.UserTable.TABLE_NAME,
                new String[]{CourseApp.UserTable.COLUMN_NAME_TYPE},
                CourseApp.UserTable.COLUMN_NAME_NAME + " =? AND "+ CourseApp.UserTable.COLUMN_NAME_PASSWORD + " = ? " ,
                new String[]{Username, Password},
                null,
                null,
                null);
        String Type = null;
        while (cursor.moveToNext()){
            Type = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.UserTable.COLUMN_NAME_TYPE));

        }

        cursor.close();
        db.close();
        return Type;

    }

    //this only returns the last message because the content value changes everytime

    public ContentValues getLastMessage(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CourseApp.MessageTable.TABLE_NAME,
                null,
                null,
               null,
                null,
                null,
                CourseApp.MessageTable._ID + " ASC");
        ContentValues contentValues = new ContentValues();

        while (cursor.moveToNext()){
            String SubjectDB = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.MessageTable.COLUMN_NAME_SUBJECT));
            String Message = cursor.getString(cursor.getColumnIndexOrThrow(CourseApp.MessageTable.COLUMN_NAME_MESSAGE));

            contentValues.put(GET_DATA_MESSAGE,Message);
            contentValues.put(GET_DATA_SUBJECT,SubjectDB);


        }

        cursor.close();
        db.close();
        return contentValues;

    }



}
