package com.example.healthadvisor.ui.dr_appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.MotherActivity;
import com.example.healthadvisor.PendingListAdapter;
import com.example.healthadvisor.PhysicianActivity;
import com.example.healthadvisor.R;
import com.example.healthadvisor.UpcomingListAdapter;
import com.example.healthadvisor.databinding.FragmentAppointmentDrBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DrAppointmentFragment extends Fragment {

    private FragmentAppointmentDrBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_appointment_dr, container, false);

        TabHost tabs = (TabHost) root.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Pending");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Upcoming");
        tabs.addTab(spec);
        ArrayList<String> username = new ArrayList<>();
        ArrayList<String>  reason= new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> appointmentID = new ArrayList<>();
        PendingListAdapter adapter = new PendingListAdapter(getActivity(),username,reason,date,appointmentID);
        ListView listView = root.findViewById(R.id.pending_list);
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("appointments");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.clear();
                reason.clear();
                date.clear();
                appointmentID.clear();
                adapter.notifyDataSetChanged();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String str = ds.child("physicianUsername").getValue(String.class);
                    String statusString = ds.child("status").getValue(String.class);
                    if(str.equals(PhysicianActivity.physicianUsername) && statusString.equals("pending")){
                        String motherUsername = ds.child("motherUsername").getValue(String.class);
                        String dateString = ds.child("date").getValue(String.class);
                        String appointmentIDString = ds.child("appointmentID").getValue(String.class);
                        appointmentID.add(appointmentIDString);
                        username.add(motherUsername);
                        reason.add("I want an appointment to get advice in birth control methods in person");
                        date.add(dateString);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayList<String> motherUsername = new ArrayList<>();
        ArrayList<String>  date2 = new ArrayList<>();

        UpcomingListAdapter adapter2 = new UpcomingListAdapter(getActivity(),motherUsername,date2);
        ListView list2 = root.findViewById(R.id.upcoming_list);
        list2.setAdapter(adapter2);

        DatabaseReference acc = database.getReference("appointments");
        acc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                motherUsername.clear();
                date2.clear();
                adapter.notifyDataSetChanged();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String str = ds.child("physicianUsername").getValue(String.class);
                    String statusString = ds.child("status").getValue(String.class);
                    if(str.equals(PhysicianActivity.physicianUsername) && statusString.equals("accepted")){
                        String motherUsernameStr = ds.child("motherUsername").getValue(String.class);
                        String dateString = ds.child("date").getValue(String.class);
                        motherUsername.add(motherUsernameStr);
                        date2.add(dateString);
                        adapter2.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}