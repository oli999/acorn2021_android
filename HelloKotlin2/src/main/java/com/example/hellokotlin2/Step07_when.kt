package com.example.hellokotlin2



fun main(){
    val c1=MyColor.RED

    /*
        in java => switch( ){
            case 값 :    break;
            case 값 :    break;
            .
            .
            default :
        }
        in kotlin => when( ){  }
     */
    val color=MyColor.GREEN
    when(color){
        MyColor.RED -> println("빨간색 이네요")
        MyColor.GREEN -> println("녹색 이네요")
        MyColor.BLUE -> {
            //실행할 코드가 여러줄이면 { } 로 묶어주면 된다.
            println("파란색 이네요")
        }
        else -> println("빨간색, 녹색, 파란색이 아니네요")
    }
}
