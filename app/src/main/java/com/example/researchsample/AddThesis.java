package com.example.researchsample;

import com.google.gson.annotations.SerializedName;

public class AddThesis {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("sampleDescripton")
    private String sampleDescripton;
    @SerializedName("developedBy")
    private String developedBy;
    @SerializedName("projectType")
    private String projectType;
    @SerializedName("department")
    private String department;
    @SerializedName("vImage1")
    private String vImage1;
    @SerializedName("patternType")
    private Boolean patternType;
    @SerializedName("uid")
    private Boolean uid;
    @SerializedName("userType")
    private Boolean userType;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    @SerializedName("genere")
    private String genere;

    @SerializedName("description")
    private String description;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

}
