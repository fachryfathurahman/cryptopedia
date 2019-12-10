package com.cryptodev.cryptopedia;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class chatroom extends AppCompatActivity {

    private ImageButton btn_send_msg;
    private EditText input_msg;
    private TextView  chat_conversation;
    private String user_name,room;
    private DatabaseReference root;
    private String temp_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        btn_send_msg = findViewById(R.id.button_chat);
        input_msg = findViewById(R.id.editText_chat);
        chat_conversation = findViewById(R.id.textView_chat);
        user_name = getIntent().getStringExtra("username");
        room="global";
        setTitle(room);

        root = FirebaseDatabase.getInstance().getReference().child(room);

        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg",input_msg.getText().toString());

                message_root.updateChildren(map2);
                input_msg.setText("");

            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversatin(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversatin(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    private String chat_msg, chat_user_name;

    private void append_chat_conversatin(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");

        }
    }

}

