package com.example.healthadvisor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class AdminListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> username;

    public AdminListAdapter(@NonNull Activity context, ArrayList<String> username) {
        super(context, R.layout.admin_list_item,username);
        this.context = context;
        this.username = username;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.admin_list_item,null,true);

        TextView doctorTextView = rowView.findViewById(R.id.username);

        doctorTextView.setText(username.get(position));

        return rowView;
    }
}
