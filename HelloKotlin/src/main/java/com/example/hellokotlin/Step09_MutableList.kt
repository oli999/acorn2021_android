package com.example.hellokotlin

fun main(){
    //수정 가능한 List
    val nums= mutableListOf(10, 20, 30)
    val names= mutableListOf("김구라", "해골", "원숭이")

    //빈 List 를 만들어서
    val animals= mutableListOf<String>()
    //나중에 데이터 추가
    animals.add("cat")
    animals.add("dog")
    animals.add("elephant")

    //값에 의한 삭제
    nums.remove(20)
    names.remove("해골")
    //인덱스에 의한 삭제
    animals.removeAt(1)
}