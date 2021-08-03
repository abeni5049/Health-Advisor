package com.example.healthadvisor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//TODO remove dark mode.
    private int userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Log IN");

        Spinner spinner = (Spinner) findViewById(R.id.usertype_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usertype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button loginButton = findViewById(R.id.login_button);
        userType = 0;
        loginButton.setOnClickListener(v -> {
            if (userType == 0) {
                Intent intent = new Intent(LoginActivity.this, MotherActivity.class);
                startActivity(intent);
//                case 1:
//                    Intent intent = new Intent(LoginActivity.this,FpWorkerActivity.class);
//                      startActivity(intent);
//                case 2:
//                    Intent intent = new Intent(LoginActivity.this,physician.class);
//                startActivity(intent);
//                case 3:
//                    Intent intent = new Intent(LoginActivity.this,Admin.class);
//                    startActivity(intent);
            }else {
                Toast.makeText(this, "error occurred", Toast.LENGTH_SHORT).show();
            }
        });

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userType = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}