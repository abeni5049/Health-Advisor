package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    // TODO : improve remove dark mode.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Log In");

        String[] usertype = new String[] {"Mother", "FP Worker","Physician","Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, usertype);
        AutoCompleteTextView usertypeEditText = findViewById(R.id.gender_dropdown);
        usertypeEditText.setAdapter(adapter);

        EditText usernameEditText = findViewById(R.id.usernameTextField);
        EditText passwordEditText = findViewById(R.id.passwordTextField);



        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String userType = usertypeEditText.getText().toString();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef1 = database.getReference("users");
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isCorrect = false;
                    String fName="";
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String uName = ds.child("username").getValue(String.class);
                        fName = ds.child("fullName").getValue(String.class);
                        String pass = ds.child("password").getValue(String.class);
                        String utype = ds.child("userType").getValue(String.class);
                        if(utype.equals(userType)) {
                            if (uName.equals(username) && pass.equals(password)) {
                                isCorrect = true;
                                break;
                            }
                        }
                    }

                    if(isCorrect){
                        Intent intent;
                        switch(userType) {
                            case "Mother":
                                intent = new Intent(LoginActivity.this, MotherActivity.class);
                                intent.putExtra("motherUsername",username);
                                startActivity(intent);
                                break;
                            case "FP Worker":
                                intent = new Intent(LoginActivity.this, FpActivity.class);
                                intent.putExtra("FpFullName",fName);
                                startActivity(intent);
                                break;
                            case "Physician":
                                intent = new Intent(LoginActivity.this,PhysicianActivity.class);
                                intent.putExtra("fullName",fName);
                                startActivity(intent);
                                break;
                            case "Admin":
                                intent = new Intent(LoginActivity.this,AdminActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "error occurred "+userType, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            
        });

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }
}