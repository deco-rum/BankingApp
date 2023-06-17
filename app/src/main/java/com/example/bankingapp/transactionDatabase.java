package com.example.bankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class transactionDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME="TRANSACTIONS";
    public static final String DB_TABLE="TRANSFER";
    public static final int DB_VERSION=1;
    private static final String ID="_id";
    private static final String SENDER_NAME="SNAME";
    private static final String SENDER_EMAIL="SEMAIL";
    private static final String RECEIVER_NAME="RNAME";
    private static final String RECEIVER_EMAIL="REMAIL";
    private static final String AMOUNT="AMOUNT";

    private static final String CREATE_TABLE="CREATE TABLE "+DB_TABLE +"("+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            SENDER_NAME+" TEXT,"+
            RECEIVER_NAME+" TEXT,"+
            SENDER_EMAIL+" TEXT,"+
            RECEIVER_EMAIL+" TEXT,"+
            AMOUNT+" INTEGER)";

    transactionDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("StepProcess","Transaction table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS TRANSFER");

        onCreate(db);
    }

    public boolean insert(String sname, String semail,String rname, String remail, int i1) {

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(SENDER_NAME,sname);
        contentValues.put(RECEIVER_NAME,rname);
        contentValues.put(SENDER_EMAIL,semail);
        contentValues.put(RECEIVER_EMAIL,remail);
        contentValues.put(AMOUNT,i1);

        long result=db.insert("TRANSFER",null,contentValues);
        Log.d("StepProcess","Transaction has been recorded");
        return result !=-1;
    }
}
