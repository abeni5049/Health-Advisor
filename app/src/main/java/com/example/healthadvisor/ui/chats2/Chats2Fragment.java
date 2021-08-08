package com.example.healthadvisor.ui.chats2;


import static com.example.healthadvisor.LoginActivity.username1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        MessageListAdapter adapter = new MessageListAdapter(getActivity(),userName,latestMessage,usertype);
        list = rootView.findViewById(R.id.message_list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("chats");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        String combinedUsername = ds.getKey();
                        String[] usernames = combinedUsername.split("-_-");
                        if(username1!=null)
                            if (username1.equals(usernames[0]) || username1.equals(usernames[1])) {
                                String username;
                                if (username1.equals(usernames[0])) {
                                    username = usernames[1];

                                } else {
                                    username = usernames[0];
                                }
                                userName.add(username);
                                latestMessage.add("");
                                usertype.add("Mother");
                                adapter.notifyDataSetChanged();
                            }
                    }catch (Exception e){
                        Log.d("exception",e.toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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