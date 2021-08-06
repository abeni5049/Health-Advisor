package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static java.security.AccessController.getContext;

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

        String[] Genders = new String[] {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Genders);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.gender_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        String[] usertype = new String[] {"Mother", "Physician" ,"FP Worker","Admin"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, usertype);
        AutoCompleteTextView editTextFilledExposedDropdown2 = findViewById(R.id.user_type_dropdown);
        editTextFilledExposedDropdown2.setAdapter(adapter2);

        String[] martialStatusArray = new String[] {"Single", "Married","Divorced" ,"Widow"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, martialStatusArray);
        AutoCompleteTextView editTextFilledExposedDropdown3 = findViewById(R.id.martial_dropdown);
        editTextFilledExposedDropdown3.setAdapter(adapter3);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String gender = editTextFilledExposedDropdown.getText().toString();
            String dateOfBirth = dateTextField.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String martialStatus = editTextFilledExposedDropdown3.getText().toString();
            String userType = editTextFilledExposedDropdown2.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();


            // Write a user to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef1 = database.getReference("users");
            myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isTaken = false;
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String str = ds.child("username").getValue(String.class);
                        if(str.equals(username)){
                            isTaken = true;
                            break;
                        }
                    }

                    if(isTaken){
                        Toast.makeText(RegisterActivity.this, "this username is taken", Toast.LENGTH_SHORT).show();
                    }else {
                        DatabaseReference myRef = database.getReference("users").push();
                        myRef.child("fullName").setValue(fullName);
                        myRef.child("gender").setValue(gender);
                        myRef.child("dateOfBirth").setValue(dateOfBirth);
                        myRef.child("phoneNumber").setValue(phoneNumber);
                        myRef.child("martialStatus").setValue(martialStatus);
                        myRef.child("userType").setValue(userType);
                        myRef.child("username").setValue(username);
                        myRef.child("password").setValue(password);
                        Toast.makeText(RegisterActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


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

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateTextField.setText(day+" / "+month+" / "+year);
        }
    }
}


