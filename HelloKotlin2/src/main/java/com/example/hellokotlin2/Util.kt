package com.example.hellokotlin2

class Util{
    // java 에서 static 에 해당되는 메소드와 필드는 어떻게 만들지?
    companion object{ //동반객체
        val version="1.0"
        // java 에서 static 자원처럼 사용가능하게 하기
        @JvmField
        val author="kimgura"
        fun upload(){
            println("upload() !")
        }
        // java 에서 static 자원처럼 사용가능하게 하기
        @JvmStatic
        fun download(){
            println("download() !")
        }
    }

    //참조값으로 호출하는 메소드
    fun test(){
        println("Util test()!")
    }
}

fun main(){
    // companion object 에 있는 필드나 메소드는 클래스명으로 참조, 호출가능
    Util.upload()
}