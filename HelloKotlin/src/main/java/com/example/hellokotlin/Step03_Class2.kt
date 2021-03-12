package com.example.hellokotlin

class MemberDto(var num:Int, var name:String, var addr:String)

fun main(){
    var mem1=MemberDto(1, "김구라", "노량진")
    var mem2=MemberDto(2, "해골", "행신동")
    var mem3=MemberDto(3, "원숭이", "상도동")

    var members= mutableListOf<MemberDto>()
    members.add(mem1)
    members.add(mem2)
    members.add(mem3)

    for(i in members.indices){
        var tmp=members.get(i)
        println("${tmp.num} | ${tmp.name} | ${tmp.addr}")
    }
}