package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin");

        ArrayList<String> username = new ArrayList<>();

        AdminListAdapter adapter = new AdminListAdapter(this,username);
        ListView list = findViewById(R.id.admin_list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uname = ds.child("username").getValue(String.class);
                    username.add(uname);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        String numberOfPosts;
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            int numberOfUsers=0,numberOfMothers=0,numberOfPhysicians=0,numberOfFpWorkers=0,numberOfAdmins=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(), String.valueOf(snapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uType = ds.child("userType").getValue(String.class);
                    switch (uType){
                        case "Mother":
                            numberOfMothers++;
                            break;
                        case "Physician":
                            numberOfPhysicians++;
                            break;
                        case "FP Worker":
                            numberOfFpWorkers++;
                            break;
                        case "Admin":
                            numberOfAdmins++;
                            break;
                    }
                }
                TextView totalUsersText = findViewById(R.id.total_number_of_users);
                String numberOfUsersStr = String.valueOf(snapshot.getChildrenCount());
                String numberOfAdminsStr = String.valueOf(numberOfAdmins);
                String numberOfMothersStr = String.valueOf(numberOfMothers);
                String numberOfFpWorkersStr = String.valueOf(numberOfFpWorkers);
                String numberOfPhysiciansStr = String.valueOf(numberOfPhysicians);
                String metrics = "\n Total number Of Users: "+numberOfUsersStr+
                        "\nTotal Number Of Mothers: "+numberOfMothersStr
                        +"\nTotal Number Of FP Workers:  "+numberOfFpWorkersStr
                        +"\nTotal Number Of Physicians:  "+numberOfPhysiciansStr
                        + "\nTotal Number Of Admins:  "+numberOfAdminsStr;
                totalUsersText.setText(metrics);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref = database.getReference("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView textView = findViewById(R.id.total_number_of_posts);
                String numberOfPosts = String.valueOf(snapshot.getChildrenCount());
                textView.setText("Total number of posts "+numberOfPosts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref2 = database.getReference("appointments");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String numberOfAppointments = String.valueOf(snapshot.getChildrenCount());
                TextView textView = findViewById(R.id.total_number_of_appointment);
                textView.setText("Total Number Of Appointments: "+numberOfAppointments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        TabHost tabs = findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("users");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("report");
        tabs.addTab(spec);


    }
}