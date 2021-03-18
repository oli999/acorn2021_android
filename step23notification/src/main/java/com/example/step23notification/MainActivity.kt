package com.example.step23notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity(), View.OnClickListener{
    //EditText 의 참조값을 저장할 필드
    lateinit var inputMsg:EditText

    //알림 채널 (중복되는 이름이 없도록 페키지명을 같이 넣어준다)
    val CHANNEL_ID="com.example.step23notification.MY_CHANNEL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //EditText 의 참조값
        inputMsg=findViewById(R.id.inputMsg)

        //버튼의 참조값 얻어와서 리스너 등록
        val notiBtn: Button =findViewById(R.id.notiBtn)
        notiBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        //입력한 문자열을 읽어와서
        var msg=inputMsg.text.toString().trim()
        //알림 띄우기
        makeAutoCancelNoti(msg)
    }
    //인자로 전달되는 문자열을 알림에 띄우는 함수
    fun makeAutoCancelNoti(msg:String){
        //이 앱의 알림 체널 만들기
        createNotificationChannel()
        //알림을 클릭했을때 실행할 Activity 정보를 가지고 있는 Intent 객체
        val intent = Intent(this, DetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("msg", msg)
        }
        //아이디 얻어내기
        val currentId:Int = (System.currentTimeMillis()/1000).toInt()

        //Intent 객체를 인텐트 전달자 객체에 담는다.
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, currentId, intent, 0)

        //NotificationCompat.Builer 객체 생성
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now) //알림 아이콘
                .setContentTitle("오빠 나야~") //알림 제목
                .setContentText(msg) //알림 내용
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) //우선순위
                .setContentIntent(pendingIntent) //인텐트 전달자 객체
                .setAutoCancel(true) //자동으로 알림이 없어지도록



        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(currentId, builder.build())
        }
    }
    /*
        앱의 사용자가 알림을 직접 관리 할수 있도록 알림 채널을 만들어야한다.
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "kim"
            val descriptionText = "test!"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    //인자로 전달되는 문자열을 알림에 띄우는 함수
    fun makeManualCancelNoti(msg:String){
        //이 앱의 알림 체널 만들기
        createNotificationChannel()

        val currentId:Int = (System.currentTimeMillis()/1000).toInt()

        //알림을 클릭했을때 실행할 Activity 정보를 가지고 있는 Intent 객체
        val intent = Intent(this, DetailActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("msg", msg)
            putExtra("id", currentId)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("msg", msg)

        //Intent 객체를 인텐트 전달자 객체에 담는다.
        val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, currentId, intent, 0)

        //NotificationCompat.Builer 객체 생성
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                .setContentTitle("오빠 나야~")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false) //자동 cancel 되지 않도록

        with(NotificationManagerCompat.from(this)) {
            //수동으로 cancel 하려면 아래에 전달된 아이디 값을 알아야 한다.
            notify(currentId, builder.build())
        }
    }

}