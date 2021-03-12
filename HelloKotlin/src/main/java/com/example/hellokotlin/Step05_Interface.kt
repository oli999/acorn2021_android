package com.example.hellokotlin

//인터페이스
interface Remocon{
    fun up()
    fun down()
}

//인터페이스를 구현한 클래스
class TvRemocon : Remocon{// in java  implements Remocon
    override fun up() {
        println("체널을 올려요")
    }

    override fun down() {
        println("체널을 내려요")
    }

    //TvRemocon 에서 정의된 메소드
    fun off(){
        println("TV 를 꺼요")
    }
}

fun main(){
    //r1 의 type 을 생략해서 묵시적으로 TvRemocon type 으로 만들기
    val r1=TvRemocon()
    //r2 의 type 을 명시적으로 TvRemocon type 으로 만들기
    val r2:TvRemocon= TvRemocon()
    //r3 의 type 을 명시적으로 Remocon interface 으로 만들기
    val r3:Remocon=TvRemocon()

    r3.up()
    r3.down()
    //r3.off()  r3 는 Remocon type 이기때문에 호출 불가
    val r4:TvRemocon = r3 as TvRemocon  // type casting
    r4.off()
}