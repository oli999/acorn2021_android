package com.example.step12gameview;

public class Enemy {
    public int x, y; //적기의 좌표
    public int type; //적기의 type 0 or 1
    public boolean isDead;//배열에서 제거할지 여부
    public int energy;//에너지
    public boolean isFall; //현재 추락하고 있는지 여부
    public int angle; //회전각
    public int size; //크기
    public int imageIndex; //적기의 이미지 인덱스 (애니메이션 효과를 주기위해)
    //생성자
    public Enemy(int x, int y, int type, int energy, int size) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.energy = energy;
        this.size = size;
    }
}
