package com.example.hellokotlin

fun main(){
    /*
        in  java =>  Map<String, Object> map=new HashMap<>();
                     map.put("num", 1);
                     map.put("name", "김구라")
                     map.put("isMan", true)
     */
    //수정 불가능한 Map
    val map=mapOf("num" to 1, "name" to "김구라", "isMan" to true)
    //Map 에 저장된 데이터 참조하는 방법1
    val num=map.get("num")
    val name=map.get("name")
    val isMan=map.get("isMan")

     //Map 에 저장된 데이터 참조하는 방법2
    val num2=map["num"]
    val name2=map["name"]
    val isMan2=map["isMan"]
}






