package com.example.healthadvisor.ui.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.MessageActivity;
import com.example.healthadvisor.MessageListAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentChatsBinding;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    private FragmentChatsBinding binding;
    ListView list;
    ArrayList<String> userName;
    ArrayList<String> latestMessage;
    ArrayList<String> usertype;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChatsBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_chats, container, false);

        userName = new ArrayList<>();
        latestMessage = new ArrayList<>();
        usertype = new ArrayList<>();

        for(int i = 0 ; i < 15 ;i++){
            userName.add("Dr. Abel");
            latestMessage.add("Thank you for your advice");
            usertype.add("Doctor");
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