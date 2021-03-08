package com.example.step08customadapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //액티비티에 전달된 인텐트 객체의 참조값 얻어오기 (MainActivity 에서 생성한)
        Intent intent=getIntent();
        //인텐트에 담긴 CountryDto 의 참조값 얻어오기
        CountryDto dto=(CountryDto) intent.getSerializableExtra("dto");
        // activity_detail.xml 을 전개했을때 생성되는 UI 의 참조값 얻어오기
        ImageView imageView=findViewById(R.id.imageView);
        TextView textView=findViewById(R.id.textView);
        Button confirmBtn=findViewById(R.id.confirmBtn);
        //ImageView, TextView 에 필요한 정보 출력하기
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getContent());
        //버튼에 리스너를 익명 클래스를 이용해서 등록하기
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //액티비티 종료하기
                //DetailActivity.this.finish();
                finish();
            }
        });
    }
}






