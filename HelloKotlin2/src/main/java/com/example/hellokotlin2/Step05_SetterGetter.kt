package com.example.hellokotlin2

class Friend{
    // property ( field )
    var num:Int=0
    // null 을 허용하는 필드에 초기값 null 대입
    var name:String?=null
        set(value) {
            println("name 의 setter 메소드 호출됨")
            field=value+"님"
        }
    // null 을 허용하는 필드에 초기값 null 대입
    var phone:String?=null
        set(value) {
            field = "${name} 의 전화 번호는 ${value}"
        }
        get() {
            println("phone 의 getter 메소드 호출됨")
            return field ?: "전화번호 없음"
        }
}

fun main(){
    val f1=Friend()
    val a=f1.num  //값을 불러오는것은 내부적으로 getter 메소드가 호출된다.
    f1.num=1 //값을 넣어주는 것은 내부적으로 setter 메소드가 호출된다.
    f1.name="김구라" // name 의 setter 호출됨
    val b=f1.phone // phone 의 getter 호출됨
    f1.phone="010-1111-2222" // phone 의 setter 호출됨
}