package com.example.virusscanner;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Locale;

public class ScannerScreen extends AppCompatActivity {

    TextView pathTextView, MD5TextView;
    Intent myFileIntent;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_screen);

        pathTextView = (TextView) findViewById(R.id.path_tv);
        MD5TextView = (TextView) findViewById(R.id.tv_md5);
    }

    public void openStorageDevice(View view){
        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("*/*");
        startActivityForResult(myFileIntent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){

            Uri uri  = data.getData();
            Context context = ScannerScreen.this;

            String path = GetRealPath.getRealPath(context, uri);
            pathTextView.setText(path);
        }
    }


    public void ScanCSVFile(){
        String path = pathTextView.getText().toString();
        FileToMD5 fileToMD5 = new FileToMD5();
        String MD5Code = fileToMD5.fileToMD5(path);

        int count = 0;

        BufferedInputStream bufferedInputStream = new BufferedInputStream(getResources().openRawResource(R.raw.hashcodedata));
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(bufferedInputStream));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals(MD5Code)){
//                    Toast.makeText(ScannerScreen.this, "This file is virus", Toast.LENGTH_SHORT).show();
                    count = 1;

                    intent = new Intent(this, ProgressScreen.class);
                    intent.putExtra("md5code", MD5Code);
                    startActivity(intent);
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (count == 0 ){
//            Toast.makeText(ScannerScreen.this, "This file isn't virus", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, Progress2.class);
            intent.putExtra("md5code", MD5Code);
            startActivity(intent);
        }
    }

    public void scanVirus(View view ){
        if (pathTextView.getText().toString().isEmpty()) {
            Toast.makeText(ScannerScreen.this, "No file is selected", Toast.LENGTH_SHORT).show();
        } else {
            ScanCSVFile();
        }
    }
}