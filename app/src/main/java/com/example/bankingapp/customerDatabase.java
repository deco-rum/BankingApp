package com.example.bankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class customerDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME="CUSTOMER_DATA";//name of our Database
    public static final String DB_TABLE="DATA";
    public static final int DB_VERSION=1;// version of our database


    private static final String ID="_id";
    private static final String NAME="NAME";
    private static final String EMAIL="EMAIL";
    private static final String BALANCE="BALANCE";

    private static final String CREATE_TABLE="CREATE TABLE "+DB_TABLE +"("+
            ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            NAME+" TEXT,"+
            EMAIL+" TEXT,"+
            BALANCE+" INTEGER)";

    customerDatabase(Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.d("StepProcess","Customer Database is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int i1) {
        db.execSQL("DROP TABLE IF EXISTS DATA");

        onCreate(db);
    }

    public Cursor viewData(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * from DATA";
        Log.d("chala","fetch hua");
        Cursor cursor=db.rawQuery(query,null);
        Log.d("StepProcess","Data found");
        return cursor;
    }

    public boolean insert( int i, String name, String s, int i1) {

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(ID,i);
        contentValues.put(NAME,name);
        contentValues.put(EMAIL,s);
        contentValues.put(BALANCE,i1);

        long result=db.insert("DATA",null,contentValues);
        Log.d("StepProcess","Customer Added");
        return result !=-1;
    }

    public boolean update(String email,int amount){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(BALANCE,amount);

        long result=db.update("DATA",contentValues,"EMAIL=?",new String[]{email});
        Log.d("StepProcess","Balance updated");
        return result !=-1;
    }
}
