package com.example.healthadvisor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> doctorUserName;
    private final ArrayList<String> date;
    private final ArrayList<String> status;

    public ListViewAdapter(@NonNull Activity context, ArrayList<String> doctorUserName , ArrayList<String> date, ArrayList<String> status) {
        super(context, R.layout.list_item,doctorUserName);
        this.context = context;
        this.doctorUserName = doctorUserName;
        this.date = date;
        this.status = status;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item,null,true);

        TextView doctorTextView = rowView.findViewById(R.id.doctor);
        TextView dateTextView = rowView.findViewById(R.id.date);
        TextView statusTextView = rowView.findViewById(R.id.status);

        doctorTextView.setText(doctorUserName.get(position));
        dateTextView.setText(date.get(position));
        statusTextView.setText(status.get(position));

        return rowView;
    }
}
