package com.example.step24fileupload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    1. 웹서버에 이미지 목록 정보를 요청한다.

    2. 웹서버가 이미지 목록 정보를 JSON 문자열로 응답한다.

    3. 웹서버가 응답한 JSON 문자열을 파싱해서

    4. ListView 의 각각의 cell 에 이미지와 이미지 정보를 출력한다.
 */
public class GalleryListActivity extends AppCompatActivity
        implements Util.RequestListener, View.OnClickListener{

    public static final String IMAGELIST_URL="http://192.168.0.17:8888/spring05/gallery/list_android.do";;
    private ListView listView;
    private ImageAdapter adapter;
    private List<ImageDto> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_list);

        listView=findViewById(R.id.listView);
        list=new ArrayList<>();
        adapter=new ImageAdapter(this, R.layout.listview_cell, list);
        listView.setAdapter(adapter);

        Button takePicBtn=findViewById(R.id.takePicBtn);
        Button refreshBtn=findViewById(R.id.refreshBtn);

        takePicBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Util.sendGetRequest(0, IMAGELIST_URL, null, this);
    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        //모델 클리어
        list.clear();

        String data=(String)result.get("data");
        try{
            JSONArray arr=new JSONArray(data);
            for(int i=0; i<arr.length(); i++){
                JSONObject obj=arr.getJSONObject(i);
                int num=obj.getInt("num");
                String writer=obj.getString("writer");
                String imagePath=obj.getString("imagePath");
                String regdate=obj.getString("regdate");
                //ImageDto 객체를 생성해서 이미지 정보를 담는다.
                ImageDto dto=new ImageDto();
                dto.setNum(num);
                dto.setImageUrl("http://192.168.0.17:8888/spring05"+imagePath);
                dto.setWriter(writer);
                dto.setRegdate(regdate);
                //ImageDto 객체를 List 에 추가
                list.add(dto);
            }
            adapter.notifyDataSetChanged();
        }catch (JSONException je){
            Toast.makeText(this, je.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        Toast.makeText(this, "실패!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePicBtn:
                //사진 찍는 액티비티로 이동하기
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.refreshBtn:
                //다시 목록 받아오기
                Util.sendGetRequest(0, IMAGELIST_URL, null, this);
                break;
        }
    }

}