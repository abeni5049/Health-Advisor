package com.example.healthadvisor.ui.appointment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthadvisor.AdminActivity;
import com.example.healthadvisor.FpActivity;
import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.ListViewAdapter;
import com.example.healthadvisor.LoginActivity;
import com.example.healthadvisor.MotherActivity;
import com.example.healthadvisor.PhysicianActivity;
import com.example.healthadvisor.R;
import com.example.healthadvisor.RegisterActivity;
import com.example.healthadvisor.databinding.FragmentAppointmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

public class AppointmentFragment extends Fragment {

    private FragmentAppointmentBinding binding;
    private static EditText dateTextField;
    ListView list;
    ArrayList<String> physician;
    ArrayList<String> date;
    ArrayList<String> status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppointmentBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        physician = new ArrayList<>();
        date = new ArrayList<>();
        status = new ArrayList<>();
        ListViewAdapter adapter = new ListViewAdapter(getActivity(),physician,date,status);
        list = rootView.findViewById(R.id.list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("appointments");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String motherUserName = ds.child("motherUsername").getValue(String.class);
                    if(motherUserName.equals(MotherActivity.motherUsername)){
                        String physicianUsername = ds.child("physicianUsername").getValue(String.class);
                        String dateString = ds.child("date").getValue(String.class);
                        String statusString = ds.child("status").getValue(String.class);
                        physician.add(physicianUsername);
                        date.add(dateString);
                        status.add(statusString);
                        adapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        EditText physicianUserNameTextField = rootView.findViewById(R.id.physician_username_text_field);
        dateTextField = rootView.findViewById(R.id.dateTextField);

        TabHost tabs = rootView.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Request");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Status");
        tabs.addTab(spec);





        Button requestButton = rootView.findViewById(R.id.request_button);
        requestButton.setOnClickListener(V->{
            DatabaseReference userRef = database.getReference("users");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean isExist = false;
                    String fName="";
                    String physicianUserName = physicianUserNameTextField.getText().toString();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String uName = ds.child("username").getValue(String.class);
                        fName = ds.child("fullName").getValue(String.class);
                        String uType = ds.child("userType").getValue(String.class);
                        if(uType.equals("Physician") && uName.equals(physicianUserName) ) {
                            isExist = true;
                            break;
                        }
                    }

                    if(isExist){
                        String physicianUsername = physicianUserNameTextField.getText().toString();
                        String date = dateTextField.getText().toString();
                        String motherUsername = MotherActivity.motherUsername;
                        DatabaseReference myRef = database.getReference("appointments").push();
                        myRef.child("physicianUsername").setValue(physicianUsername);
                        myRef.child("date").setValue(date);
                        myRef.child("status").setValue("pending");
                        myRef.child("motherUsername").setValue(motherUsername);
                    }else{
                        Toast.makeText(getContext(), "this username does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        dateTextField.setOnClickListener( V ->{
            showTimePickerDialog();
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getChildFragmentManager(), "DatePicker");
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
            String monthName = new DateFormatSymbols().getMonths()[month-1];
            dateTextField.setText(day+"  "+monthName+"  "+year);

        }
    }

}