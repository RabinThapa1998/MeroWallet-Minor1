package com.example.merowalletv11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="MW.db";
    public static final String SIGNUP_TABLE_NAME ="SIGN_UP";
    public static final String EXPENSE_TABLE_NAME ="EXPENSE";
    public static final String SIGNUP_COL_ID ="ID"; //1
    public static final String SIGNUP_COL_FIRSTNAME ="FIRSTNAME"; //2
    public static final String SIGNUP_COL_LASTNAME ="LASTNAME"; //3
    public static final String SIGNUP_COL_USERNAME ="USERNAME"; //4
    public static final String SIGNUP_COL_PASSWORD ="PASSWORD"; //5
    public static final String SIGNUP_COL_CONFIRMPASSWORD ="CONFIRMPASSWORD"; //6
    public static final String SIGNUP_COL_PHONENUMBER ="PHONENUMBER"; //7
    public static final String SIGNUP_COL_ADDRESS ="ADDRESS"; //8
    public static final String SIGNUP_COL_EMAIL ="EMAIL"; //9


    //new table for expense
    public static final String EXPENSE_COL_ID = "EID";
    public static final String EXPENSE_COL_MERCHANTNAME = "MERCHANT_NAME";
    public static final String EXPENSE_COL_AMOUNT = "AMOUNT";
    public static final String EXPENSE_COL_DATE = "DATE";
    public static final String EXPENSE_COL_CATEGORY = "CATEGORY";
    public static final String EXPENSE_COL_PAYMENT_TYPE = "PAYMENT_TYPE";
    public static final String EXPENSE_COL_RECIEPT = "RECIEPT";







    //whenever this construtor is called the database is created.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+ SIGNUP_TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT NOT NULL,LASTNAME TEXT NOT NULL,USERNAME TEXT NOT NULL UNIQUE ,PASSWORD TEXT NOT NULL,CONFIRMPASSWORD TEXT NOT NULL,PHONENUMBER TEXT,ADDRESS TEXT,EMAIL TEXT NOT NULL)");


        sqLiteDatabase.execSQL("create table "+ EXPENSE_TABLE_NAME +
                "(EID INTEGER PRIMARY KEY AUTOINCREMENT,MERCHANT_NAME TEXT,AMOUNT TEXT,DATE TEXT ,CATEGORY TEXT ,PAYMENT_TYPE TEXT ,RECIEPT TEXT)");

        //Creating expense table
        /*String CREATE_EXPENSE_TABLE = "create table " + EXPENSE_TABLE_NAME +
                " ( " + EXPENSE_COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXPENSE_COL_MERCHANTNAME + "TEXT,"
                + EXPENSE_COL_AMOUNT + "INTEGER,"
                + EXPENSE_COL_DATE + "DATE,"
                + EXPENSE_COL_CATEGORY + "TEXT,"
                + EXPENSE_COL_PAYMENT_TYPE + "TEXT,"
                + EXPENSE_COL_RECIEPT + "BLOB );";
                */

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SIGNUP_TABLE_NAME);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ EXPENSE_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String  firstname ,String lastname ,String username, String password ,String confirmpassword,String phonenumber,String address,String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(SIGNUP_COL_FIRSTNAME, firstname);
        contentValues.put(SIGNUP_COL_LASTNAME, lastname);
        contentValues.put(SIGNUP_COL_USERNAME, username);
        contentValues.put(SIGNUP_COL_PASSWORD,password);
        contentValues.put(SIGNUP_COL_CONFIRMPASSWORD,confirmpassword);
        contentValues.put(SIGNUP_COL_PHONENUMBER,phonenumber);
        contentValues.put(SIGNUP_COL_ADDRESS,address);
        contentValues.put(SIGNUP_COL_EMAIL,email);

        long result = db.insert(SIGNUP_TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }


    public boolean insertExpense(String merchantname,String category,String amount,String date,String paymenttype,String receipt) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues2= new ContentValues();
        contentValues2.put(EXPENSE_COL_MERCHANTNAME,merchantname);
        contentValues2.put(EXPENSE_COL_CATEGORY,category);
        contentValues2.put(EXPENSE_COL_AMOUNT,amount);
        contentValues2.put(EXPENSE_COL_DATE,date);
        contentValues2.put(EXPENSE_COL_PAYMENT_TYPE,paymenttype);
        contentValues2.put(EXPENSE_COL_RECIEPT,receipt);


        long res = db1.insert(EXPENSE_TABLE_NAME,null,contentValues2);
        if(res == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from "+SIGNUP_TABLE_NAME,null);
        return res;

    }


}
