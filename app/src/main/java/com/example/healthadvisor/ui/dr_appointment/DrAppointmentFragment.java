package com.example.healthadvisor.ui.dr_appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.PendingListAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentAppointmentDrBinding;

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
        for(int i = 0 ; i < 15 ; i++){
            username.add("Ms. birke");
            reason.add("I want an appointment to get advice in birth control methods in person");
            date.add("5 July 200");
        }
        PendingListAdapter adapter = new PendingListAdapter(getActivity(),username,reason,date);
        ListView listView = root.findViewById(R.id.pending_list);
        listView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}