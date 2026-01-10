package com.example.project2_khanacad;

import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ChatMessageModel> messages;
    String myId;

    public MessageAdapter(ArrayList<ChatMessageModel> messages, String myId) {
        this.messages = messages;
        this.myId = myId;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).senderId.equals(myId) ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int type) {
        if (type == 1)
            return new VH(LayoutInflater.from(p.getContext())
                    .inflate(R.layout.item_message_sender, p, false));
        else
            return new VH(LayoutInflater.from(p.getContext())
                    .inflate(R.layout.item_message_receiver, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h, int pos) {
        ((VH) h).txtMsg.setText(messages.get(pos).message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtMsg;
        VH(View v) {
            super(v);
            txtMsg = v.findViewById(R.id.txtMessage);
        }
    }
}
