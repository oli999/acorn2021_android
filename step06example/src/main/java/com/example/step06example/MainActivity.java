package com.example.step06example;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    //Spinner 에 출력할 내용을 String[] 배열에 준비하기
    String[] names={"김구라", "해골", "원숭이"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner 의 참조값 얻어오기
        Spinner spinner=findViewById(R.id.spinner);

        //Spinner 에 연결할 아답타 객체
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner 에 아답타 연결하기
        spinner.setAdapter(adapter);
        //Spinner 에 아이템을 선택했는지 감시할 리스너 등록
        spinner.setOnItemSelectedListener(this);
    }
    //리스너를 등록한 UI 에 아이템 select 이벤트가 일어나면 호출되는 메소드
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
            position 은 선택한 아이템의 인덱스가 들어 있다.
         */
        Toast.makeText(this, names[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}








