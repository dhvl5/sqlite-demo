package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity
{
    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView listView;

    ArrayList<String> idList;
    ArrayList<String> nameList;
    ArrayList<String> phoneList;

    ArrayList<String> listViewClickItem = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data);

        listView = findViewById(R.id.displayData);

        idList = new ArrayList<>();
        nameList = new ArrayList<>();
        phoneList = new ArrayList<>();

        sqLiteHelper = new SQLiteHelper(this);

        if(listView == null) {
            listView.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowSingleRecord.class);
                intent.putExtra("ListViewClickItem", listViewClickItem.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        ShowSQLiteData();
        super.onResume();
    }

    private void ShowSQLiteData()
    {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

        idList.clear();
        nameList.clear();
        phoneList.clear();

        if(cursor.moveToFirst())
        {
            do
            {
                idList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
                listViewClickItem.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID)));
                nameList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name)));
                phoneList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber)));
            }
            while(cursor.moveToNext());
        }

        listAdapter = new com.example.sqlite.ListAdapter(this, idList, nameList, phoneList);

        listView.setAdapter(listAdapter);
        cursor.close();
    }
}