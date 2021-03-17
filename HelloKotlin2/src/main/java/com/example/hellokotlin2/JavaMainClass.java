package com.example.hellokotlin2;

public class JavaMainClass {
    //메인 메소드
    public static void main(String[] args) {
        System.out.println("main method started!");
        //참조값으로 호출하는 메소드
        Util u1=new Util();
        u1.test();
        //클래스명에 . 찍어서 호출하는 메소드

        // kotlin 클래스에 동반객체에 정의된 필드값과 메소드 사용하는 방법
        Util.Companion.upload();
        String version=Util.Companion.getVersion();

        //만일 Companion 을 사용하지 않고 static 자원처럼 사용하고 싶다면?
        Util.download();
        String author=Util.author;
    }
}
