package com.example.virusscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;

public class ScannerScreen extends AppCompatActivity {

    TextView pathTextView, MD5TextView;
    Intent myFileIntent;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){

            Uri uri  = data.getData();
            Context context = ScannerScreen.this;

            String path = GetRealPath.getRealPath(context, uri);
            pathTextView.setText(path);
        }
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode){
//            case 10:
//                if (resultCode == RESULT_OK){
////                    String path = data.getData().getPath();
//
//                    Uri uri = data.getData();
//                    String type = data.getType();
//                    if (uri != null)
//                    {
//                        String path = uri.toString();
//                        if (path.toLowerCase().startsWith("file://"))
//                        {
//                            // Selected file/directory path is below
//                            path = (new File(URI.create(path))).getAbsolutePath();
//                        }
//
//                        pathTextView.setText(path);
//
//                    }
//
//                }
//                break;
//        }
//    }
//
//    public static String fileToMD5(String filePath) {
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(filePath);
//            byte[] buffer = new byte[1024];
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            int numRead = 0;
//            while (numRead != -1) {
//                numRead = inputStream.read(buffer);
//                if (numRead > 0)
//                    digest.update(buffer, 0, numRead);
//            }
//            byte [] md5Bytes = digest.digest();
//            return convertHashToString(md5Bytes);
//        } catch (Exception e) {
//            return null;
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e) { }
//            }
//        }
//    }
//
//    private static String convertHashToString(byte[] md5Bytes) {
//        String returnVal = "";
//        for (int i = 0; i < md5Bytes.length; i++) {
//            returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
//        }
//        return returnVal.toUpperCase();
//    }

    public static String fileToMD5(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead);
            }
            byte [] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) { }
            }
        }
    }

    private static String convertHashToString(byte[] md5Bytes) {
        String returnVal = "";
        for (int i = 0; i < md5Bytes.length; i++) {
            returnVal += Integer.toString(( md5Bytes[i] & 0xff ) + 0x100, 16).substring(1);
        }
        return returnVal.toUpperCase();
    }

    public void scanVirus(View view ) {
        String path = pathTextView.getText().toString();
        String MD5Code = fileToMD5(path);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(getResources().openRawResource(R.raw.hashcodedata));
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(bufferedInputStream));

        if (pathTextView.getText().toString().isEmpty()) {
            Toast.makeText(ScannerScreen.this, "No file is selected", Toast.LENGTH_SHORT).show();
        } else {
            try {
                String line;
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
//                    Log.i("Test123", line + " + " + i);
                    if (line == MD5Code){
                        Toast.makeText(ScannerScreen.this, "This file is virus", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScannerScreen.this, "This file isn't virus", Toast.LENGTH_SHORT).show();
                    }
                    MD5TextView.setText("MD5: " + line);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}