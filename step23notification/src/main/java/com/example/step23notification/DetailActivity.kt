package com.example.step23notification

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

    }
}