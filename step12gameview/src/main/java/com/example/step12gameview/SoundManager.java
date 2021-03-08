package com.example.step12gameview;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

/*
    효과음을 필요한 시점에 재생하기 위한 클래스 설계
 */
public class SoundManager {
    //사운드의 아이디값(정수값) 을 저장하기 위한 Map
    Map<Integer, Integer> map=new HashMap<>();
    //SoundPool
    SoundPool pool;
    //Context
    Context context;
    //볼륨
    int streamVolume;

    //생성자
    public SoundManager(Context context){
        this.context=context;
        pool=new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //오디어 서비스 객체를 얻어와서
        AudioManager am=(AudioManager)
                   context.getSystemService(Context.AUDIO_SERVICE);
        //설정된 음악 볼륨값을 읽어와서 필드에 저장한다.
        streamVolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    //재생할 사운드 등록하는 메소드
    public void addSound(int key, int resId){
        //resId 를 이용해서 사운드를 로딩하고 아이디값을 리턴 받는다.
        int soundId=pool.load(context, resId, 1);
        //리턴 받은 아이디값을 인자로 전달된 키값으로 저장한다
        map.put(key, soundId);
    }
    //사운드를 재생하는 메소드
    public void playSound(int key){
        //인자로 전달받은 키값을 이용해서 Map 에서 재생할 사운드의 아이디를 읽어온다.
        int soundId=map.get(key);
        //재생하기
        pool.play(soundId, streamVolume, streamVolume, 1,0, 1);
    }
    public void stopSound(int key){
        int soundId=map.get(key);
        pool.stop(soundId);
    }
    public void pauseSound(int key){
        int soundId=map.get(key);
        pool.pause(soundId);
    }
    public void resumeSound(int key){
        pool.resume(map.get(key));
    }
    //자원 해제 하는 메소드
    public void release(){
        pool.release();
    }
}








