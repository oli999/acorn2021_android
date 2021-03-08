package com.example.step01activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//app 을 실행했을때 처음 사용자를 대면하는 MainActivity
public class MainActivity extends AppCompatActivity {

    //액티비티가 활성화 될때 onCreate() 메소드가 호출된다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main);
        //액티비티가 활성화 되는 시점에 원하는 작업이 있으면 여기에 코딩한다.
    }

    /*
        activity_main.xml 에 정의된 버튼을 눌렀을때 호출되는 메소드 정의하기
        메소드의 인자로 View type 을 전달받도록 만들어야 한다.
     */
    public void sendClicked(View v){
        //콘솔창에 문자열 출력하기
        Log.i("sendClicked()", "전송 버튼을 눌렀네?");
        //잠깐 떳다가 사라지는 토스트 메세지 띄우기
        Toast.makeText(this, "전송 버튼을 눌렀네?", Toast.LENGTH_LONG).show();
    }
    public void deleteClicked(View v){
        //알림 다이얼로그 띄우기
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제 합니다")
                .setPositiveButton("확인", null)
                .create()
                .show();

        /*위의 코드를 여러줄로 작성하면 아래와 같다
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("삭제 합니다.");
        builder.setPositiveButton("확인", null);
        AlertDialog dialog=builder.create();
        dialog.show();
        */
    }
}