package com.jenjenfuture.bluejackkos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "DBUser";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USER = "User";
    public static final String FIELD_USER_ID = "UserId";
    public static final String FIELD_USER_NAME = "Username";
    public static final String FIELD_USER_PASS = "Password";
    public static final String FIELD_USER_PHONE = "PhoneNumber";
    public static final String FIELD_USER_GENDER = "Gender";
    public static final String FIELD_USER_DOF = "DateofBirth";


    public static final String TABLE_BOOKING = "BookingTransaction";
    public static final String FIELD_BOOKING_ID = "BookingId";
    public static final String FIELD_KOS_NAME = "KosName";
    public static final String FIELD_KOS_FACILITY = "KosFacility";
    public static final String FIELD_KOS_PRICE = "KosPrice";
    public static final String FIELD_KOS_DESC = "KosDesc";
    public static final String FIELD_KOS_LONGITUDE = "KosLongitude";
    public static final String FIELD_KOS_LATITUDE = "KosLatitude";
    public static final String FIELD_KOS_BOOKING_DATE = "BookingDate";


    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" +
            FIELD_USER_ID + " TEXT PRIMARY KEY , "+
            FIELD_USER_NAME + " TEXT, "+
            FIELD_USER_PASS + " TEXT, "+
            FIELD_USER_PHONE + " TEXT, "+
            FIELD_USER_GENDER + " TEXT,"+
            FIELD_USER_DOF + " TEXT);";

    private static final String CREATE_TABLE_BOOKING = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOKING + "(" +
            FIELD_BOOKING_ID + " TEXT PRIMARY KEY , "+
            FIELD_KOS_NAME + " TEXT, "+
            FIELD_KOS_FACILITY + " TEXT, "+
            FIELD_KOS_PRICE + " TEXT, "+
            FIELD_KOS_DESC +" TEXT, "+
            FIELD_KOS_LONGITUDE + " TEXT, "+
            FIELD_KOS_LATITUDE + " TEXT, "+
            FIELD_KOS_BOOKING_DATE + " TEXT, " +
            FIELD_USER_ID  + " TEXT, "+ "FOREIGN KEY (" + FIELD_USER_ID + ") REFERENCES " + TABLE_USER + "(" + FIELD_USER_ID + ")" +
            ");" ;

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER + ";";

    private static final String DROP_TABLE_BOOKING = "DROP TABLE IF EXISTS " + TABLE_BOOKING + ";";

    private static DBHandler instance;



    public static synchronized DBHandler getInstance(Context context){
        if (instance==null){
            instance = new DBHandler(context.getApplicationContext());
            return instance;
        }
        return instance;
    }

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BOOKING);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(DROP_TABLE_BOOKING);
        onCreate(db);
    }

}
