package com.example.hellokotlin2


// null 을 허용하고 String type 을 전달받는 함수
fun greet(msg:String?){
    /*
        ?:  는  Elvis 연산자이다.
        -해당연산자의 좌측이 null 이면 연산자의 우측에 있는 값이 사용되거나
        우측에 있는 코드가 수행 됩니다.
     */
    val result = msg ?: "없습니다."
    println("오늘의 인사: ${result}")
}

fun main(){
    greet("안녕하세요")
    greet(null)
}