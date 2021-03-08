package com.example.step14sharedpref;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener,
                            CompoundButton.OnCheckedChangeListener {
    EditText editText;
    Switch isGood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        Button saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        //Switch 객체의 참조값을 필드에 저장
        isGood=findViewById(R.id.isGood);
        //체크 상태가 바뀌었을때 감시할 리스너 등록
        isGood.setOnCheckedChangeListener(this);

        //SharedPreference 를 이용해서 저장된 정보를 읽어오기
        SharedPreferences pref=getSharedPreferences("info", MODE_PRIVATE);
        // .getString(키값, 없을 경우 기본값)
        String msg=pref.getString("msg","");
        // EditText 에 저장하기
        editText.setText(msg);
        //isGood 이라는 키값으로 저장된 boolean 값 얻어오기
        boolean result=pref.getBoolean("isGood", false);
        //Switch 객체의 체크 상태에 반영한다.
        isGood.setChecked(result);
    }
    //우상단에 옵션 메뉴를 만들고 싶으면 오버라이드 하는 메소드
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //res/menu/menu_main.xml 문서를 전개해서 메뉴 구성하기
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //옵션 메뉴에 있는 아이템을 선택했을때 호출되는 메소드
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //선택한 메뉴의 아이디 읽어오기
        int id = item.getItemId();

        if (id == R.id.action_settings) { //만일 settings 메뉴를 선택했다면
            //SettingsActivity 로 이동하기
            Intent intent=new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //EditText 에 입력한 내용을 어딘가? 에 영구 저장하기
        //다음에 액티비티를 다시 실행 시켜도 읽어올수 있도록
        String msg=editText.getText().toString();

        //SharedPreferences 를 활용해서 저장하기
        SharedPreferences pref=getSharedPreferences("info", MODE_PRIVATE);
        //에디터 객체의 참조값 얻어오기
        SharedPreferences.Editor editor=pref.edit();
        //msg 라는 키값으로 입력한 문자열 저장하기
        editor.putString("msg", msg);
        editor.commit(); // commit() 시점에 실제로 저장된다.
        new AlertDialog.Builder(this)
                .setMessage("저장했습니다.")
                .setNeutralButton("확인", null)
                .create().show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences pref=getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        //Switch 의 체크된 상태를 SharedPreference 를 이용해서 저장한다.
        editor.putBoolean("isGood", isChecked);
        editor.commit();
    }
}







