package com.example.healthadvisor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> userName;
    private final ArrayList<String> latestMessage;
    private final ArrayList<String> usertype;

    public MessageListAdapter(@NonNull Activity context, ArrayList<String> userName , ArrayList<String>latestMessage, ArrayList<String> usertype) {
        super(context, R.layout.message_list_item,userName);
        this.context = context;
        this.userName = userName;
        this.latestMessage = latestMessage;
        this.usertype = usertype;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.message_list_item,null,true);

        TextView userNameTextView = rowView.findViewById(R.id.user_name);
        TextView latestMessageTextView = rowView.findViewById(R.id.latest_message);
        TextView usertypeTextView = rowView.findViewById(R.id.usertype);

        userNameTextView.setText(userName.get(position));
        latestMessageTextView.setText(latestMessage.get(position));
        usertypeTextView.setText(usertype.get(position));

        return rowView;
    }
}
