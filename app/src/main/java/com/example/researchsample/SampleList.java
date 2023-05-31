package com.example.researchsample;

public class SampleList {

    private int id;
    private String name;
    private String developedBy;
    private String imageUrl;



    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDevelopedBy() {
        return developedBy;
    }

    public void setDevelopedBy(String developedBy) {
        this.developedBy = developedBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public SampleList(int id, String name, String developedBy, String imageUrl,String url) {
        this.id = id;
        this.name = name;
        this.developedBy = developedBy;
        this.imageUrl = imageUrl;
        this.url = url;
    }
}
