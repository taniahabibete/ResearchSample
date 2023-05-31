package com.example.researchsample;

public class Booklist {
    private String vProductName;
    private String vAuthorName;
    private String vImage1;
    private int iAutoId;

    public Booklist(String bookName, String bookDescription, String bookImage, int iAutoId) {
        this.vProductName = bookName;
        this.vAuthorName = bookDescription;
        this.vImage1 = bookImage;
        this.iAutoId = iAutoId;
    }

    public String getvProductName() {
        return vProductName;
    }

    public void setvProductName(String vProductName) {
        this.vProductName = vProductName;
    }

    public String getvAuthorName() {
        return vAuthorName;
    }

    public void setvAuthorName(String vAuthorName) {
        this.vAuthorName = vAuthorName;
    }

    public String getvImage1() {
        return vImage1;
    }

    public void setvImage1(String vImage1) {
        this.vImage1 = vImage1;
    }

    public int getiAutoId() {
        return iAutoId;
    }

    public void setiAutoId(int iAutoId) {
        this.iAutoId = iAutoId;
    }
}
