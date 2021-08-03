package com.example.healthadvisor.ui.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.ListViewAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentAppointmentBinding;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AppointmentFragment extends Fragment {

    private FragmentAppointmentBinding binding;
    ListView list;
    ArrayList<String> doctor;
    ArrayList<String> date;
    ArrayList<String> status;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAppointmentBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_appointment, container, false);

        doctor = new ArrayList<>();
        date = new ArrayList<>();
        status = new ArrayList<>();
        for(int i = 0 ; i < 15 ;i++){
            doctor.add("Dr. Abel");
            date.add("5 july 2000");
            status.add("Approved");
        }
        ListViewAdapter adapter = new ListViewAdapter(getActivity(),doctor,date,status);
        list = rootView.findViewById(R.id.list);
        list.setAdapter(adapter);

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
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}