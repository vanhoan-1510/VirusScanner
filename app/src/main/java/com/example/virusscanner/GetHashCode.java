package com.example.virusscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetHashCode extends AppCompatActivity {

    private TextView showMD5Hash, showSHA256Hash;
    private EditText inputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_hash_code);

        showMD5Hash = (TextView) findViewById(R.id.show_MD5hash);
        showSHA256Hash = (TextView) findViewById(R.id.show_SHA256hash);
        inputString = (EditText) findViewById(R.id.text_input);

    }



    public void GenerateStringToHash(View view){
        showMD5Hash.setText("MD5: " + getMdHash(inputString.getText().toString()));
        showSHA256Hash.setText("SHA256: " + getSHA256(inputString.getText().toString()));

    }

    @SuppressLint("SuspiciousIndentation")
    private String getMdHash(String toString){
        String MD5 = "MD5";

        try {

            //create MD5 hash
            final MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(toString.getBytes());

            final byte messageDigest[] = digest.digest();

            //create hex string
            StringBuilder hexString = new StringBuilder();

            for (byte aMsgDigest : messageDigest){
                String h = Integer.toHexString(0xFF & aMsgDigest);

                while (h.length() < 2)
                    h = "0" + h;
                    hexString.append(h);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSHA256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}