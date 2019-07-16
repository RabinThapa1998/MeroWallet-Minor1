package com.example.merowalletv11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(in);
                // overridePendingTransition(R.anim.opening_animation, R.anim.closing_animation);
                finish();
            }
        }, 2000);
    }
}
