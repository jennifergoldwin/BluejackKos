package com.jenjenfuture.bluejackkos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBTransaction {

    private DBHandler dbHandler;
    private Context context;
    private static DBTransaction instance;


    public static synchronized DBTransaction getInstance(Context context){
        if (instance==null){
            instance = new DBTransaction(context.getApplicationContext());
            return instance;
        }
        return instance;
    }
    public DBTransaction (Context context){
        this.context = context;
        dbHandler = DBHandler.getInstance(context);
    }

    public void insertBooking (BookingTransaction bookingTransaction){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHandler.FIELD_USER_ID,bookingTransaction.getUserId());
        cv.put(DBHandler.FIELD_BOOKING_ID,bookingTransaction.getBookingId());
        cv.put(DBHandler.FIELD_KOS_NAME,bookingTransaction.getKosName());
        cv.put(DBHandler.FIELD_KOS_DESC,bookingTransaction.getKosDesc());
        cv.put(DBHandler.FIELD_KOS_FACILITY,bookingTransaction.getKosFacility());
        cv.put(DBHandler.FIELD_KOS_PRICE,bookingTransaction.getKosPrice());
        cv.put(DBHandler.FIELD_KOS_LATITUDE,bookingTransaction.getKosLatitude());
        cv.put(DBHandler.FIELD_KOS_LONGITUDE,bookingTransaction.getKosLongtitude());
        cv.put(DBHandler.FIELD_KOS_BOOKING_DATE,bookingTransaction.getBookingDate());

        db.insert(DBHandler.TABLE_BOOKING,null,cv);
        db.close();
    }

    public void deleteBooking (String bookid){

        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.delete(DBHandler.TABLE_BOOKING,DBHandler.FIELD_BOOKING_ID + " = ? ",new String[]{bookid});
        db.close();

    }

    public  List<BookingTransaction> filterListBooking (String userId){
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        List<BookingTransaction> bookingTransactionList = new ArrayList<>();

//        String SELECT_QUERY = "SELECT * FROM " + DBHandler.TABLE_BOOKING;

        String selection = DBHandler.FIELD_USER_ID + " = ? ";
        String[] selectionArgs = {userId};

        Cursor cursor = db.query(DBHandler.TABLE_BOOKING,null,selection,
                selectionArgs,null,null,null);

        if (cursor.moveToFirst()){
            do{
                BookingTransaction bookingTransaction = new BookingTransaction();
                bookingTransaction.setUserId(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_ID)));
                bookingTransaction.setBookingId(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_BOOKING_ID)));
                bookingTransaction.setKosName(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_NAME)));
                bookingTransaction.setKosDesc(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_DESC)));
                bookingTransaction.setKosFacility(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_FACILITY)));
                bookingTransaction.setKosPrice(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_PRICE)));
                bookingTransaction.setKosLongtitude(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_LONGITUDE)));
                bookingTransaction.setKosLatitude(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_LATITUDE)));
                bookingTransaction.setBookingDate(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_BOOKING_DATE)));

                bookingTransactionList.add(bookingTransaction);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return bookingTransactionList;
    }
    public List<BookingTransaction> getAllBooking(){
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        List<BookingTransaction> bookingTransactionList = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM " + DBHandler.TABLE_BOOKING;



        Cursor cursor = db.rawQuery(SELECT_QUERY,null);

        if (cursor.moveToFirst()){
            do{
                BookingTransaction bookingTransaction = new BookingTransaction();
                bookingTransaction.setUserId(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_USER_ID)));
                bookingTransaction.setBookingId(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_BOOKING_ID)));
                bookingTransaction.setKosName(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_NAME)));
                bookingTransaction.setKosDesc(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_DESC)));
                bookingTransaction.setKosFacility(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_FACILITY)));
                bookingTransaction.setKosPrice(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_PRICE)));
                bookingTransaction.setKosLongtitude(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_LONGITUDE)));
                bookingTransaction.setKosLatitude(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_LATITUDE)));
                bookingTransaction.setBookingDate(cursor.getString(cursor.getColumnIndex(DBHandler.FIELD_KOS_BOOKING_DATE)));

                bookingTransactionList.add(bookingTransaction);
            }while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return bookingTransactionList;
    }

    public  boolean checkBook (String userId,String kosName, String bookDate){

        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String selection = DBHandler.FIELD_USER_ID + " = ?" + " AND " +
                DBHandler.FIELD_KOS_NAME + " = ? " + " AND " + DBHandler.FIELD_KOS_BOOKING_DATE + " = ? ";

        String[] selectionArgs = {userId,kosName,bookDate};

        Cursor cursor = db.query(DBHandler.TABLE_BOOKING,null,selection,
                selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
            return true;

        return false;
    }
}
