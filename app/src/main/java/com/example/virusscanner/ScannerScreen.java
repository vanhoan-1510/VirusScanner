package com.example.virusscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ScannerScreen extends AppCompatActivity {

    TextView textView;
    Intent myFileIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_screen);

        textView = (TextView) findViewById(R.id.path_tv);
    }

    public void openStorageDevice(View view){
        myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        myFileIntent.setType("*/*");
        startActivityForResult(myFileIntent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK){
                    String path = data.getData().getPath();
                    textView.setText(path);
                }
                break;
        }
    }

    public void scanVirus(View view ){
        if (textView.getText().toString().isEmpty()){
            Toast.makeText(ScannerScreen.this, "No file is selected", Toast.LENGTH_SHORT).show();
        }
    }

}