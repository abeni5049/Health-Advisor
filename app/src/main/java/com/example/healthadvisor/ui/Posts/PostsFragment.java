package com.example.healthadvisor.ui.Posts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.DetailActivity;
import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentPostsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    ArrayList<String> title;
    ArrayList<String> info;
    ArrayList<String> author;
    ArrayList<String> date;
    ArrayList<String> postID;
    ListView simpleGrid;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPostsBinding.inflate(inflater, container, false);

        title = new ArrayList<>();
        info = new ArrayList<>();
        author = new ArrayList<>();
        date = new ArrayList<>();
        postID = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        simpleGrid = rootView.findViewById(R.id.simpleGridView) ;
        GridAdapter customAdapter = new GridAdapter(getContext(), title,info,author,date,postID);
        simpleGrid.setAdapter(customAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("posts");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String title_ = ds.child("title").getValue(String.class);
                    String info_ = ds.child("info").getValue(String.class);
                    String author_ = ds.child("author").getValue(String.class);
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

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}