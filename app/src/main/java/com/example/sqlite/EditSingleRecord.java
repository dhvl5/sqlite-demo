package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditSingleRecord extends AppCompatActivity
{
    EditText name, phone;
    Button update;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String idHolder;
    String queryHolder;
    SQLiteDatabase sqLiteDatabaseObj;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_single_record);

        name = findViewById(R.id.nameUpdateEditText);
        phone = findViewById(R.id.phoneUpdateEditText);
        update = findViewById(R.id.updateRecordButton);

        sqLiteHelper = new SQLiteHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = name.getText().toString();
                String getPhone = phone.getText().toString();

                OpenDatabse();

                queryHolder = "UPDATE " + SQLiteHelper.TABLE_NAME + " SET "+SQLiteHelper.Table_Column_1_Name+" = '"+getName+"' , "+SQLiteHelper.Table_Column_2_PhoneNumber+" = '"+getPhone+"' WHERE id = " + idHolder + "";
                sqLiteDatabaseObj.execSQL(queryHolder);
                sqLiteDatabase.close();
                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditSingleRecord.this, DisplayData.class));
            }
        });
    }

    @Override
    protected void onResume()
    {
        ShowRecordInEditText();
        super.onResume();
    }

    public void ShowRecordInEditText()
    {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        idHolder = getIntent().getStringExtra("EditID");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + idHolder + "", null);

        if(cursor.moveToFirst())
        {
            do
            {
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));
                phone.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void OpenDatabse()
    {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
}