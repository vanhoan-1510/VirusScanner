package com.example.virusscanner;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (CardView) findViewById(R.id.btnStart);

        cardView.setOnClickListener((View.OnClickListener) this);

        //get permission

        if (checkPermission()){
            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        } else {
            takePermission();
        }
//        if(ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE}, 1);
//        }
    }

//    public void takePermissions(View view){
//        if (checkPermission()){
//            Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
//        } else {
//            takePermission();
//        }
//    }

    boolean checkPermission(){
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int readCheck = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
            return readCheck == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void takePermission(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent,100);
            } catch (Exception exception){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 100);
            }
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, 101);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                    if (Environment.isExternalStorageManager()){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        takePermission();
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0){
            if (requestCode == 101) {
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (readExternalStorage){
                    Toast.makeText(this, "Read Permission is Granted in android 10 or below", Toast.LENGTH_SHORT).show();
                }
                else {
                    takePermission();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        cardView : intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}