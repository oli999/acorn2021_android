package com.example.step03view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //액티비티가 활성화 될때 호출되는 메소드
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        /*
            레이아웃 리소스 아이디를 전달하면 자동으로 UI 객체들이 생성되고
            생성된 UI 가 화면을 구성하게 된다.
            이 예제 에서는 ConstraintLayout, EditText, Button, TextView 객체가 생성이 되어서
            화면 구성을 하게 된다.
         */
        setContentView(R.layout.activity_main);
    }
    //전송 버튼을 눌렀을때 호출되는 메소드
    public void sendClicked(View v){
        //1. EditText 에 입력한 문자열을 읽어온다.
        EditText a=findViewById(R.id.inputText);
        String msg=a.getText().toString();
        //2. Toast 에 읽어온 문자열을 띄운다.
        // 액티비티의 메소드 안에서 Context type 이 필요하면 this 를 전달하면 된다.
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        //3. TextView 에도 출력
        TextView textView=findViewById(R.id.textView);
        textView.setText(msg);
        //4. EditText 에 입력된 문자열 삭제
        a.setText("");
    }
}







