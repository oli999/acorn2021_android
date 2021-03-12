package com.example.hellokotlin

/*
    클래스 선언시 기본값을 상속을 받지 못하게 되어 있다.
    마치 java 에서  final class Phone{ } 처럼...
    상속을 받을수 있게 하려면 open 이라는 예약어를 붙여 준다.
 */
open class Phone{
    fun call(){
        println("전화를 걸어요")
    }
}

open class HandPhone : Phone(){
    fun mobileCall(){
        println("이동 중에 전화를 걸어요")
    }
    //재정의 가능한 메소드로 만들려면 open 예약어를 붙여야 한다.
    open fun takePicture(){
        println("100만 화소의 사진을 찍어요")
    }
}

class SmartPhone : HandPhone(){
    fun doInternet(){
        println("인터냇을 해요!")
    }
    override fun takePicture() {
        //super.takePicture()
        println("1000만 화소의 사진을 찍어요")
    }
}


fun main(){
    val p1=Phone()
    val p2=HandPhone()
    p1.call()

    p2.call() //Phone 클래스로 부터 상속받은 메소드도 호출가능
    p2.mobileCall()
    p2.takePicture()

    val p3=SmartPhone()
    p3.call()
    p3.mobileCall()
    p3.doInternet()
    p3.takePicture()
}