package com.example.project2_khanacad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Already logged in
            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            // Not logged in
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}
