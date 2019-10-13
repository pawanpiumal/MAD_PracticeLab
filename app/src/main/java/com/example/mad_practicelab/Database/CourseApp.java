package com.example.mad_practicelab.Database;

import android.provider.BaseColumns;

public class CourseApp
{
    private CourseApp() {
    }

    protected static class UserTable implements BaseColumns{

        protected static final String TABLE_NAME ="usertable";
        protected static final String COLUMN_NAME_NAME = "name";
        protected static final String COLUMN_NAME_PASSWORD = "password";
        protected static final String COLUMN_NAME_TYPE = "type";

    }


    protected static class MessageTable implements BaseColumns{

        protected static final String TABLE_NAME ="messagetable";
        protected static final String COLUMN_NAME_USER = "user";
        protected static final String COLUMN_NAME_SUBJECT = "subject";
        protected static final String COLUMN_NAME_MESSAGE = "message";

    }


}
