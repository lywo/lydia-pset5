package com.example.lydia.lydia_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Lydia on 22-5-2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "savedSearches";
    public static final String COLUMN_ITEM = "searchItem";
    private final Context context;

    // constructor
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ITEM + " TEXT"+ ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ITEM + " TEXT"+ ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }


    /*
    CRUD method
    Add one item.
    */
    public void addItem(String cityName) {
        if (searchItem(cityName).size() == 0) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM, cityName);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    /*
    CRUD method
    search db for 1 item
     */
    public ArrayList <String> searchItem(String item){
        String [] Columns = {COLUMN_ITEM};
        ArrayList <String> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(TABLE_NAME , Columns, null, null, null, null, null) ;

        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            String name = cursor.getString(0);
            if (name.equals(item)) {
                result.add(name);
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();

        return result;
    }

    /*
    CRUD method
    Delete one item from the list.
     */
    public void deleteItem(String item) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ITEM + " =  " + "\'" +item + "\'";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    /*
    CRUD method
    Loading complete database
     */
    public ArrayList<String> readDB() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery( query, null );
        ArrayList<String> ToDoItems = new ArrayList<String>();

        String searchItem = "";
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                searchItem = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
                ToDoItems.add(searchItem);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return ToDoItems;
    }

    /*
    Function to clean up whole database.
    for debug function
     */
    public void DeleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    /*
    Function to Delete Database
    for debug funtion
     */
    public void DeleteDatabase(){
        context.deleteDatabase(DATABASE_NAME);
    }
}
