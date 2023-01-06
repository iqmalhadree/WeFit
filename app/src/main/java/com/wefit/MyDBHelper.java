package com.wefit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "user";
    private static final String COLUMN_AGE = "user_age";
    private static final String COLUMN_WEIGHT = "user_weight";
    private static final String COLUMN_HEIGHT = "user_height";
    private static final String COLUMN_GENDER = "user_gender";
    private static final String COLUMN_FITNESS = "user_fitness";
    private static final String COLUMN_NAME = "user_name";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME + " TEXT PRIMARY KEY, " +
                        COLUMN_AGE + " INTEGER, " +
                        COLUMN_WEIGHT + " INTEGER, " +
                        COLUMN_HEIGHT + " INTEGER, " +
                        COLUMN_GENDER + " TEXT, " +
                        COLUMN_FITNESS + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String name, int age, int height, int weight, String gender, String fitness){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_FITNESS, fitness);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public User extractUserDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + ";";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int age = cursor.getInt(1);
        int weight = cursor.getInt(2);
        int height = cursor.getInt(3);
        String gender = cursor.getString(4);
        String fitness = cursor.getString(5);

        User data = new User(weight, height, age, gender, fitness);
        return data;
    }


}
