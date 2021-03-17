package com.example.hellokotlin2

fun main(){
    var names= listOf("김구라","해골","원숭이","주뎅이","덩어리")

    for(i in 0..4){
        val tmp=names[i]
        println(tmp)
    }
    println("-----")
    for(i in 0..names.size-1){
        val tmp=names[i]
        println(tmp)
    }
    println("-----")
    for(tmp in names){
        println(tmp)
    }
    println("-----")
    for(i in 0 until names.size){ // 0~5 미만
        val tmp=names[i]
        println(tmp)
    }
    println("-----")
    for((index, value) in names.withIndex()){
        println("$index 번째 인덱스의 item 은 $value")
    }
    println("-----")
    for(num in 0..10 step 2){
        println(num)
    }
    println("-----")
    for(num in 10 downTo 0){
        println(num)
    }
    println("-----")
    for(num in 10 downTo 0 step 2){
        println(num)
    }
    println("-----")
    for(i in names.size-1 downTo 0){
        var tmp=names[i] // i 값은 4, 3, 2, 1, 0
        println(tmp)
    }
}