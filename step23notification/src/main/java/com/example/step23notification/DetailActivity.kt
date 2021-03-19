package com.example.step23notification

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //DetailActivity 가 활성화 되기 위해서 전달된 Intent 객체의 참조값
        val i=getIntent() // value intent 를 바로 참조해도 된다.
        //Intent 객체에 msg 라는 키값으로 전달된 문자열 얻어오기
        val msg=i.getStringExtra("msg")
        //TextView 에 출력하기
        findViewById<TextView>(R.id.textView).setText(msg)
        /*
        val textView:TextView = findViewById(R.id.textView)
        textView.setText(msg)
         */
        //버튼의 참조값 얻어와서 리스너 등록
        findViewById<Button>(R.id.deleteBtn).setOnClickListener{
            //인텐츠에 id 라는 키값으로 담긴 정수값 얻어오기 (없으면 기본값 0 )
            val id = i.getIntExtra("id", 0)
            //알림 메니저 객체 얻어오기
            val notiManager:NotificationManagerCompat=
                    NotificationManagerCompat.from(this)
            //알림의 아이디를 이용해서 해당 알림 삭제
            notiManager.cancel(id)
            //액티비티 종료하기
            finish()
        }
    }
}