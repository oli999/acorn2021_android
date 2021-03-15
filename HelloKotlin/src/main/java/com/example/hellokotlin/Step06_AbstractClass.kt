package com.example.hellokotlin

//추상 클래스
abstract class Weapon{
    fun move(){
        println("이동 합니다.")
    }
    abstract fun attack()
}
/*
    in java  =>  extends Weapon
    in koltin =>  : Weapon()
 */
class MyWeapon:Weapon(){
    override fun attack() {
        println("무언가를 공격해요!")
    }
}

fun main(){
    val w1=MyWeapon()
    w1.move()
    w1.attack()

    val w2=object:Weapon(){ //클래스 상속은 () 가 있어야 된다.
        override fun attack() {
            println("아무나 공격해요!")
        }
    }
    w2.move()
    w2.attack()
}