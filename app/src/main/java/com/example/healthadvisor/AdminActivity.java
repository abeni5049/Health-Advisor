package com.example.healthadvisor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    ArrayList<String> username;
    ArrayList<String> userType;
    static TextInputEditText dateTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin");
        
        username = new ArrayList<>();
        userType = new ArrayList<>();

        AdminListAdapter adapter = new AdminListAdapter(this,username,userType);
        ListView list = findViewById(R.id.admin_list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.clear();
                adapter.notifyDataSetChanged();
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

        ReportAdapter reportAdapter = new ReportAdapter(this,metricsList,valueList);
        ListView reportList = findViewById(R.id.report_list);
        reportList.setAdapter(reportAdapter);

        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            int numberOfMothers=0,numberOfPhysicians=0,numberOfFpWorkers=0,numberOfAdmins=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uType = ds.child("userType").getValue(String.class);
                    switch (Objects.requireNonNull(uType)){
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

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("register");
        tabs.addTab(spec);

        dateTextField = findViewById(R.id.dateTextField);

        EditText usernameEditText = findViewById(R.id.usernameTextField);
        EditText passwordEditText = findViewById(R.id.passwordTextField);
        EditText fullNameEditText = findViewById(R.id.fullNameTextField);
        EditText phoneNumberEditText = findViewById(R.id.phoneNumberTextField);

        String[] Genders = new String[] {"Male", "Female"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, Genders);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.gender_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter1);

        String[] usertype = new String[] {"Physician" ,"FP Worker","Admin"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, usertype);
        AutoCompleteTextView editTextFilledExposedDropdown2 = findViewById(R.id.user_type_dropdown);
        editTextFilledExposedDropdown2.setAdapter(adapter2);

        String[] martialStatusArray = new String[] {"Single", "Married","Divorced" ,"Widowed"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, martialStatusArray);
        AutoCompleteTextView editTextFilledExposedDropdown3 = findViewById(R.id.martial_dropdown);
        editTextFilledExposedDropdown3.setAdapter(adapter3);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String gender = editTextFilledExposedDropdown.getText().toString().trim();
            String dateOfBirth = Objects.requireNonNull(dateTextField.getText()).toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();
            String martialStatus = editTextFilledExposedDropdown3.getText().toString().trim();
            String userType = editTextFilledExposedDropdown2.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if(fullName.isEmpty() ||
                    gender.isEmpty() ||
                    dateOfBirth.isEmpty() ||
                    phoneNumber.isEmpty() ||
                    martialStatus.isEmpty() ||
                    userType.isEmpty() ||
                    username.isEmpty() ||
                    password.isEmpty()){
                Toast.makeText(this,"all fields are required",Toast.LENGTH_SHORT).show();
            }else {
                registerButton.setEnabled(false);
                DatabaseReference userRef = database.getReference("users");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            Toast.makeText(AdminActivity.this, "this username is taken", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(AdminActivity.this, "successfully registered", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            DatabaseReference ref1 = database.getReference("users");
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String usernameStr = Objects.requireNonNull(ds.child("username").getValue()).toString();
                        if( usernameStr.equals(username.get(position)) ){
                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                            builder.setPositiveButton("Yes", (dialog, id1) -> {
                                ds.getRef().removeValue();
                                Toast.makeText(getApplicationContext(), "account deleted", Toast.LENGTH_SHORT).show();
                            });
                            builder.setNegativeButton("No", (dialog, id12) -> dialog.cancel());
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("Delete User");
                            dialog.setMessage("Do you want to delete Account of "+usernameStr+" ?");
                            dialog.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item1){
            Intent intent = new Intent(AdminActivity.this,ProfileActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.item2){
            Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new AdminActivity.DatePickerFragment();
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