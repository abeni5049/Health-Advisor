package com.example.healthadvisor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PendingListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> userName;
    private final ArrayList<String> reason;
    private final ArrayList<String> date;

    public PendingListAdapter(@NonNull Activity context, ArrayList<String> userName , ArrayList<String>reason, ArrayList<String> date) {
        super(context, R.layout.pending_list_item,userName);
        this.context = context;
        this.userName = userName;
        this.reason = reason;
        this.date = date;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.pending_list_item,null,true);

        TextView userNameTextView = rowView.findViewById(R.id.name);
        TextView reasonTextView = rowView.findViewById(R.id.reason);
        TextView dateTextView = rowView.findViewById(R.id.date);

        userNameTextView.setText(userName.get(position));
        reasonTextView.setText(reason.get(position));
        dateTextView.setText(date.get(position));

        return rowView;
    }
}
