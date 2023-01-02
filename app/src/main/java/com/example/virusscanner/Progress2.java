package com.example.virusscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Progress2 extends AppCompatActivity {

    private int CurrentProgress = 0;
    ProgressBar progressBar;
    TextView tv_progress;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress2);

        progressBar = findViewById(R.id.progress_bar);
        tv_progress = findViewById(R.id.text_view_progress);

        CountDownTimer countDownTimer = new CountDownTimer(11*1000,100) {
            @Override
            public void onTick(long l) {
                if (CurrentProgress < 100) {
                    CurrentProgress = CurrentProgress + 1;
                    progressBar.setProgress(CurrentProgress);
                    tv_progress.setText(CurrentProgress + "%");
                    progressBar.setMax(100);
                }
            }

            @Override
            public void onFinish() {
                    noMalwareScreen();
            }
        };
        countDownTimer.start();

    }

    private void noMalwareScreen(){
        String MD5CodeToDisplay = getIntent().getStringExtra("md5code");
        intent = new Intent(this, NoMalwareScreen.class);
        intent.putExtra("MD5Code", MD5CodeToDisplay);
        startActivity(intent);
    }

}