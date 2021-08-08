package com.example.healthadvisor.ui.Post;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.DetailActivity;
import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.LoginActivity;
import com.example.healthadvisor.PhysicianActivity;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentPostBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PostFragment extends Fragment {

    private FragmentPostBinding binding;
    ArrayList<String> title;
    ArrayList<String> info;
    ArrayList<String> author;
    ArrayList<String> date;
    ArrayList<String> postID;
    ListView simpleGrid;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_post, container, false);

        EditText titleEditText = root.findViewById(R.id.title_text_field);
        EditText infoEditText = root.findViewById(R.id.information_text_field);
        Button post = root.findViewById(R.id.post_button);
        post.setOnClickListener(v->{
            String title = titleEditText.getText().toString();
            String info = infoEditText.getText().toString();
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
            String currentDateString = formatter.format(todayDate);
            String author = PhysicianActivity.physicianFullName;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("posts").push();
            myRef.child("title").setValue(title);
            myRef.child("info").setValue(info);
            myRef.child("date").setValue(currentDateString);
            myRef.child("author").setValue(author);
            myRef.child("postID").setValue(myRef.getKey());
        });

        title = new ArrayList<>();
        info = new ArrayList<>();
        author = new ArrayList<>();
        date = new ArrayList<>();
        postID = new ArrayList<>();

        simpleGrid = root.findViewById(R.id.your_posts_list) ;
        GridAdapter customAdapter = new GridAdapter(getContext(), title,info,author,date,postID);
        simpleGrid.setAdapter(customAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("posts");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String author_ = ds.child("author").getValue(String.class);
                    if(author_.equals(PhysicianActivity.physicianFullName)) {
                        String title_ = ds.child("title").getValue(String.class);
                        String info_ = ds.child("info").getValue(String.class);
                        String date_ = ds.child("date").getValue(String.class);
                        String postID_ = ds.child("postID").getValue(String.class);
                        title.add(title_);
                        info.add(info_);
                        author.add(author_);
                        date.add(date_);
                        postID.add(postID_);
                        customAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        simpleGrid.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("title",title.get(position));
            intent.putExtra("author",author.get(position));
            intent.putExtra("info",info.get(position));
            intent.putExtra("date",date.get(position));
            startActivity(intent);
        });

        simpleGrid.setOnItemLongClickListener((parent, view, position, id) -> {
            DatabaseReference ref1 = database.getReference("posts");
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()){
                        if( ds.child("postID").getValue().toString().equals(postID.get(position)) ){
                            ds.getRef().removeValue();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            return false;
        });

        TabHost tabs = root.findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("post");
        tabs.addTab(spec);
        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("your posts");
        tabs.addTab(spec);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}