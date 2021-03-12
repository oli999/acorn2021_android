package com.example.hellokotlin

// run 했을때 실행순서가 시작 되는 main 함수
fun main(){
    //숫자 type
    var a:Double
    var b:Float
    var c:Long
    var d:Int  // java 에서는 Integer 에 해당하는 type
    var e:Short
    var f:Byte
    //숫자 대입
    a=10.1
    b=10.2f
    c=1000000
    d=50
    e=10
    f=16

    //Char type
    var ch1:Char
    ch1='가'
    var ch2:Char='나'
    var ch3='다'

    //String type
    var myName:String
    myName="김구라"
    var yourName:String="해골"
    var ourName="원숭이"

    //Array

    /*
         in java

         int[] nums={10,20,30,40,50};
     */
    var nums=arrayOf(10,20,30,40,50)
    var num1=nums[0] //0번방 참조
    var num2=nums[1] //1번방 참조
    var size=nums.size //크기 참조
    nums[0]=100 //0번방 수정

    //모든 type 표현을 다 표시하면 아래와 같다
    var nums2:Array<Int> = arrayOf<Int>(10,20,30)
    //문자열 배열
    var animals:Array<String> = arrayOf<String>("cat","dog","elephant")

    /*
        Collection

        in  java  List

        kotlin 에서는 수정가능한 List 와 수정 불가능한 List (readonly) 가 있다.
     */
    var list1= listOf<String>("김구라","해골","원숭이")

    // 아래의 한줄이  java 에서  List<String> list2=new ArrayList<String>(); 과 같다
    var list2= mutableListOf<String>("김구라","해골","원숭이")
    
    // 0 번방을 참조하는 두가지 방법!
    var name1=list1[0]
    var name2=list1.get(0)
    
    var listSize=list1.size //방의 크기 참조

    //list1[0]="덩어리"  //수정불가
    list2[0]="덩어리" //수정가능
    list2.add("덩어리") //아이템 추가 가능
    //list1.add("덩어리") //아이템 추가 불가

    //Range 범위
    var range1 = 1..5
    var range2 = 10..20

    for(i in range1){
        println(i)
    }
}