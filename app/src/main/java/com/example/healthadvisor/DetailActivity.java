package com.example.healthadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.title);
        TextView info = findViewById(R.id.info);
        TextView author = findViewById(R.id.author);
        TextView date = findViewById(R.id.date);

        Intent intent = getIntent();
        String titleStr = intent.getStringExtra("title");
        String infoStr = intent.getStringExtra("info");
        String authorStr = intent.getStringExtra("author");
        String dateStr = intent.getStringExtra("date");

        title.setText(titleStr);
        info.setText(infoStr);
        author.setText(authorStr);
        date.setText(dateStr);
    }
}