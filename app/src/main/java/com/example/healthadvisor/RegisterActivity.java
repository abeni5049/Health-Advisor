package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    static TextInputEditText dateTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView loginText = findViewById(R.id.login_text);
        dateTextField = findViewById(R.id.dateTextField);

        EditText usernameEditText = findViewById(R.id.usernameTextField);
        EditText passwordEditText = findViewById(R.id.passwordTextField);
        EditText fullNameEditText = findViewById(R.id.fullNameTextField);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberTextField);


        String[] martialStatusArray = new String[] {"Single", "Married","Divorced" ,"Widowed"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, martialStatusArray);
        AutoCompleteTextView editTextFilledExposedDropdown3 = findViewById(R.id.martial_dropdown);
        editTextFilledExposedDropdown3.setAdapter(adapter3);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String gender = "Female";
            String dateOfBirth = Objects.requireNonNull(dateTextField.getText()).toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String martialStatus = editTextFilledExposedDropdown3.getText().toString().trim();
            String userType = "Mother";
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if(fullName.isEmpty() ||
                dateOfBirth.isEmpty() ||
                phoneNumber.isEmpty() ||
                martialStatus.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty()){
                Toast.makeText(this,"all fields are required",Toast.LENGTH_SHORT).show();
            }else {
                registerButton.setEnabled(false);
                // Write a user to the database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("users");
                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isTaken = false;
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String str = ds.child("username").getValue(String.class);
                            if(str!=null)
                            if (str.equals(username)) {
                                isTaken = true;
                                break;
                            }
                        }

                        if (isTaken) {
                            Toast.makeText(RegisterActivity.this, "this username is taken", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference myRef = database.getReference("users").push();
                            myRef.child("fullName").setValue(fullName);
                            myRef.child("gender").setValue(gender);
                            myRef.child("dateOfBirth").setValue(dateOfBirth);
                            myRef.child("phoneNumber").setValue(phoneNumber);
                            myRef.child("martialStatus").setValue(martialStatus);
                            myRef.child("userType").setValue(userType);
                            myRef.child("username").setValue(username);
                            myRef.child("password").setValue(password).addOnCompleteListener(task -> {
                                registerButton.setEnabled(true);
                                Toast.makeText(RegisterActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR)-30;
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @SuppressLint("SetTextI18n")
        public void onDateSet(DatePicker view, int year, int month, int day) {
            String monthName = new DateFormatSymbols().getMonths()[month];
            dateTextField.setText(day+"  "+monthName+"  "+year);
        }
    }
}


