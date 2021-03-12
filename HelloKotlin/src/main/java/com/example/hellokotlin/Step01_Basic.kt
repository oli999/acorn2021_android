package com.example.hellokotlin
/*
    함수명 : sum
    함수에 전달하는 인자의 갯수 : 2개
    인자의 type : Int
    함수의 리턴 type : Int
 */
fun sum(a:Int, b:Int):Int{

    return a+b
}

fun main(){
    //sum 함수가 리턴하는 숫자를 콘솔창에 출력
    println(sum(10, 20))
    println(sum(5, 5))
    //변수를 만들때 사용하는 예약어 val  vs  var
    val a=10  // val 은 readonly(읽기전용, 값 수정 불가)
    var b=10  // var  read, write (값 수정 가능)
    //a=20; 수정 불가
    b=20 //수정 가능

    val c:Int //선언만 하고 값을 나중에 넣고 싶으면 type 을 지정해야 한다.
    c=20
    //c=30 //값이 결정되면 수정 불가

    var d:Int
    d=20
    d=30

    //값이 대입되면서 String type 으로 myName 이 만들어진다.
    val myName="김구라"
    //String type 으로 선언된 yourName 에 값 대입하기
    val yourName:String="해골"
    //String type ourName 변수 만들고
    val ourName:String
    //값을 나중에 대입하기
    ourName="원숭이"
}