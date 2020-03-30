package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<String> id;
    private ArrayList<String> name;
    private ArrayList<String> phone;

    ListAdapter(Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> phone)
    {
        this.context = context;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder holder;

        LayoutInflater layoutInflater;

        if(convertView == null)
        {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.items, null);
            holder = new Holder();
            holder.ID_TextView = convertView.findViewById(R.id.textViewID);
            holder.Name_TextView = convertView.findViewById(R.id.textViewNAME);
            holder.PhoneNumberTextView = convertView.findViewById(R.id.textViewPHONE_NUMBER);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        holder.ID_TextView.setText(id.get(position));
        holder.Name_TextView.setText(name.get(position));
        holder.PhoneNumberTextView.setText(phone.get(position));

        return convertView;
    }

    public static class Holder
    {
        TextView ID_TextView;
        TextView Name_TextView;
        TextView PhoneNumberTextView;
    }
}