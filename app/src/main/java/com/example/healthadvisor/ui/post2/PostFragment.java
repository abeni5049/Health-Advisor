package com.example.healthadvisor.ui.post2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.healthadvisor.FpActivity;
import com.example.healthadvisor.PhysicianActivity;
import com.example.healthadvisor.R;
import com.example.healthadvisor.databinding.FragmentPost2Binding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostFragment extends Fragment {

private FragmentPost2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_post2, container, false);
    EditText titleEditText = root.findViewById(R.id.title_text_field);
    EditText infoEditText = root.findViewById(R.id.information_text_field);
    Button post = root.findViewById(R.id.post_button);
    post.setOnClickListener(v->{
        String title = titleEditText.getText().toString();
        String info = infoEditText.getText().toString();
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String currentDateString = formatter.format(todayDate);
        String author = FpActivity.FpFullName;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts").push();
        myRef.child("title").setValue(title);
        myRef.child("info").setValue(info);
        myRef.child("date").setValue(currentDateString);
        myRef.child("author").setValue(author);
    });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}