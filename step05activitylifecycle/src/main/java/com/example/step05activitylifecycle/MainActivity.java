package com.example.step05activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //빨간색 글씨로 로그 출력하기 Log.e( 테그, 내용)
        Log.e("MainActivity", "onCreate()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MainActivity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MainActivity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MainActivity", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MainActivity", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity", "onDestroy()");
    }
    //이동 버튼을 눌렀을때 호출되는 메소드
    public void moveToSub(View v){
        //SubActivity 로 이동
        startActivity(new Intent(this, SubActivity.class));
    }
}