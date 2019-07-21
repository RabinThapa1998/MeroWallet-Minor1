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
    public static final String CATEGORY_TABLE_NAME ="CATEGORY";
    public static final String SIGNUP_COL_ID ="ID"; //1
    public static final String SIGNUP_COL_FIRSTNAME ="FIRSTNAME"; //2
    public static final String SIGNUP_COL_LASTNAME ="LASTNAME"; //3
    public static final String SIGNUP_COL_USERNAME ="USERNAME"; //4
    public static final String SIGNUP_COL_PASSWORD ="PASSWORD"; //5
    public static final String SIGNUP_COL_CONFIRMPASSWORD ="CONFIRMPASSWORD"; //6
    public static final String SIGNUP_COL_PHONENUMBER ="PHONENUMBER"; //7
    public static final String SIGNUP_COL_ADDRESS ="ADDRESS"; //8
    public static final String SIGNUP_COL_EMAIL ="EMAIL"; //9
    public static final String SIGNUP_COL_BUDGET="BUDGET"; //10
    public static final String SIGNUP_COL_CASHEXPENSE="CASHEXPENSE";//11
    public static final String SIGNUP_COL_CARDEXPENSE="CARDEXPENSE";//12
    public static final String SIGNUP_COL_ONLINESTATUS = "ONLINESTATUS";//13-1
    public static final String SIGNUP_COL_SIGNUPDATE = "SIGNUPDATE";
    public static final String SIGNUP_COL_IMAGE = "IMAGE";



    //new table for expense
    public static final String EXPENSE_COL_ID = "EID";
    public static final String EXPENSE_COL_USERNAME = "USERNAME";
    public static final String EXPENSE_COL_AMOUNT = "AMOUNT";
    public static final String EXPENSE_COL_DATE = "DATE";
    public static final String EXPENSE_COL_CATEGORY = "CATEGORY";
    public static final String EXPENSE_COL_PAYMENT_TYPE = "PAYMENT_TYPE";
    public static final String EXPENSE_COL_IMAGE = "IMAGE";

    //new table for category
    public static final String CATEGORY_COL_ID="CID";
    public static final String CATEGORY_COL_USERNAME="USERNAME";
    public static final String CATEGORY_COL_CATEGORY="CATEGORY";








    //whenever this construtor is called the database is created.
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+ SIGNUP_TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRSTNAME TEXT NOT NULL,LASTNAME TEXT NOT NULL,USERNAME TEXT NOT NULL UNIQUE ,PASSWORD TEXT NOT NULL,CONFIRMPASSWORD TEXT NOT NULL,PHONENUMBER TEXT,ADDRESS TEXT,EMAIL TEXT NOT NULL,BUDGET DOUBLE DEFAULT 0,CASHEXPENSE DOUBLE DEFAULT 0,CARDEXPENSE DOUBLE DEFAULT 0,ONLINESTATUS INTEGER DEFAULT 0,SIGNUPDATE TEXT,IMAGE TEXT)");


        sqLiteDatabase.execSQL("create table "+ EXPENSE_TABLE_NAME +
                "(EID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,AMOUNT TEXT,DATE TEXT ,CATEGORY TEXT ,PAYMENT_TYPE TEXT ,IMAGE TEXT DEFAULT 0)");

        sqLiteDatabase.execSQL("create table "+ CATEGORY_TABLE_NAME +
                "(CID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,CATEGORY TEXT)");

        //Creating expense table
        /*String CREATE_EXPENSE_TABLE = "create table " + EXPENSE_TABLE_NAME +
                " ( " + EXPENSE_COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + EXPENSE_COL_USERNAME + "TEXT,"
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CATEGORY_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String  firstname ,String lastname ,String username, String password ,String confirmpassword,String phonenumber,String address,String email,String date,String img) {
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
        contentValues.put(SIGNUP_COL_SIGNUPDATE,date);
        contentValues.put(SIGNUP_COL_IMAGE,img);

        // contentValues.put(SIGNUP_COL_BUDGET,budget);

        long result = db.insert(SIGNUP_TABLE_NAME,null,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;

    }


    public boolean insertExpense(String username,String category,String amount,String date,String paymenttype,String img) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues2= new ContentValues();
        contentValues2.put(EXPENSE_COL_USERNAME,username);
        contentValues2.put(EXPENSE_COL_CATEGORY,category);
        contentValues2.put(EXPENSE_COL_AMOUNT,amount);
        contentValues2.put(EXPENSE_COL_DATE,date);
        contentValues2.put(EXPENSE_COL_PAYMENT_TYPE,paymenttype);
        contentValues2.put(EXPENSE_COL_IMAGE,img);



        long res = db1.insert(EXPENSE_TABLE_NAME,null,contentValues2);
        db1.close();
        if(res == -1)
            return false;
        else
            return true;

    }

    public boolean insertCategoryList(String username,String category){
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues3= new ContentValues();
        contentValues3.put(CATEGORY_COL_USERNAME,username);
        contentValues3.put(CATEGORY_COL_CATEGORY,category);

        long res = db1.insert(CATEGORY_TABLE_NAME,null,contentValues3);
        db1.close();
        if(res == -1)
            return false;
        else
            return true;


    }

    public boolean updateBudget(String username,double budget)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(SIGNUP_COL_BUDGET,budget);

        long value = db1.update(SIGNUP_TABLE_NAME,contentValues4,SIGNUP_COL_USERNAME + " = ?",new String[] {username });
        db1.close();

        if(value == -1)
            return false;

        else
            return true;
    }

    public void updateCashExpense(String username,double cashExpense)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(SIGNUP_COL_CASHEXPENSE,cashExpense);

        long value = db1.update(SIGNUP_TABLE_NAME,contentValues4,SIGNUP_COL_USERNAME + " = ?",new String[] {username });
        db1.close();

        /*if(value == -1)
            return false;

        else
            return true;*/
    }


    public void updateCardExpense(String username,double cardExpense)
    {
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(SIGNUP_COL_CARDEXPENSE,cardExpense);

        long value = db1.update(SIGNUP_TABLE_NAME,contentValues4,SIGNUP_COL_USERNAME + " = ?",new String[] {username });
        db1.close();

        /*if(value == -1)
            return false;

        else
            return true;*/
    }

    public int deleteCategory(String username, String category){
        SQLiteDatabase db = this.getWritableDatabase();


        return db.delete(CATEGORY_TABLE_NAME,CATEGORY_COL_USERNAME + " = ? AND " + CATEGORY_COL_CATEGORY + " = ?", new String[]{username,category});

    }

   /* public void updateCategoryFromExpenseTable(String username,String deleteCategory){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXPENSE_COL_USERNAME,"admin");
        cv.put(EXPENSE_COL_CATEGORY,"Null");

        long value = db.update(EXPENSE_TABLE_NAME,cv,EXPENSE_COL_USERNAME + " = ? AND " + EXPENSE_COL_CATEGORY + " = ?", new String[]{username,deleteCategory});
        db.close();

    }*/


    public Cursor getAllData()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from "+SIGNUP_TABLE_NAME,null);
        return res;

    }

    public Cursor getAllCategoryList()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("select * from "+CATEGORY_TABLE_NAME,null);
        return res;

    }

    public Cursor getExpenseTableData()
    {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res1 = database.rawQuery("select * from "+EXPENSE_TABLE_NAME,null);
        return res1;
    }
     public void updateOnlineStatus(String username, int status){

         SQLiteDatabase db1=this.getWritableDatabase();
         ContentValues contentValues00 = new ContentValues();
         contentValues00.put(SIGNUP_COL_ONLINESTATUS,status);


         long value = db1.update(SIGNUP_TABLE_NAME,contentValues00,SIGNUP_COL_USERNAME + " = ?",new String[] {username });
         db1.close();



     }



}
