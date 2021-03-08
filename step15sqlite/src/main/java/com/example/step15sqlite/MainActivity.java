package com.example.step15sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity
                                implements View.OnClickListener,
                                AdapterView.OnItemLongClickListener {
    //필요한 필드 정의하기
    DBHelper helper;
    EditText inputText;
    TodoAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // new DBHelper( Context type, 생성될 DB 파일의 이름, 팩토리, 버전)
        // 어떤 로직에 의해서 version 값이 증가되면 DBHelper 객체의 OnUpgrade() 메소드가 호출된다.
        helper=new DBHelper(this, "MyDB.sqlite", null, 1);
        //UI 의 참조값을 얻어와서 기본 동작을 준비한다.
        inputText=findViewById(R.id.inputText);
        Button addBtn=findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        //todo 테이블에 있는 content 를 SELECT 해서 ListView 에 출력해 보세요.

        //할일 목록 얻어오기
        List<TodoDto> list=new TodoDao(helper).getList();
        //ListView 에 연결할 아답타
        adapter=new TodoAdapter(this, R.layout.listview_cell, list);
        //ListView 에 아답타 연결하기
        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //아이템 롱 클릭 리스너 등록
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //1. 입력한 문자열을 읽어와서
        String msg=inputText.getText().toString();
        //2. TodoDto 객체에 담아서
        TodoDto dto=new TodoDto();
        dto.setContent(msg);
        //3. TodoDao 객체를 이용해서 저장한다.
        new TodoDao(helper).insert(dto);
        //4. 업데이트된 할일 목록을 얻어와서
        List<TodoDto> list=new TodoDao(helper).getList();
        //5. 아답타에 넣어주고
        adapter.setList(list);
        //6. 모델의 내용이 바뀌었다고 아답타에 알려서 ListView 를 업데이트 한다.
        adapter.notifyDataSetChanged();
        //7. 최근 추가된 내용이 보일수 있도록
        listView.smoothScrollToPosition(adapter.getCount());
        
        Toast.makeText(this, "저장했습니다.", Toast.LENGTH_SHORT).show();
        inputText.setText("");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //position 은 long 클릭한 인덱스이고, id 는 해당 아이템의 primary key 이다.
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제 하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TodoDao dao=new TodoDao(helper);
                        //long 을 int 로 casting
                        int num=(int)id;
                        //DB 에서 삭제
                        dao.delete(num);
                        //새로 목록을 얻어와서
                        List<TodoDto> list=dao.getList();
                        //아답타에 전달하고
                        adapter.setList(list);
                        //UI 업데이트 하기
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("아니요", null)
                .create()
                .show();
        return false;
    }
}









