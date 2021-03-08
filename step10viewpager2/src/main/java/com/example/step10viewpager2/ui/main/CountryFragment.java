package com.example.step10viewpager2.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.step10viewpager2.CountryDto;
import com.example.step10viewpager2.R;


public class CountryFragment extends Fragment {
    //필드
    CountryDto dto;
    //자신의 객체 참조값을 생성해서 리턴해주는 static 메소드
    public static CountryFragment newInstance(CountryDto dto) {
        //프레그먼트를 생성해서
        CountryFragment fragment = new CountryFragment();
        //번들 객체에 CountryDto 를 dto 라는 키값으로 담아서
        Bundle bundle=new Bundle();
        bundle.putSerializable("dto", dto);
        //프레그먼트의 인자로 전달하고
        fragment.setArguments(bundle);
        //프레그먼트를 리턴해 준다.
        return fragment;
    }
    //프레그 먼트가 활성화 될때 가장 첫번째로 호출되는 메소드
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //무언가 초기화 작업을 하기에 적당한 위치이다.
        //1. 인자로 전달된 번들 객체의 참조값을 얻어와서
        Bundle bundle=getArguments();
        //2. 번들에 dto 라는 키값으로 담긴 CountryDto 객체를 얻어내서 필드에 저장
        dto=(CountryDto) bundle.getSerializable("dto");
    }
    //onCreate() 메소드가 호출된 이후에 호출되는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프레그먼트의 레이아웃 xml 을 전개해서 View 를 만들어서
        View view=inflater.inflate(R.layout.fragment_country, container,
                false);
        //View 안에서 필요한 UI 의 참조값을 얻어와서
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textView=view.findViewById(R.id.textView);
        //이미지와 텍스트를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getContent());
        //이미지와 텍스트가 출력된 View 를 리턴해준다.
        return view;
    }
}