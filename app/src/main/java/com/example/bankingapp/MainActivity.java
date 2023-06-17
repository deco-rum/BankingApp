package com.example.bankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    customerDatabase db;
    ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new customerDatabase(this);

        listItems= new ArrayList<>();
        listView= findViewById(R.id.myListView);

        viewData();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent=new Intent(MainActivity.this,custProfile.class);
            intent.putExtra("value",i);
            startActivity(intent);
            Log.d("Data selected","Selected a Customer");
        });
    }

    private void insert(customerDatabase db, int i, String name, String s, int i1) {
        db.insert(i,name,s,i1);
    }


    private void viewData() {
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
    }
}