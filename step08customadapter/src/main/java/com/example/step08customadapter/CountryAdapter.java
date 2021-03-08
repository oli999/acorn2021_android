package com.example.step08customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
    ListView 에 연결할 아답타 클래스 정의하기

    - BaseAdapter 추상 클래스를 상속 받아서 만든다.
 */
public class CountryAdapter extends BaseAdapter {
    //필드
    Context context;
    int layoutRes;
    List<CountryDto> list;
    //생성자
    public CountryAdapter(Context context, int layoutRes,
                          List<CountryDto> list){
        //생성자의 인자로 전달된 값을 필드에 저장한다.
        this.context=context;
        this.layoutRes=layoutRes;
        this.list=list;
    }
    //모델의 아이템 갯수를 리턴한다.
    @Override
    public int getCount() {
        return list.size();
    }
    //인자로 전달된 position 인덱스에 해당하는 아이템 리턴
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    //인자로 전달된 position 인덱스에 해당하는 아이템의 아이디(PK) 가
    //있으면 리턴 , 없으면 그냥 position 을 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
    //인자로 전달된 position 에 해당하는 cell view 를 만들어서 리턴하거나
    //이미 만들어지 cell view 의 내용만 만들어서 리턴해 준다.
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.e("CountryAdapter", "getView() 호출됨 position:"+position);
        //1. res/layout/listview_cell.xml 문서를 전개해서 View 객체를 만든다.

        if(view == null){
            Log.e("CountryAdapter", "view 가 null 이여서 cell view 를 새로 만듭니다.");
            //레이아웃전개자(레이아웃 xml 문서를 이용해서 View 를 만드는객체 ) 객체의 참조값 얻어오기
            LayoutInflater inflater=LayoutInflater.from(context);
            //listview_cell.xml 문서를 전개해서 새로운 View 를 만든다.
            view=inflater.inflate(R.layout.listview_cell, parent, false);
        }
        //2. position 에 해당하는 CountryDto 객체의 참조값을 얻어온다.
        CountryDto dto=list.get(position);
        //3. 만든 View 객체 안에 있는 ImageView, TextView 의 참조값을 얻어온다.
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textView=view.findViewById(R.id.textView);
        //4. ImageView, TextView 에 정보를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getName());
        //5. View 객체를 리턴해 준다.
        return view;
    }
}
