package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static  String username1;
    private  ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Log In");



        String[] usertype = new String[] {"Mother", "FP Worker","Physician","Admin"};
        adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, usertype);
        AutoCompleteTextView usertypeEditText = findViewById(R.id.gender_dropdown);
        usertypeEditText.setAdapter(adapter);

        EditText usernameEditText = findViewById(R.id.usernameTextField);
        EditText passwordEditText = findViewById(R.id.passwordTextField);



        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            if(!isNetworkAvailable(this)){
                Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            }else {
                loginButton.setEnabled(false);
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String userType = usertypeEditText.getText().toString().trim();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("users");
                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isCorrect = false;
                        String fName = "";
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String uName = ds.child("username").getValue(String.class);
                            fName = ds.child("fullName").getValue(String.class);
                            String pass = ds.child("password").getValue(String.class);
                            String uType = ds.child("userType").getValue(String.class);
                            if(uType != null && uName != null && pass != null)
                            if (uType.equals(userType)) {
                                if (uName.equals(username) && pass.equals(password)) {
                                    isCorrect = true;
                                    break;
                                }
                            }
                        }

                        if(isCorrect) {
                            username1 = username;
                            Intent intent;
                            switch (userType) {
                                case "Mother":
                                    intent = new Intent(LoginActivity.this, MotherActivity.class);
                                    intent.putExtra("motherUsername", username);
                                    startActivity(intent);
                                    break;
                                case "FP Worker":
                                    intent = new Intent(LoginActivity.this, FpActivity.class);
                                    intent.putExtra("FpFullName", fName);
                                    startActivity(intent);
                                    break;
                                case "Physician":
                                    intent = new Intent(LoginActivity.this, PhysicianActivity.class);
                                    intent.putExtra("fullName", fName);
                                    intent.putExtra("physicianUsername", username);
                                    startActivity(intent);
                                    break;
                                case "Admin":
                                    intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "error occurred " + userType, Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "incorrect username or password", Toast.LENGTH_SHORT).show();
                        }
                        loginButton.setEnabled(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this, "error occurred , please check your internet connection", Toast.LENGTH_SHORT).show();
                        loginButton.setEnabled(true);
                    }
                });
            }
            
        });

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onResume() {
        adapter.getFilter().filter(null);
        super.onResume();
    }
}