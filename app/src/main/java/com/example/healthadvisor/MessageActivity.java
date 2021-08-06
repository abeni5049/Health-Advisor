package com.example.healthadvisor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class MessageActivity extends AppCompatActivity {

    RelativeLayout back,messageContainer;
    EditText messageBox;
    MessageAdapter adapter;
    ImageView send;
    private String user1username;
    private String user2username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        user2username = intent.getStringExtra("username");

        user1username = LoginActivity.username1;


        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.message_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> messageArray = new ArrayList<>();
        ArrayList<Boolean> senderArray = new ArrayList<>();
        adapter = new MessageAdapter(this,messageArray,senderArray);
        ListView listView = findViewById(R.id.message_list);
        listView.setAdapter(adapter);


        back = findViewById(R.id.messageBackground);
       // back.setBackgroundColor(getResources().getColor(R.color.purple_700));
        messageContainer = findViewById(R.id.message_container);

        messageBox = findViewById(R.id.message_box);
        send = findViewById(R.id.send);

        send.setOnClickListener(v -> {
            String message = messageBox.getText().toString().trim();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String[] str = {user1username,user2username};
            Arrays.sort(str);
            String combinedUsername = str[0]+"-_-"+str[1];
            DatabaseReference ref = database.getReference("chats").child(combinedUsername).push();
            ref.child("message").setValue(message);
            ref.child("sender").setValue(user1username);
            messageBox.setText("");
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
