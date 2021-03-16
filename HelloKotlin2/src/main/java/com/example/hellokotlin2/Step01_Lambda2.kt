package com.example.hellokotlin2

fun main(){
    /*
        in java => public void a(){ }
        in kotlin => fun a():Unit{ }  or  fun a(){ }
        코틀린에서 Unit 은 원시 type 이라고 지칭하고 java 의 void 와 비슷한 역활을 한다.
     */
    fun a():Unit{
        println("a 함수 호출됨")
    }
    a()

    val b:Int = 10
    val c:String = "kim"
    /*
        ()->Unit 은
        1. 함수에 전달되는 인자는 없으며
        2. 아무값도 리턴하지 않는
        3. 함수 type 을 의미한다.
     */
    val d:()->Unit = fun(){
        println("d 함수 호출됨")
    }
    // fun() 생략 가능
    val e:()->Unit = {
        println("e 함수 호출됨")
    }
    d()
    e()
    /*
        (String)->String
        1. String type 인자를 하나 받아서
        2. String type 을 리턴해주는
        3. 함수 type
     */
    val f:(String)->String = {name -> "내이름은 ${name}" }
    // f 함수 호출하면서 "김구라" 를 전달하고 리턴되는 값을 result 라는 상수에 담기
    val result = f("김구라")
}