package com.wefit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalorieDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CalorieInfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "calorie";
    private static final String COLUMN_ID = "calorie_id";
    private static final String COLUMN_FOOD_NAME = "calorie_food_name";
    private static final String COLUMN_ENTRY_DATE = "calorie_entry_date";
    private static final String COLUMN_ENTRY_DAY = "calorie_entry_day";
    private static final String COLUMN_AMOUNT = "calorie_amount";


    public CalorieDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_FOOD_NAME + " TEXT, " +
                        COLUMN_ENTRY_DATE + " DATE, " +
                        COLUMN_ENTRY_DAY + " TEXT, " +
                        COLUMN_AMOUNT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addCal(String name, int amount, String date, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FOOD_NAME, name);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_ENTRY_DATE, date);
        cv.put(COLUMN_ENTRY_DAY, day);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDummy(String name, int amount, String date, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                COLUMN_FOOD_NAME + ", " +
                COLUMN_AMOUNT + ", " +
                COLUMN_ENTRY_DATE + ", " +
                COLUMN_ENTRY_DAY + ") " +
                "VALUES (" +
                name + ", " +
                amount + ", " +
                date + ", " +
                day + "); ";
        db.execSQL(query);
    }


    public List<FoodModel> sumCal(String dateQuery){
        SQLiteDatabase db = this.getReadableDatabase();
        List<FoodModel> amountList = new ArrayList<FoodModel>();

        String query = "SELECT " + COLUMN_ENTRY_DATE + ", " + COLUMN_ENTRY_DAY + ", SUM(" + COLUMN_AMOUNT + ") " +
                        "FROM " + TABLE_NAME +
                        " GROUP BY " + COLUMN_ENTRY_DATE +
                        " HAVING " + COLUMN_ENTRY_DATE + " =\"" + dateQuery + "\";";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String day = cursor.getString(1);
                int value = cursor.getInt(2);
                FoodModel fm = new FoodModel(day, value);
                amountList.add(fm);

            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();
        return amountList;
    }

    public Boolean noTable(){
        int value;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        value = cursor.getInt(0);
        cursor.close();
        if(value==0){
            return true;
        }
        else{
            return false;
        }
    }

}
