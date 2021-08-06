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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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


        MessageListAdapter adapter = new MessageListAdapter(getActivity(),userName,latestMessage,usertype);
        list = rootView.findViewById(R.id.message_list);
        list.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("users");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String uname = ds.child("username").getValue(String.class);
                    String utype = ds.child("userType").getValue(String.class);
                    if(utype.equals("Physician") ||utype.equals("FP Worker")  ) {
                        userName.add(uname);
                        latestMessage.add("Thank you for your advice");
                        usertype.add(utype);
                        adapter.notifyDataSetChanged();
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