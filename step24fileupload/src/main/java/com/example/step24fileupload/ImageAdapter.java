package com.example.step24fileupload;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ImageDto> list;
    private int layoutRes;
    private Context context;

    //생성자
    public ImageAdapter(Context context,int layoutRes,List<ImageDto> list){
        this.layoutRes=layoutRes;
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getNum();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view=inflater.inflate(layoutRes, viewGroup, false);
        }
        ImageDto dto=list.get(i);
        TextView textWriter=view.findViewById(R.id.textWriter);
        TextView textRegdate=view.findViewById(R.id.textRegdate);
        ImageView imageView=view.findViewById(R.id.imageView);

        textWriter.setText("업로더 : "+dto.getWriter());
        textRegdate.setText("등록일 : "+dto.getRegdate());
        // Glide  를 이용해서 ImageView 에 이미지 출력하기
        Glide.with(context)
                .load(dto.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);

        return view;
    }
}
