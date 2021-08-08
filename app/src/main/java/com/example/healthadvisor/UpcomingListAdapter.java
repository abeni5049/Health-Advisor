package com.example.healthadvisor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class UpcomingListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> motherUserName;
    private final ArrayList<String> date;

    public UpcomingListAdapter(@NonNull Activity context, ArrayList<String> motherUserName , ArrayList<String> date) {
        super(context, R.layout.upcoming_list_item,motherUserName);
        this.context = context;
        this.motherUserName = motherUserName;
        this.date = date;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View rowView = inflater.inflate(R.layout.upcoming_list_item,null,true);

        TextView motherTextView = rowView.findViewById(R.id.mother_name);
        TextView dateTextView = rowView.findViewById(R.id.date);

        motherTextView.setText(motherUserName.get(position));
        dateTextView.setText(date.get(position));

        return rowView;
    }
}