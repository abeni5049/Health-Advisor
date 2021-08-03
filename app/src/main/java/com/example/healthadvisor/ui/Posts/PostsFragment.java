package com.example.healthadvisor.ui.Posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.GridAdapter;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentPostsBinding;

public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    int logos[] = {R.drawable.chats, R.drawable.chats, R.drawable.chats, R.drawable.chats,
            R.drawable.chats, R.drawable.chats, R.drawable.chats, R.drawable.chats, R.drawable.chats,
            R.drawable.chats, R.drawable.chats, R.drawable.chats, R.drawable.chats};
    GridView simpleGrid;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPostsBinding.inflate(inflater, container, false);

        View rootView = inflater.inflate(R.layout.fragment_posts, container, false);
        simpleGrid = rootView.findViewById(R.id.simpleGridView) ; // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        GridAdapter customAdapter = new GridAdapter(getContext(), logos);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // set an Intent to Another Activity
//                Intent intent = new Intent(getContext(), SecondActivity.class);
//                intent.putExtra("image", logos[position]); // put image data in Intent
//                startActivity(intent); // start Intent
            }
        });
//        final TextView textView = binding.textHome;

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}