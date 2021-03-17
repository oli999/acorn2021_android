package com.example.hellokotlin2

// 폭과 높이를 생성자의 인자로 전달받는 Rect 클래스
class Rect(val width:Int, val height:Int){
    var isSquare:Boolean=false
        /*
        get() {
            return width==height
        }
        */
        get() = width==height  //위의 코드를 좀더 간략히 하면
}

fun main(){
    val r1=Rect(100, 200)
    val r2=Rect(200, 100)
    val r3=Rect(100, 100)

    println("r1 의 정사각형 여부:${r1.isSquare}")
    println("r2 의 정사각형 여부:${r2.isSquare}")
    println("r3 의 정사각형 여부:${r3.isSquare}")
}