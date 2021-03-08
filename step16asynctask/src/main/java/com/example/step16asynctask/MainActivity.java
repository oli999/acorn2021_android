package com.example.step16asynctask;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity
            implements View.OnClickListener{
    TextView console;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 화면 구성하기
        setContentView(R.layout.activity_main);
        //버튼의 참조값 얻어오기
        Button sendBtn=findViewById(R.id.sendBtn);
        //리스너 등록하기
        sendBtn.setOnClickListener(this);
        //TextView 의 참조값 얻어오기
        console=findViewById(R.id.console);
        //버튼의 참조값 얻어오기
        Button counterBtn=findViewById(R.id.counterBtn);
        //리스너 등록하기
        counterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CounterTask task=new CounterTask();
                task.execute("김구라", "해골", "원숭이");
            }
        });
    }
    @Override
    public void onClick(View v) {
       /*
            시간이 오래 걸리거나 혹은 실행 시간이 불확실한 작업은
            Main thread (UI thread) 에서 하면 안된다.
        */

        //비동기 작업 클래스를 이용해서 객체 생성후
        SendTask task=new SendTask();
        // execute() 메소드를 호출하면 자동으로 새로운 스레드에서 작업을 하게 된다.
        task.execute("hello~", "one", "two", "three");
    }

    /*
         비동기 작업을 도와줄 클래스 설계하기
         1. AsyncTask 추상 클래스를 상속 받는다.
         2. AsyncTask<파라미터 type, 진행중 type, 결과 type>
            에 맞게끔 Generic 클래스를 잘 정의 한다.
         3. doInBackground() 메소드를 오버라이드한다.
         4. 추가로 필요한 메소드가 있으면 추가로 오버라이드 한다.
     */
    public class SendTask extends AsyncTask<String, Void, Void>{
        // SendTask 객체의 execute() 메소드를 호출하면 호출되는 메소드
        @Override
        protected Void doInBackground(String... strings) {
            //동적인 갯수의 인자 strings 는 String[] 이다
            //배열의 0번방에 첫번째 인자가 들어 있다.
            String first=strings[0];
            //여기가 새로운 스레드라고 생각하면 된다.
            Messenger.sendMessage(first);
            //여기는 새로운 스레드이기 때문에 UI 에 관련된 작업은 제약이 따른다.
            //작업이 끝났을때 UI 에 관련된 작업을 하고 싶으면
            //OnPostExecute() 메소드를 오버라이드 해서 그 안에서 작업하면 된다.
            return null;
        }
        //doInBackground() 메소드가 리턴하면 자동으로 호출되는 메소드
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //여기는 UI 스레드이기 때문에 UI 에 관련된 작업을 마음대로 할수 있다.
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("작업성공")
                    .create()
                    .show();
        }
    }

    public class CounterTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            //새로운 스레드
            String name1=strings[0]; //김구라
            String name2=strings[1]; //해골
            String name3=strings[2]; //원숭이
            // 1~20 사이의 랜덤한 정수를 얻어내서
            int ranNum=new Random().nextInt(20)+1;
            int count=0; //카운트를 셀 지역 변수
            //렌덤한 숫자를 엇어낸 만큼 반복문 돌기
            for(int i=0; i<ranNum; i++ ){
                count++;
                try {
                    //1 초씩 시간 딜레이
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                //여기서 현재 카운트값을 발행하기
                //결과적으로 onProgressUpdate() 메소드가 호출된다.
                this.publishProgress(count);
            }
            String result=count+" 까지 숫자를 다 세었습니다.";
            //비동기 작업의 결과를 리턴해주기
            //리턴된 데이터는 onPostExecute() 메소드가 호출되면서 인자로 전달된다.
            return result;
        }
        // doInBackground() 메소드가 호출되기 직전에 호출되는 메소드
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UI 스레드
            console.setText("숫자를 세기 시작합니다...");
        }
        // doInBackground() 가 리턴된 직후 호출되는 메소드
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //UI 스레드
            console.setText(s);
        }
        // publishProgress() 메소드를 호출할때 마다 호출되는 메소드
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //UI 스레드
            //인자로 전달된 정수값을 문자열로 바꿔서 TextView 에 출력
            console.setText(Integer.toString(values[0]));
        }
    }
}






