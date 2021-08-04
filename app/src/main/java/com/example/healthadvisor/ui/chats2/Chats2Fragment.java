package com.example.healthadvisor.ui.chats2;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.MessageActivity;
import com.example.healthadvisor.MessageListAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentChats2Binding;

import java.util.ArrayList;

public class Chats2Fragment extends Fragment {

    private FragmentChats2Binding binding;
    ListView list;
    ArrayList<String> userName;
    ArrayList<String> latestMessage;
    ArrayList<String> usertype;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChats2Binding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_chats, container, false);

        userName = new ArrayList<>();
        latestMessage = new ArrayList<>();
        usertype = new ArrayList<>();

        for(int i = 0 ; i < 15 ;i++){
            userName.add("Dr. Abel");
            latestMessage.add("Thank you for your advice");
            usertype.add("Mother");
        }

        MessageListAdapter adapter = new MessageListAdapter(getActivity(),userName,latestMessage,usertype);
        list = rootView.findViewById(R.id.message_list);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), MessageActivity.class);
            intent.putExtra("username",userName.get(position));
            startActivity(intent);
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}