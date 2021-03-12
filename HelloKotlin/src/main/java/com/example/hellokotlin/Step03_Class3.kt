package com.example.hellokotlin

class Cat(){//대표 생성자는 반드시 호출되어야 하는 생성자
    // init 블럭은 대표 생성자의 일부이다
    init{
        println("init!")
    }

    // ? 는 null 을 허용하는 선언
    var name:String?=null
    constructor(name:String):this(){ // :this() 는 대표 생성자 호출
        this.name=name
        println("secondary 생성자")
    }
}

fun main(){
    var c1=Cat()
    var c2=Cat("톰캣")
}