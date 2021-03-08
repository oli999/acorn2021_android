package com.example.step09fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity2 extends AppCompatActivity
                implements GuraFragment.GuraFragmentListener,
                    View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //레이아웃 xml 문서를 전개해서 화면 구성
        setContentView(R.layout.activity_main2);
        Button resetBtn=findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(this);
    }
    //GuraFrament 에서 특정 시점에 호출하는 메소드
    @Override
    public void wow(String msg) {
        TextView textConsole=findViewById(R.id.textConsole);
        textConsole.setText(msg);
    }

    @Override
    public void onClick(View v) {
        //액티비티의 getSupportFragmentManager() 메소드를 이용해서 FragmentManager 객체의
        //참조값을 얻어온다.
        FragmentManager fm=getSupportFragmentManager();
        //FragmentManager 객체의 메소드를 이용해서 Fragement 객체의 참조값 얻어오기
        Fragment f=fm.findFragmentById(R.id.guraFragment);
        //원래 type 으로 casting
        GuraFragment gf=(GuraFragment)f;
        gf.reset();

        //GuraFragment 객체의 참조값을 얻어오는 작업을 1줄로 줄이면..
        GuraFragment gf2=(GuraFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.guraFragment);
    }
}