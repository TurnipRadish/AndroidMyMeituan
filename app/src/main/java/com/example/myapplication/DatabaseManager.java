package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DATABASE_NAME = "meituan1.db";
    private static DatabaseManager instance;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, 1);
        database = dbHelper.getWritableDatabase();
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    public boolean isUsernameExists(String account) {
        String[] projection = {"username"};
        String selection = "username = ?";
        String[] selectionArgs = {account};
        Cursor cursor = database.query("users", projection, selection, selectionArgs, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public long insertUser(String account, String password) {
        ContentValues values = new ContentValues();
        values.put("username", account);
        values.put("password", password);
        return database.insert("users", null, values);
    }

    public boolean checkUserPassword(String account, String password) {
        String[] projection = { "password" };
        String selection = "username = ?";
        String[] selectionArgs = { account };
        Cursor cursor = database.query("users", projection, selection, selectionArgs, null, null, null);
        boolean isCorrect = false;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int passwordIndex = cursor.getColumnIndex("password");
            if (passwordIndex >= 0) {
                String dbPassword = cursor.getString(passwordIndex);
                isCorrect = dbPassword.equals(password);
            }
        }
        cursor.close();
        return isCorrect;
    }

    public void insertDish(Dish dish) {
        dbHelper.insertDish(dish);
    }

    public void generateTestData(int n) {
        dbHelper.generateTestData(n);
    }

    public List<Dish> getDishesByCategory(String category) {
        return dbHelper.getDishesByCategory(category);
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}