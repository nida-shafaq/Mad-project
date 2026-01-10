package com.example.project2_khanacad;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class ChatActivityRT extends AppCompatActivity {

    RecyclerView rvChat;
    EditText etMsg;
    Button btnSend;

    FirebaseFirestore db;
    String senderId, receiverId, chatId;

    ArrayList<ChatMessageModel> messages = new ArrayList<>();
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rt);

        rvChat = findViewById(R.id.rvChatRT);
        etMsg = findViewById(R.id.etMsg);
        btnSend = findViewById(R.id.btnSendRT);

        senderId = FirebaseAuth.getInstance().getUid();
        receiverId = getIntent().getStringExtra("receiverId");

        chatId = senderId.compareTo(receiverId) < 0
                ? senderId + "_" + receiverId
                : receiverId + "_" + senderId;

        db = FirebaseFirestore.getInstance();

        adapter = new MessageAdapter(messages, senderId);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        db.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener((snap, e) -> {
                    messages.clear();
                    for (DocumentSnapshot ds : snap) {
                        messages.add(ds.toObject(ChatMessageModel.class));
                    }
                    adapter.notifyDataSetChanged();
                    rvChat.scrollToPosition(messages.size() - 1);
                });

        btnSend.setOnClickListener(v -> {
            String msg = etMsg.getText().toString().trim();
            if (msg.isEmpty()) return;

            db.collection("chats")
                    .document(chatId)
                    .collection("messages")
                    .add(new ChatMessageModel(
                            senderId,
                            receiverId,
                            msg,
                            System.currentTimeMillis()
                    ));
            etMsg.setText("");
        });
    }
}
