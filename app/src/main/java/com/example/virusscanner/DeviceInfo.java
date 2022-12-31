package com.example.virusscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        textView = (TextView) findViewById(R.id.tv_displayInfo);

        textView.setText(
                "Model: " + Build.MODEL + "\n" +
                        "ID: " + Build.ID + "\n" +
                        "SDK:" + Build.VERSION.SDK_INT + "\n" +
                        "Manufacture: " + Build.MANUFACTURER + " \n" +
                        "Brand: " + Build.BRAND + "\n" +
                        "User: " + Build.USER + "\n" +
                        "Type: " + Build.TYPE + "\n" +
                        "Base: " + Build.VERSION_CODES.BASE + "\n" +
                        "Incremental: " + Build.VERSION.INCREMENTAL + "\n" +
                        "Board: " + Build.BOARD + "\n" +
                        "Host: " + Build.HOST + "\n" +
                        "FingerPrint: " + Build.FINGERPRINT + "\n" +
                        "Version Code: " + Build.VERSION.RELEASE);
    }
}