package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase sqLiteDatabase;
    EditText nameEditText, phoneEditText;
    String nameHolder, phoneHolder, queryHolder;
    Button insertButton, displayButton;
    Boolean emptyEditTextHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        insertButton = findViewById(R.id.insertButton);
        displayButton = findViewById(R.id.displayDataButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                InsertData();
                EmptyEditText();
            }
        });

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DisplayData.class));
            }
        });
    }

    public void SQLiteDatabaseBuild()
    {
        sqLiteDatabase = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void SQLiteTableBuild()
    {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+SQLiteHelper.TABLE_NAME+"("+SQLiteHelper.Table_Column_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+SQLiteHelper.Table_Column_1_Name+" VARCHAR, "+SQLiteHelper.Table_Column_2_PhoneNumber+" VARCHAR);");
    }

    public void CheckEditTextStatus()
    {
        nameHolder = nameEditText.getText().toString();
        phoneHolder = phoneEditText.getText().toString();

        if(TextUtils.isEmpty(nameHolder) || TextUtils.isEmpty(phoneHolder))
            emptyEditTextHolder = false;
        else
            emptyEditTextHolder = true;
    }

    public void InsertData()
    {
        if(emptyEditTextHolder== true)
        {
            queryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,phone_number) VALUES('"+nameHolder+"', '"+phoneHolder+"');";
            sqLiteDatabase.execSQL(queryHolder);
            sqLiteDatabase.close();
            Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Please fill all the required fields", Toast.LENGTH_SHORT).show();
    }

    public void EmptyEditText()
    {
        nameEditText.getText().clear();
        phoneEditText.getText().clear();
    }
}