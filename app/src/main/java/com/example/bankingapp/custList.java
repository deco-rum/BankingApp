package com.example.bankingapp;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class custList extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_list);

        customerDatabase db=new customerDatabase(this);

        ArrayList<String> listItems= new ArrayList<>();
        ListView listView= findViewById(R.id.myListView);
        ArrayAdapter adapter;

            Cursor cursor=db.viewData();

            if(cursor.getCount()==0){
                Toast.makeText(this,"No data to show",Toast.LENGTH_SHORT).show();
            }
            else{
                while(cursor.moveToNext()){
                    listItems.add(cursor.getString(1));
                }
                adapter=new ArrayAdapter<>(this,R.layout.row,listItems);
                listView.setAdapter(adapter);
            }

        Log.d("StepProcess","Customer List generated");
        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            String senName=getIntent().getStringExtra("sname");
            String senEmail=getIntent().getStringExtra("semail");
            int senBalance=getIntent().getIntExtra("sbalance",0);
            int amount=getIntent().getIntExtra("amount",0);

            String recName="";
            String recEmail="";
            int recBalance=0;
            Cursor curs=db.viewData();
            if(curs.getCount()==0){
                makeText(this,"No data to show",Toast.LENGTH_SHORT).show();
            }
            else{
                while(curs.moveToNext()){
                    if(curs.getInt(0)==i+1){
                        recName=curs.getString(1);

                        recEmail=curs.getString(2);

                        recBalance=curs.getInt(3);
                    }
                }
            }

            updateData(db,senEmail,senBalance-amount);
            updateData(db,recEmail,recBalance+amount);

            transactionDatabase data=new transactionDatabase(this);
            insertData(data,senName,recName,senEmail,recEmail,amount);

            Toast.makeText(this,"Amount Transferred",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(custList.this,MainActivity.class);
            startActivity(intent);
        });
    }

    private void insertData(transactionDatabase data, String senName, String recName, String senEmail, String recEmail, int amount) {
        data.insert(senName,recName,senEmail,recEmail,amount);
    }

    private void updateData(customerDatabase db, String senEmail, int i) {
        db.update(senEmail,i);
    }


    private void insert(customerDatabase db, int i, String name, String s, int i1) {
        db.insert(i,name,s,i1);
    }

}
