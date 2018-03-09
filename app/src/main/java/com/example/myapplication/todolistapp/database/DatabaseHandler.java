package com.example.myapplication.todolistapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.todolistapp.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yara on 05-Mar-18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "ToDoList";

    // Contacts table name
    private static final String TABLE_TASKS = "tasks";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TASKS = "task";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME= "time";
    private static final String KEY_STATUS = "status";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASKS + " TEXT, "
                + KEY_DATE + " TEXT, "
                + KEY_TIME + " TEXT, "
                + KEY_STATUS + " TEXT " + ")" ;
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Adding new Task
    public void addTask(Task tasks) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASKS, tasks.getTask());
        values.put(KEY_DATE, tasks.getDate());
        values.put(KEY_TIME, tasks.getTime());
        values.put(KEY_STATUS, tasks.getStatus());
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Getting All Tasks
    public List<Task> getAllTasks() {
        List<Task> tasksList = new ArrayList<Task>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Task tasks = new Task();
                tasks.setId(Integer.parseInt(cursor.getString(0)));
                tasks.setTask(cursor.getString(1));
                tasks.setDate(cursor.getString(2));
                tasks.setTime(cursor.getString(3));
                tasks.setStatus(cursor.getString(4));
                tasksList.add(tasks);
            } while (cursor.moveToNext());
        }
        return tasksList;
    }

    // Deleting single Task
    public void deleteTask(Task tasks) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",
                new String[] { String.valueOf(tasks.getId()) });
        db.close();
    }

    public void doneTask(Task tasks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS,"Done");
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tasks.getId()) });
    }

    public void cancleTask(Task tasks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS,"Canceled");
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tasks.getId()) });
    }
    //Update task text
    public void updateTask(Task tasks,String taskname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKS,taskname);
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tasks.getId()) });
    }


    // search by task name
    public List<Task> search(String name) {
        List<Task> tasksList = new ArrayList<Task>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, new String[]{KEY_ID,
                        KEY_TASKS, KEY_DATE, KEY_TIME, KEY_STATUS}, KEY_TASKS + "=?",
                new String[]{name}, null, null, null, null);
        cursor.moveToFirst();
        if (cursor != null & cursor.getCount() > 0) {
            do {
                Task tasks = new Task();
                tasks.setId(Integer.parseInt(cursor.getString(0)));
                tasks.setTask(cursor.getString(1));
                tasks.setDate(cursor.getString(2));
                tasks.setTime(cursor.getString(3));
                tasks.setStatus(cursor.getString(4));
                tasksList.add(tasks);
            } while (cursor.moveToNext());

        }
        return tasksList;
    }


}
