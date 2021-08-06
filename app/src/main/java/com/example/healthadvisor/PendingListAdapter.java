package com.example.healthadvisor;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> userName;
    private final ArrayList<String> reason;
    private final ArrayList<String> date;
    private final ArrayList<String> appointmentID;

    public PendingListAdapter(@NonNull Activity context, ArrayList<String> userName , ArrayList<String>reason, ArrayList<String> date,ArrayList<String> appointmentID) {
        super(context, R.layout.pending_list_item,userName);
        this.context = context;
        this.userName = userName;
        this.reason = reason;
        this.date = date;
        this.appointmentID = appointmentID;
    }

    public View getView(int position , View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.pending_list_item,null,true);

        TextView userNameTextView = rowView.findViewById(R.id.name);
        TextView reasonTextView = rowView.findViewById(R.id.reason);
        TextView dateTextView = rowView.findViewById(R.id.date);
        Button acceptButton = rowView.findViewById(R.id.Accept_button);
        Button declineButton = rowView.findViewById(R.id.decline_button);
        acceptButton.setOnClickListener(V->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference appointmentsNode = database.getReference("appointments").child(appointmentID.get(position));
            appointmentsNode.child("status").setValue("accepted").addOnCompleteListener(task -> {
                Toast.makeText(getContext(), "request accepted", Toast.LENGTH_SHORT).show();
            });
        });

        declineButton.setOnClickListener(V->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference appointmentsNode = database.getReference("appointments").child(appointmentID.get(position));
            appointmentsNode.child("status").setValue("declined").addOnCompleteListener(task -> {
                Toast.makeText(getContext(), "request declined", Toast.LENGTH_SHORT).show();
            });
        });

        userNameTextView.setText(userName.get(position));
        reasonTextView.setText(reason.get(position));
        dateTextView.setText(date.get(position));

        return rowView;
    }
}
