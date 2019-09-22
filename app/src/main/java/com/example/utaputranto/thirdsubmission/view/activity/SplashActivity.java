package com.example.utaputranto.thirdsubmission.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utaputranto.thirdsubmission.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView tvTitle,tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imgLogo = findViewById(R.id.img_logo);
        tvTitle = findViewById(R.id.tv_title);
        tvVersion = findViewById(R.id.tv_version);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        imgLogo.startAnimation(animation);
        tvTitle.startAnimation(animation);
        tvVersion.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);
    }
}
