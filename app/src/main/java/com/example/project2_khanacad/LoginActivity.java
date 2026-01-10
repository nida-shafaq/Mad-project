package com.example.project2_khanacad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin, btnGoSignup;
    private TextView txtForgot;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SESSION HANDLING: Skip login if already logged in
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        // Views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);
        txtForgot = findViewById(R.id.txtForgot);

        // Firebase
        auth = FirebaseAuth.getInstance();

        // LOGIN
        btnLogin.setOnClickListener(v -> loginUser());

        // FORGOT PASSWORD
        txtForgot.setOnClickListener(v -> resetPassword());

        // GO TO SIGNUP
        btnGoSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );
    }

    // ================= LOGIN =================
    private void loginUser() {
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();

        if (e.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(e, p)
                .addOnSuccessListener(result -> {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, DashboardActivity.class));
                    finish();
                })
                .addOnFailureListener(ex -> {
                    if (ex instanceof FirebaseAuthInvalidUserException) {
                        Toast.makeText(this, "Account does not exist", Toast.LENGTH_LONG).show();
                    }
                    else if (ex instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Wrong password", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ================= FORGOT PASSWORD =================
    private void resetPassword() {
        String e = email.getText().toString().trim();

        if (e.isEmpty()) {
            Toast.makeText(this, "Enter email first", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(e)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                this,
                                "Reset link sent. Check Inbox / Spam",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        String msg = (task.getException() != null)
                                ? task.getException().getMessage()
                                : "Reset failed";
                        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
