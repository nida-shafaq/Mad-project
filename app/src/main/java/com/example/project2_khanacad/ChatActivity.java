package com.example.project2_khanacad;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private EditText etMessage;
    private Button btnSend;

    private final ArrayList<ChatMessage> messages = new ArrayList<>();
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        rvChat = findViewById(R.id.rvChat);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        adapter = new ChatAdapter(messages);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(adapter);

        // Welcome message
        messages.add(new ChatMessage(
                "Hello 👋 I am your AI Tutor.\nAsk me about Android, Firebase, QR codes, AI, or Mobile Applications.",
                false
        ));
        adapter.notifyItemInserted(0);

        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String userText = etMessage.getText().toString().trim();

        if (userText.isEmpty()) {
            Toast.makeText(this, "Type something first", Toast.LENGTH_SHORT).show();
            return;
        }

        etMessage.setText("");

        // User bubble
        messages.add(new ChatMessage(userText, true));
        adapter.notifyItemInserted(messages.size() - 1);
        rvChat.scrollToPosition(messages.size() - 1);

        // Typing bubble
        messages.add(new ChatMessage("Typing...", false));
        int typingIndex = messages.size() - 1;
        adapter.notifyItemInserted(typingIndex);
        rvChat.scrollToPosition(typingIndex);

        generateAIReply(userText, typingIndex);
    }

    // LOCAL AI LOGIC (Exam safe, no billing issues)
    private void generateAIReply(String question, int typingIndex) {

        String q = question.toLowerCase();
        String reply;

        if (q.contains("mobile application")) {
            reply = "A mobile application is software designed to run on smartphones to perform specific tasks like learning, communication, or entertainment.";
        }
        else if (q.contains("android")) {
            reply = "Android is a mobile operating system developed by Google. Developers use Java or Kotlin to build Android apps.";
        }
        else if (q.contains("firebase")) {
            reply = "Firebase is a backend platform by Google that provides authentication, databases, storage, and analytics.";
        }
        else if (q.contains("qr")) {
            reply = "A QR code is a machine-readable code that stores information and can be scanned using a mobile camera.";
        }
        else if (q.contains("ai") || q.contains("artificial intelligence")) {
            reply = "Artificial Intelligence refers to systems that can perform tasks requiring human intelligence, such as learning and decision making.";
        }
        else if (q.contains("machine learning")) {
            reply = "Machine Learning is a subset of AI where systems learn from data and improve automatically.";
        }
        else if (q.contains("login") || q.contains("authentication")) {
            reply = "User authentication verifies user identity using email, password, or other secure methods like Firebase Auth.";
        }
        else {
            reply = "I am your AI Tutor 🤖. You can ask me questions related to Android development, Firebase, AI, QR codes, and mobile applications.";
        }

        // Delay to simulate AI thinking
        new Handler(getMainLooper()).postDelayed(() -> {
            messages.set(typingIndex, new ChatMessage(reply, false));
            adapter.notifyItemChanged(typingIndex);
            rvChat.scrollToPosition(typingIndex);
        }, 900);
    }
}
