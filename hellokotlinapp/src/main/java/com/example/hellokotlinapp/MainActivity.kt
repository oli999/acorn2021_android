package com.example.hellokotlinapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener { // extends AppCompatActivity
    /*
        null 을 허용하는 inputMsg 필드를 선언하고 null 로 초기화 하기
     */
    var inputMsg: EditText? = null
    //위의 표현식이 번거로우면 아래처럼 lateinit 예약어를 이용한다.
    lateinit  var console: TextView

    // onCreate() 메소드 오버라이드
    override fun onCreate(savedInstanceState: Bundle?) { // ?는 null 가능
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //EditText 객체의 참조값 얻어오기
        inputMsg= findViewById(R.id.inputMsg)
        //TextView 객체의 참조값 얻어오기
        console= findViewById(R.id.console)
        //Button 의 참조값 얻어와서 리스너 등록
        val sendBtn:Button = findViewById(R.id.sendBtn)
        sendBtn.setOnClickListener(this)
        //Button 의 참조값 얻어와서 리스너 등록
        val sendBtn2:Button = findViewById(R.id.sendBtn2)
        //익명의 inner class 를 이용해서 리스너 등록
        /*
        sendBtn2.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                val msg=inputMsg?.text.toString()
                console.setText(msg)
            }
        })
        */
        sendBtn2.setOnClickListener {
            val msg=inputMsg?.text.toString()
            console.setText(msg)
        }

        //이동하기 버튼
        val moveBtn:Button = findViewById(R.id.moveBtn)
        moveBtn.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                /*
                       in java => MainActivity.this
                       in kotlin => this@MainActivity
                       ------------------------------
                       in java => SubActivity.class
                       in kotlin => SubActivity::class.java
                 */
                //액티비티를 이동할 Intent 객체
                val intent=Intent(this@MainActivity,
                        SubActivity::class.java)
                //액티비티 이동하기
                startActivity(intent)
            }
        })
    }

    override fun onClick(v: View?) {
        /*
            getter, setter 메소드는 필드를 참조하는 형식으로 사용한다.
            ? 는 null 일 가능성이 있는 자원을 참조할때 뒤에 붙여야 한다.
         */

        // in java =>  String msg=inputMsg.getText().toString()
        val msg=inputMsg?.text.toString()
        console.setText(msg)
        //console.text=msg //이렇게 사용할수도 있다.

        // .getText() 는  .text 로 사용할수 있다.
        val a = console.getText()
        val b = console.text

    }
}





