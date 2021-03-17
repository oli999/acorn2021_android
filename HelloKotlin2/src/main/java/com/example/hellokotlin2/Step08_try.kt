package com.example.hellokotlin2

fun main(){
    var str = "1000"
    var str2 = "천"
    var result=0
    // try~catch 형식은 java 와 거의 같다
    try{
        result = Integer.parseInt(str2)
    }catch (nfe : NumberFormatException){
        nfe.printStackTrace()
    }
    println("result:"+result)
}