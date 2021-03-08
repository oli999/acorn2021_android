package com.example.mygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class BeerView extends View {
    //뷰의 크기를 저장할 필드
    int viewWidth, viewHeight;
    //맥주병 이미지를 저장할 필드
    Bitmap bottleImg;
    //맥주병의 크기
    int bottleWidth, bottleHeight;
    //MyView 의 가운데 좌표
    int centerX, centerY;
    //canvas 의 회전각도
    int angle=0;
    //action down 이 일어난곳의 좌표를 저장할 필드
    int downX, downY;
    //action down 이 일어난 시간을 저장할 필드
    long downTime;
    //맥주병의 회전 각속도
    int angleSpeed;
    //상태값을 관리할 필드
    boolean isRotating=false;
    //트릭 모드인지 여부
    boolean isTrick=false;
    //트릭 모드 횟수
    int trickCount=0;
    //사운드 메니저를 필드에
    Util.SoundManager sManager;
    //필요한 static final 상수 정의하기
    static final int SOUND_BIRDDIE=0;
    static final int SOUND_SHOOT=1;

    //생성자
    public BeerView(Context context) {
        super(context);
    }

    public BeerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //View 의 사이즈를 전달 받을 메소드
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth=w;
        viewHeight=h;
        //비트맵 이미지 로딩하기(원본크기)
        bottleImg= BitmapFactory.decodeResource(getResources(), R.drawable.bottle);
        //크기 조절
        bottleImg= Bitmap.createScaledBitmap(bottleImg,
                300, 600, false);
        //로딩된 이미지의 폭과 높이
        bottleWidth=bottleImg.getWidth();
        bottleHeight=bottleImg.getHeight();
        //View 의 정중앙 설정
        centerX=viewWidth/2;
        centerY=viewHeight/2;
        //핸들러에 지연된 메세지 보내기
        handler.sendEmptyMessageDelayed(0, 10);
        //사운드 재생 준비
        //사운드 메니저의 참조값 얻어와서
        sManager=Util.SoundManager.getInstance();
        //준비 작업을 하고
        sManager.init(getContext());
        //필요한 사운드를 로딩 시킨다.
        sManager.addSound(SOUND_BIRDDIE, R.raw.birddie);
        sManager.addSound(SOUND_SHOOT, R.raw.shoot1);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.YELLOW);
        //캔바스 회전 시키기
        canvas.rotate(angle, centerX, centerY);

        canvas.drawBitmap(bottleImg,
                centerX-bottleWidth/2,
                centerY-bottleHeight/2, null);
        if(angleSpeed>0) {//회전 중인 경우
            //각속도 만큼 회전 각도를 반영한다.
            angle += angleSpeed;
            //각속도를 1씩 감소 시키기
            angleSpeed--;

            if(angleSpeed==80 && isTrick) {
                angle = 0 + 90*trickCount;
                isTrick=false;// 트릭 모드 해제
                trickCount++;
            }

        }else{//회전이 끝난경우
            if(isRotating){
                //birddie.mp3 효과음을 재생해 보세요.
                sManager.play(SOUND_BIRDDIE);
            }
            isRotating=false;
            angleSpeed=0;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            handler.sendEmptyMessageDelayed(0, 10);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isRotating){//만일 회전 상태이면
            return false;//여기서 메소드 종료하기
        }

        //이벤트가 일어난 곳의 좌표
        int x=(int)event.getX();
        int y=(int)event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //액션 다운 이벤트가 일어난 곳의 좌표와 시간을 필드에 저장하기
                downX=x;
                downY=y;
                downTime=System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                long upTime=System.currentTimeMillis();
                // action_down 이벤트와 action_up 이벤트가 일어나는데 걸린시간
                long deltaT=upTime-downTime;
                //손가락의 속도 구하기
                double fingerSpeed=
                        Math.sqrt(Math.pow((x-downX) , 2)
                                +Math.pow((y-downY), 2))/deltaT;
                //맥주병의 회전 각속도 부여하기
                angleSpeed=(int)fingerSpeed*50;
                //회전 시작이라 표시
                isRotating=true;

                //만일 좌하단 영역을 터치 했을때는 트릭 모드로 바꾼다.
                if(x < 100 && y > viewHeight-100){
                    isTrick=true;
                }

                break;
        }
        return true;
    }
}











