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
    private final ArrayList<String> userType;

    public AdminListAdapter(@NonNull Activity context, ArrayList<String> username,ArrayList<String> userType) {
        super(context, R.layout.admin_list_item,username);
        this.context = context;
        this.username = username;
        this.userType = userType;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.admin_list_item,null,true);

        TextView usersTextView = rowView.findViewById(R.id.username);
        TextView userTypeTextView = rowView.findViewById(R.id.user_type);

        usersTextView.setText(username.get(position));
        userTypeTextView.setText((userType.get(position)));

        return rowView;
    }
}
