package com.example.healthadvisor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin");

        ArrayList<String> username = new ArrayList<>();
        for(int i = 0 ; i < 15 ;i++){
            username.add("Dr. Abel");
        }

        AdminListAdapter adapter = new AdminListAdapter(this,username);
        ListView list = findViewById(R.id.admin_list);
        list.setAdapter(adapter);

    }
}