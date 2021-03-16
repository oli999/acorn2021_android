package com.example.hellokotlin2

val nums = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

fun main(){

    // nums List에서 짝수만 찾아서 짝수로만 이루어진 List 를 만들어 내려면?

    //짝수만 담을 List 객체 만들기
    val nums2= mutableListOf<Int>()
    //반복문 돌면서 짝수만 찾아서 nums2 에 추가하기
    for(item in nums){
        if(item % 2 == 0){
            nums2.add(item)
        }
    }
    //결과
    println(nums2)

    // Collection 에 내장된 함수를 이용하면
    val nums3 = nums.filter({
        it % 2 == 0
    })

    /*
        1. filter( ) 는 Collection 내장 함수 이다.
        2. filter() 함수는 새로운 Collection 객체를 생성해서 리턴하는 함수이다.
        3. original Collection 에서 { } 안에 조건이 true 인 item 만 남겨서
           새로운 Collection 을 만들어서 리턴해준다.
        4. { } 안에 it 은 original Collection 에 저장된 각각의 아이템을 순서대로 지칭한다.
     */
    val nums4 = nums.filter { it % 2 == 0 }

    println(nums3)
    println(nums4)
}