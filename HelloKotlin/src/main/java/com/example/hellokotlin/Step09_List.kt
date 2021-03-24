package com.example.hellokotlin

fun main(){
    //수정 불가능한 List
    val nums= listOf(1,2,3,4,5)
    val names= listOf("김구라","해골","원숭이")

    //데이터 참조
    val num1=nums.get(0)
    val num2=nums.get(1)

    //데이터 참조2
    val num3=nums[0]
    val num4=nums[1]

}