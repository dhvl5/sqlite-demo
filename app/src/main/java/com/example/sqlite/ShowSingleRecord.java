package com.example.sqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowSingleRecord extends AppCompatActivity
{
    String idHolder;
    TextView id, name, phone;
    SQLiteDatabase sqLiteDatabase;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    Button edit, delete;
    SQLiteDatabase sqLiteDatabaseObj;
    String queryHolder;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_single_record);

        id = findViewById(R.id.idTextView);
        name = findViewById(R.id.nameTextView);
        phone = findViewById(R.id.phoneTextView);
        edit = findViewById(R.id.editRecordButton);
        delete = findViewById(R.id.deleteRecordButton);

        sqLiteHelper = new SQLiteHelper(this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDatabase();
                queryHolder = "DELETE FROM "+SQLiteHelper.TABLE_NAME+" WHERE id = "+idHolder+"";
                sqLiteDatabase.execSQL(queryHolder);
                sqLiteDatabase.close();
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditSingleRecord.class);
                intent.putExtra("EditID", idHolder);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        ShowSingleRecordInTextView();
        super.onResume();
    }

    public void ShowSingleRecordInTextView()
    {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        idHolder = getIntent().getStringExtra("ListViewClickItem");

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " WHERE id = " + idHolder + "", null);

        if(cursor.moveToFirst())
        {
            do
            {
                id.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
                name.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));
                phone.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void OpenDatabase()
    {
        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
}