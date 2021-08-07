package com.example.healthadvisor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> title;
    ArrayList<String> info;
    ArrayList<String> author;
    ArrayList<String> date;
    ArrayList<String> postID;

    LayoutInflater inflater;
    public GridAdapter(Context applicationContext, ArrayList<String> title, ArrayList<String> info, ArrayList<String> author, ArrayList<String> date,ArrayList<String> postID) {
        this.context = applicationContext;
        this.title = title;
        this.info = info;
        this.author = author;
        this.date = date;
        this.postID = postID;
        inflater = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return title.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_gridview, null); // inflate the layout

        TextView titleTextView = view.findViewById(R.id.title);
        TextView infoTextView = view.findViewById(R.id.info);
        TextView authorTextView = view.findViewById(R.id.author);
        TextView dateTextView = view.findViewById(R.id.date);

        titleTextView.setText( title.get(i));
        infoTextView.setText( info.get(i));
        authorTextView.setText( author.get(i));
        dateTextView.setText( date.get(i));



        return view;
    }
}