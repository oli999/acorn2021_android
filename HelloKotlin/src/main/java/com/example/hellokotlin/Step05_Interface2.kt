package com.example.hellokotlin

interface Computer{
    fun calc()
    }

/*
        in  java  =>  implements Computer
        in  kotlin =>  : Computer
 */
class MyComputer : Computer{
    override fun calc() {
       println("내 컴퓨터가 무언가를 계산해요")
    }
}

fun main(){
    //MyComputer 객체의 다형성 확인
    /*
        in java => Object
        in kotlin => Any
     */
    val c1:Any = MyComputer()
    val c2:Computer = MyComputer()
    val c3:MyComputer = MyComputer()
    /*
        in java

        Computer c4=new Computer(){
            @Override
            public void calc(){

            }
        }
     */
    val c4=object:Computer{
        override fun calc() {
            println("익명 클래스로 만든 객체에서 계산해요!")
        }
    }
    c4.calc()

}