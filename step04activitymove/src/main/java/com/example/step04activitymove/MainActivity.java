package com.example.step04activitymove;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Gura 로 이동 버튼을 눌렀을때 호출되는 메소드
    public void moveToGura(View v){
       /*
            액티비티를 이동하는 코드를 작성해 보세요.
        */
        // GuraActivity 로 이동하겠다는 의도(Intent) 객체 생성하기
        Intent intent=new Intent(this, GuraActivity.class);
        startActivity(intent);
    }
    //Rabbit 으로 이동 버튼을 눌렀을때 호출되는 메소드
    public void moveToRabbit(View v){
        Intent intent=new Intent(this, RabbitActivity.class);
        startActivity(intent);
    }
}







