package com.example.step24fileupload;

public class ImageDto {
    private int num;
    private String writer;
    private String caption;
    private String imageUrl;
    private String regdate;
    //디폴트 생성자
    public ImageDto(){}

    public ImageDto(int num, String writer, String caption, String imageUrl, String regdate) {
        this.num = num;
        this.writer = writer;
        this.caption = caption;
        this.imageUrl = imageUrl;
        this.regdate = regdate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
}
