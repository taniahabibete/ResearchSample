package com.example.researchsample;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("phone_no")
    private String phone_no;
    @SerializedName("usertype")
    private String usertype;


    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;



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
