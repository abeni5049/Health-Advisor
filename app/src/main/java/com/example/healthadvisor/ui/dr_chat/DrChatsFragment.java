package com.example.healthadvisor.ui.dr_chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.PendingListAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentChatsDrBinding;

import java.util.ArrayList;

public class DrChatsFragment extends Fragment {

    private FragmentChatsDrBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_chats_dr, container, false);




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}