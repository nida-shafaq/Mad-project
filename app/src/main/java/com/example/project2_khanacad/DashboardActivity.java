package com.example.project2_khanacad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    Button btnLogout, btnScanQR, btnAiTutor, btnChatUsers;
    AdView adView;

    InterstitialAd interstitialAd;
    String nextAction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_dashboard);

        btnLogout = findViewById(R.id.btnLogout);
        btnScanQR = findViewById(R.id.btnScanQR);
        btnAiTutor = findViewById(R.id.btnAiTutor);
        btnChatUsers = findViewById(R.id.btnChatUsers);
        adView = findViewById(R.id.adView);

        /* ---------- AdMob ---------- */
        MobileAds.initialize(this, status -> {});
        adView.loadAd(new AdRequest.Builder().build());
        loadInterstitialAd();

        /* ---------- Buttons ---------- */

        btnScanQR.setOnClickListener(v -> {
            nextAction = "SCAN";
            showAdOrContinue();
        });

        btnAiTutor.setOnClickListener(v -> {
            nextAction = "AI";
            showAdOrContinue();
        });

        btnChatUsers.setOnClickListener(v -> {
            nextAction = "USERS";
            showAdOrContinue();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void loadInterstitialAd() {
        InterstitialAd.load(
                this,
                "ca-app-pub-3940256099942544/1033173712",
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(InterstitialAd ad) {
                        interstitialAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError error) {
                        interstitialAd = null;
                    }
                }
        );
    }

    private void showAdOrContinue() {
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            interstitialAd = null;
                            loadInterstitialAd();
                            proceed();
                        }
                    }
            );
            interstitialAd.show(this);
        } else {
            proceed();
        }
    }

    private void proceed() {
        if (nextAction.equals("SCAN")) {
            startActivity(new Intent(this, QRScannerActivity.class));
        } else if (nextAction.equals("AI")) {
            startActivity(new Intent(this, ChatActivity.class));
        } else if (nextAction.equals("USERS")) {
            startActivity(new Intent(this, UsersActivity.class));
        }
    }
}
