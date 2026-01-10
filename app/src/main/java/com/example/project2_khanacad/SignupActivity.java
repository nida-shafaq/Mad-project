package com.example.project2_khanacad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class SignupActivity extends AppCompatActivity {

    EditText email, password;
    Button btnSignup;
    Button btnGoLogin;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        btnGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(v -> createAccount());
    }

    private void createAccount() {
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();

        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(e, p)
                .addOnSuccessListener(result -> {
                    Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(ex -> {

                    if (ex instanceof FirebaseAuthWeakPasswordException) {
                        Toast.makeText(this,
                                "Password is too weak (min 6 characters)",
                                Toast.LENGTH_LONG).show();
                    }
                    else if (ex instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(this,
                                "Account already exists with this email",
                                Toast.LENGTH_LONG).show();
                    }
                    else if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this,
                                "Invalid email format",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this,
                                ex.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
