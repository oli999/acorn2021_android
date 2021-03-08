package com.example.step12customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    //색상을 나타내는 상수값을 미리 int[] 에 준비 하고
    int[] colors={Color.GREEN, Color.RED, Color.BLUE};
    //인덱스로 사용할 필드
    int index;

    //생성자1
    public MyView(Context context) {
        super(context);
    }
    //생성자2 (layout xml 문서에서 사용하면 아래의 생성자가 호출된다)
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //View 가 차지하고 있는 화면에 그림 그리기
    @Override
    protected void onDraw(Canvas canvas) {
        //Canvas 객체를 이용해서 원하는 작업을 한다.
        canvas.drawColor(colors[index]);
        //BitmapFactory 클래스의 static 메소드를 이용해서 이미지 로딩해서
        //Bitmap type 으로 만들기
        Bitmap image=BitmapFactory
                .decodeResource(getResources(), R.drawable.austria);
        //Bitmap 의 크기를 변환하기
        Bitmap scaledImage=Bitmap
                .createScaledBitmap(image, 100, 100, false);
        //Canvas 객체를 이용해서 이미지의 좌상단의 좌표를 지정하고 그린다.
        canvas.drawBitmap(scaledImage, 100, 100, null);
        //글자를 출력하기 위한 Paint 객체
        Paint textP=new Paint();
        textP.setColor(Color.YELLOW);
        textP.setTextSize(100);
        //Canvas 객체를 이용해서 글자의 좌하단의 좌표를 지정하고 그린다.
        canvas.drawText("안녕!", 100, 10, textP);
    }
    //View 에 터치 이벤트가 발생했을때 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //인덱스를 1 증가 시키고
        index++;
        //만일 없는 인덱스라면
        if(index==3){
            index=0; // 0 으로 초기화 하기
        }
        //화면 갱신하기 (결과적으로 View 가 무효화 되고 onDraw() 가 다시 호출)
        invalidate();
        return super.onTouchEvent(event);
    }
}
