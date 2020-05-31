package com.jenjenfuture.bluejackkos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

public class DBUser {

    private DBHandler dbHandler;
    private Context context;

    private static String userId;
    private static DBUser instance;

    public static synchronized DBUser getInstance(Context context){
        if (instance==null){
            instance = new DBUser(context.getApplicationContext());
            return instance;
        }
        return instance;
    }

    public DBUser(Context context){
        this.context = context;
        dbHandler = DBHandler.getInstance(context);
    }

    public void insertUser (User user){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHandler.FIELD_USER_ID,user.getUserId());
        cv.put(DBHandler.FIELD_USER_NAME,user.getUserName());
        cv.put(DBHandler.FIELD_USER_PASS,user.getPassword());
        cv.put(DBHandler.FIELD_USER_PHONE,user.getPhoneNum());
        cv.put(DBHandler.FIELD_USER_GENDER,user.getGender());
        cv.put(DBHandler.FIELD_USER_DOF,user.getDof());

        db.insert(DBHandler.TABLE_USER,null,cv);
        db.close();
    }

    public List<User> getAllUser(){
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        List<User> userList = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM " + DBHandler.TABLE_USER;
        Cursor cursor = db.rawQuery(SELECT_QUERY,null);

        if (cursor.moveToFirst()){
            do{
                User user = new User();
                user.setUserId(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_ID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_PASS)));
                user.setGender(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_GENDER)));
                user.setDof(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_DOF)));
                user.setPhoneNum(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_PHONE)));

                userList.add(user);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return userList;
    }

    public  boolean checkUserValid (String username){
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String selection = DBHandler.FIELD_USER_NAME + " = ? ";


        String[] selectionArgs = {username};

        Cursor cursor = db.query(DBHandler.TABLE_USER,null,selection,
                selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }

    public static String getUserId(){
        return userId;
    }

    public  boolean checkUser (String username,String password){

        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String selection = DBHandler.FIELD_USER_NAME + " = ?" + " AND " +
                DBHandler.FIELD_USER_PASS + " = ? ";

        String column[] = {DBHandler.FIELD_USER_ID};
        String[] selectionArgs = {username,password};

        Cursor cursor = db.query(DBHandler.TABLE_USER,column,selection,
                selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();
        if (cursor.moveToFirst()){
            userId = cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_ID));
        }

        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }




}
