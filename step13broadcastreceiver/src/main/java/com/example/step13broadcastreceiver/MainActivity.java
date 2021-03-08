package com.example.step13broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //필드
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //방송 수신자 객체를 생성해서 동작이 가능하도록 등록하기
        MyReceiver mr=new MyReceiver();
        registerReceiver(mr, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        //TextView 의 참조값을 필드에 저장
        textView=findViewById(R.id.textView);
    }
    //방송 수신자 객체에서 호출할 메소드
    public void setMessage(String msg){
        textView.setText(msg);
    }
}