package com.example.step07listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {
    //필요한 필드 정의하기
    List<String> names;
    int position;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ListView 의 참조값 얻어오기
        ListView listView=findViewById(R.id.listView);
        //ListView 에 출력할 모델(데이터)
        names=new ArrayList<>();
        names.add("김구라");
        names.add("해골");
        names.add("원숭이");
        for(int i=0; i<100; i++){
            names.add("주뎅이"+i);
        }
        //ListView 에 연결할 아답타 객체
        // new ArrayAdapter<>( Context , layout resource , 모델 )
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                names);
        //ListView 에 아답타 연결하기
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }
    //ListView 의 특정 cell 을 클릭하면 호출되는 메소드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // View 는 cell view 의 참조값, position 은 클릭한 아이템 인덱스
        //클릭한 셀에 사용된 데이터 얻어내기
        String name=names.get(position);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
    //ListView 의 셀을 오랬동안 클릭하면 호출되는 메소드
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //지역변수 position 에 전달된 값을 필드 position 에 대입하기
        this.position=position;
        String name=names.get(position);

        //알림창 띄우기
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+" 을 삭제 하시겠습니까?")
                .setNegativeButton("아니요", null)
                .setPositiveButton("네", this)
                .create()
                .show();

        return false;
    }
    //알림창에 "네" 버튼을 누르면 호출되는 메소드
    @Override
    public void onClick(DialogInterface dialog, int which) {
        //필드에 저장된 값을 이용해서 모델에서 해당 인덱스를 삭제
        names.remove(position);
        //모델의 내용이 수정되었다고 아답타에 알리기
        //결과적으로 아답타가 ListView 에 수정된 cell view 를 다시 공급해서 ListView 가 업데이트된다.
        adapter.notifyDataSetChanged();
    }
}





