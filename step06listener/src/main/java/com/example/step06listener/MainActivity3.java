package com.example.step06listener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //id 가 sendBtn 인 Button 의 참조값 얻어오기
        Button sendBtn=findViewById(R.id.sendBtn);

        //전송 버튼에 필드에 저장된 클릭 리스너 등록하기
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "전송합니다. 3", Toast.LENGTH_SHORT).show();
    }
}