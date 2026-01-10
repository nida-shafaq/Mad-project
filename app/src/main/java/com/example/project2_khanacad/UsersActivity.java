package com.example.project2_khanacad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    RecyclerView rvUsers;
    FirebaseFirestore db;
    ArrayList<ChatUser> users = new ArrayList<>();
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        rvUsers = findViewById(R.id.rvUsers);
        db = FirebaseFirestore.getInstance();

        adapter = new UserAdapter(users, this);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.setAdapter(adapter);

        String myUid = FirebaseAuth.getInstance().getUid();

        db.collection("users").get().addOnSuccessListener(qs -> {
            users.clear();
            for (DocumentSnapshot ds : qs) {
                ChatUser user = ds.toObject(ChatUser.class);
                if (!user.uid.equals(myUid)) {
                    users.add(user);
                }
            }
            adapter.notifyDataSetChanged();
        });
    }
}
