package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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


public class ProfileActivity extends AppCompatActivity {

    static TextInputEditText dateTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dateTextField = findViewById(R.id.dateTextField);

        EditText usernameEditText = findViewById(R.id.usernameTextField);
        EditText passwordEditText = findViewById(R.id.passwordTextField);
        EditText fullNameEditText = findViewById(R.id.fullNameTextField);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberTextField);

        String[] Genders = new String[] {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Genders);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.gender_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        String[] martialStatusArray = new String[] {"Single", "Married","Divorced" ,"Widow"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, martialStatusArray);
        AutoCompleteTextView editTextFilledExposedDropdown3 = findViewById(R.id.martial_dropdown);
        editTextFilledExposedDropdown3.setAdapter(adapter3);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uName = ds.child("username").getValue(String.class);
                    if(uName!=null)
                    if(uName.equals(LoginActivity.username1)) {
                        String fName = ds.child("fullName").getValue(String.class);
                        String password = ds.child("password").getValue(String.class);
                        String dateOfBirth = ds.child("dateOfBirth").getValue(String.class);
                        String gender = ds.child("gender").getValue(String.class);
                        String martialStatus = ds.child("martialStatus").getValue(String.class);
                        String phoneNumber = ds.child("phoneNumber").getValue(String.class);

                        usernameEditText.setText(uName);
                        fullNameEditText.setText(fName);
                        passwordEditText.setText(password);
                        dateTextField.setText(dateOfBirth);
                        editTextFilledExposedDropdown.setText(gender);
                        editTextFilledExposedDropdown3.setText(martialStatus);
                        phoneNumberEditText.setText(phoneNumber);
                        adapter.getFilter().filter(null);
                        adapter3.getFilter().filter(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString();
            String gender = editTextFilledExposedDropdown.getText().toString();
            String dateOfBirth = Objects.requireNonNull(dateTextField.getText()).toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String martialStatus = editTextFilledExposedDropdown3.getText().toString();
            String newUsername = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();


            DatabaseReference myRef = database.getReference("users");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isTaken = false;
                    String currentUseSnapshot="unknown";
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String uName = ds.child("username").getValue(String.class);
                        if(uName!=null)
                        if(uName.equals(LoginActivity.username1)) {
                            currentUseSnapshot = ds.getKey();
                            if (uName.equals(newUsername) && !(newUsername.equals(LoginActivity.username1))) {
                                isTaken = true;
                                break;
                            }
                        }
                    }

                    if(isTaken){
                        Toast.makeText(ProfileActivity.this, "this username is taken", Toast.LENGTH_SHORT).show();
                    }else if(currentUseSnapshot!=null){
                        DatabaseReference myRef = database.getReference("users").child(currentUseSnapshot);
                        myRef.child("fullName").setValue(fullName);
                        myRef.child("gender").setValue(gender);
                        myRef.child("dateOfBirth").setValue(dateOfBirth);
                        myRef.child("phoneNumber").setValue(phoneNumber);
                        myRef.child("martialStatus").setValue(martialStatus);
                        myRef.child("username").setValue(newUsername);
                        LoginActivity.username1 = newUsername;
                        myRef.child("password").setValue(password);
                        Toast.makeText(ProfileActivity.this, "successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


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


