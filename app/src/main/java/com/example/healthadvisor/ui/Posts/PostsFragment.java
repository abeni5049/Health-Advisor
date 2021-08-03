package com.example.healthadvisor.ui.Posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentPostsBinding;

import java.util.ArrayList;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    ArrayList<String> title;
    ArrayList<String> info;
    ArrayList<String> author;
    ArrayList<String> date;
    GridView simpleGrid;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPostsBinding.inflate(inflater, container, false);

        title = new ArrayList<>();
        info = new ArrayList<>();
        author = new ArrayList<>();
        date = new ArrayList<>();
        for(int i = 0 ; i < 30 ; i++){
            title.add("birth control");
            info.add("there are a lot of methods to control birth , among these using condom is the best");
            author.add("Author: Dr. abel");
            date.add("Date: 5 july 2000");
        }
        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        simpleGrid = rootView.findViewById(R.id.simpleGridView) ; // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        GridAdapter customAdapter = new GridAdapter(getContext(), title,info,author,date);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener((parent, view, position, id) -> {
//                // set an Intent to Another Activity
//                Intent intent = new Intent(getContext(), SecondActivity.class);
//                intent.putExtra("image", logos[position]); // put image data in Intent
//                startActivity(intent); // start Intent
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}