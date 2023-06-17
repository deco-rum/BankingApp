package com.example.bankingapp;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bankingapp.R;

public class custProfile extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cust_profile);

        Log.d("StepProcess","Customer Profile opened up");
        int id=getIntent().getIntExtra("value",0);
        customerDatabase db;
        TextView name;
        TextView email;
        TextView balance;

        db=new customerDatabase(this);
        name= findViewById(R.id.viewName);
        email= findViewById(R.id.viewEmail);
        balance= findViewById(R.id.viewBalance);


        Cursor cursor=db.viewData();

        if(cursor.getCount()==0){
            makeText(this,"No data to show",Toast.LENGTH_SHORT).show();
        }
        else{
            while(cursor.moveToNext()){
                if(cursor.getInt(0)==id+1){
                    String custName=cursor.getString(1);
                    name.setText(custName);

                    String custEmail=cursor.getString(2);
                    email.setText(custEmail);

                    int custBalance=cursor.getInt(3);
                    balance.setText( String.valueOf(custBalance));
                }
            }
        }

        Button but= findViewById(R.id.transfer);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView value= findViewById(R.id.typeAmount);
                TextView name= findViewById(R.id.viewName);
                TextView email= findViewById(R.id.viewEmail);
                TextView balance= findViewById(R.id.viewBalance);

                if(Integer.parseInt(String.valueOf(value.getText()))==0){
                    Toast.makeText(custProfile.this, "No amount entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(custProfile.this,custList.class);
                    intent.putExtra("amount",Integer.parseInt(String.valueOf(value.getText())));
                    intent.putExtra("sname",name.getText());
                    intent.putExtra("semail",email.getText());
                    intent.putExtra("sbalance",Integer.parseInt(String.valueOf(balance.getText())));

                    startActivity(intent);
                    Log.d("StepProcess","Customer List is called");
                }
            }
        });
    }
}

