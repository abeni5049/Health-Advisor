package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    ArrayList<String> username;
    ArrayList<String> userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin");
        
        username = new ArrayList<>();
        userType = new ArrayList<>();

        AdminListAdapter adapter = new AdminListAdapter(this,username,userType);
        ListView list = findViewById(R.id.admin_list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uName = ds.child("username").getValue(String.class);
                        String uType = ds.child("userType").getValue(String.class);
                        username.add(uName);
                        userType.add(uType);
                        adapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ArrayList<String> metricsList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();

        AdminListAdapter reportAdapter = new AdminListAdapter(this,metricsList,valueList);
        ListView reportList = findViewById(R.id.report_list);
        reportList.setAdapter(reportAdapter);

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
                String numberOfUsersStr = String.valueOf(snapshot.getChildrenCount());
                String numberOfAdminsStr = String.valueOf(numberOfAdmins);
                String numberOfMothersStr = String.valueOf(numberOfMothers);
                String numberOfFpWorkersStr = String.valueOf(numberOfFpWorkers);
                String numberOfPhysiciansStr = String.valueOf(numberOfPhysicians);

                metricsList.add("total Number Of users");
                valueList.add(numberOfUsersStr);
                metricsList.add("total number Of mothers");
                valueList.add(numberOfMothersStr);
                metricsList.add("total number Of FP workers");
                valueList.add(numberOfFpWorkersStr);
                metricsList.add("total number Of physicians");
                valueList.add(numberOfPhysiciansStr);
                metricsList.add("total number Of admins");
                valueList.add(numberOfAdminsStr);
                reportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference ref = database.getReference("posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String numberOfPosts = String.valueOf(snapshot.getChildrenCount());
                metricsList.add("total number of posts");
                valueList.add(numberOfPosts);
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
                metricsList.add("total number of appointments");
                valueList.add(numberOfAppointments);
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
        
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            DatabaseReference ref1 = database.getReference("users");
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        if( ds.child("username").getValue().toString().equals(username.get(position)) ){
                            ds.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return false;
        });




    }
}