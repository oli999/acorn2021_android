package com.example.hellokotlin2

// Weapon interface
interface Weapon{
    fun attack()
}

// Weapon type 을 전달받는 함수
fun useWeapon(w:Weapon){
    w.attack()
}

// function type 을 전달 받는 함수
fun useFunc(f:()->Unit){
    //인자로 전달 받은 함수 호출하기
    f()
}

fun main(){
    //1. useWeapon 함수 호출하기
    useWeapon(object : Weapon{
        override fun attack() {
            println("김구라를 공격해요")
        }
    })

    //2. useFunc 함수 호출하기
    useFunc(fun(){
        println("익명 함수 호출됨")
    })
    useFunc({
        println("익명 함수 호출됨2")
    })
    useFunc{
        println("익명 함수 호출됨3")
    }

}




