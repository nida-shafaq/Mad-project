package com.example.project2_khanacad;

public class ChatMessageModel {
    public String senderId;
    public String receiverId;
    public String message;
    public long timestamp;

    public ChatMessageModel() {}

    public ChatMessageModel(String senderId, String receiverId, String message, long timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = timestamp;
    }
}

