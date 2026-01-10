package com.example.project2_khanacad;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.VH> {

    ArrayList<ChatUser> users;
    Context context;

    public UserAdapter(ArrayList<ChatUser> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ChatUser user = users.get(position);
        holder.txtEmail.setText(user.email);

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, ChatActivityRT.class);
            i.putExtra("receiverId", user.uid);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtEmail;
        VH(View v) {
            super(v);
            txtEmail = v.findViewById(R.id.txtUserEmail);
        }
    }
}
