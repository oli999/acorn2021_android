package com.example.hellokotlin

class Car{
    fun drive(){
        println("달려요!")
    }
}

class Util{
    // 클래스명에 . 찍어서 사용할수 있는 동반객체 만들기
    companion object{
        val color="RED"
        fun write(){
            println("필기를 해요")
        }
        fun writeByColor(){
            println("${color} 색으로 필기를 해요")
        }
    }
}
/*
    in java =>  private MyDao(){ }
    in kotlin => private constructor()
 */
class MyDao private constructor(){
    // single ton 으로 MyDao 를 사용할수 있게 companion object 정의
    companion object{
        private val dao=MyDao()
        fun getInstance():MyDao{
            return dao
        }
    }

    fun insert(){
        println("추가합니다.")
    }
    fun update(){
        println("수정합니다.")
    }
}

fun main(){
    val c1=Car()
    c1.drive()
    //Util 클래스에 정의된 companion object 사용하기
    val a=Util.color
    Util.write()
    Util.writeByColor()
    //single ton dao 객체의 참조값 얻어와서 사용하기
    val dao=MyDao.getInstance()
    dao.insert()
    dao.update()
}