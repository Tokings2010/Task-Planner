package com.geekykelvin.taskplanner.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by Geeky Kelvin on 11/7/2018.
 * Email: Kelvinator4leo@gmail.com
 */

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";

    private static final int DATABASE_VERSION = 1;

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the Task table
        String SQL_CREATE_TASK_TABLE =  "CREATE TABLE " + TaskContract.AlarmTaskEntry.TABLE_NAME + " ("
                + TaskContract.AlarmTaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskContract.AlarmTaskEntry.KEY_TITLE + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_DATE + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_TIME + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_REPEAT + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_REPEAT_NO + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_REPEAT_TYPE + " TEXT, "
                + TaskContract.AlarmTaskEntry.KEY_ACTIVE + " TEXT " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_TASK_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
