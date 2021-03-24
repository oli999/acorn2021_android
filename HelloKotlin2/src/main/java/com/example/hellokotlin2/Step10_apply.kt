package com.example.hellokotlin2

class Kimgura{
    var name:String?=null
    fun prepare(){
        println("준비해요")
    }
    fun eat(){
        println("먹어요")
    }
    fun play(what:String){
        println("$what 과 함께 놀아요")
    }
}

fun main(){
    val k1 = Kimgura()
    k1.name="이정호"
    k1.prepare()
    k1.eat()
    k1.play("아무게")

    val k2 = Kimgura().apply {
        name="해골"
        prepare()
        eat()
        play("아무게")
    }

    // Kimgura() 의 참조값을 가지고(with) 여러 작업을 {} 블럭안에서 하기
    with(Kimgura()){
        name="원숭이"
        prepare()
        eat()
        play("아무나")
    }
}



