package com.example.step12gameview;

public class Missile {
    //필드
    public int x;
    public int y;
    //화면에서 제거할지 여부
    public boolean isDead=false;
    //생성자(미사일의 초기 좌표를 생성자로 전달받아서 필드에 저장)
    public Missile(int x, int y){
        this.x=x;
        this.y=y;
    }
}
