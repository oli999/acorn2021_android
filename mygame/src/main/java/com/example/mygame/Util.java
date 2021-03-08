package com.example.mygame;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class Util {
    //효과음을 재생하기 위한 클래스
    public static class SoundManager {
        //필요한 필드 정의하기
        private static SoundManager sManager;
        private SoundPool soundPool;
        private HashMap<Integer,Integer> map;
        private Context context;
        //싱글톤 페턴
        private SoundManager(){}
        public static SoundManager getInstance(){
            if(sManager==null){
                sManager=new SoundManager();
            }
            return sManager;
        }
        //초기화 하기
        public  void init(Context context){
            this.context=context;
            //필요한 객체를 전달해서 SoundPool 객체를 생성한다.
            soundPool=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            //사운드 리소스 id 값을 저장할 HashMap 객체 생성하기
            map=new HashMap<Integer, Integer>();
        }
        //음원을 추가하는 메소드
        public void addSound(int index, int resId){
            //인자로 전달된 resId 값을 이용해서 사운드를 로딩 시키고 재생할 준비를 한다.
            int id=soundPool.load(context, resId, 1);
            //등록하고 리턴되는 아이디를 맵에 저장한다.
            map.put(index, id);
        }
        //음원을 재생하는 메소드
        public void play(int index){
            //인자로 전달된 인덱스 값을 이용해서 해당 음원을 재생하도록한다.
            soundPool.play(map.get(index), 1, 1, 1, 0, 1);

        }
        //음원 재생을 중지하는 메소드
        public void stopSound(int index){
            //인자로 전달된 인덱스 값을 이용해서 해당 음원을 정지 시킨다.
            soundPool.stop(map.get(index));
        }
    }
}
