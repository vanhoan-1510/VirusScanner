package com.example.virusscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private CardView scan, hash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        scan = (CardView) findViewById(R.id.scanCard);
        hash = (CardView) findViewById(R.id.hashCard);

        scan.setOnClickListener((View.OnClickListener) this);
        hash.setOnClickListener((View.OnClickListener) this);
    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.scanCard: intent = new Intent(this, ScannerScreen.class); startActivity(intent); break;
            case R.id.hashCard: intent = new Intent(this, GetHashCode.class); startActivity(intent); break;
        }
    }
}