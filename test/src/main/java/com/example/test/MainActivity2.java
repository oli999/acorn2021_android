package com.example.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam1);

        Button sendBtn=findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        EditText inputMsg=findViewById(R.id.inputMsg);
        String msg=inputMsg.getText().toString();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}