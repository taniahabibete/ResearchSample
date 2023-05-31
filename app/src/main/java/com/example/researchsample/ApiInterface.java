package com.example.researchsample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haerul on 15/03/18.
 */

public interface ApiInterface {

//

    @FormUrlEncoded
    @POST("add_thesis.php")
    Call<AddThesis> insertThesis(
            @Field("key") String key,
            @Field("name") String name,
            @Field("sampleDescripton") String sampleDescripton,
            @Field("developedBy") String developedBy,
            @Field("projectType") String projectType,
            @Field("department") String department,
            @Field("vImage1") String vImage1,
            @Field("patternType") String patternType,
            @Field("userType") String userType,
            @Field("uid") String uid,
            @Field("url") String weburl
    );

    @FormUrlEncoded
    @POST("add_thesis_idea.php")
    Call<AddThesis> insertThesisIdea(
            @Field("key") String key,
            @Field("name") String name,
            @Field("description") String description,
            @Field("genere") String genere,
            @Field("projectType") String projectType,
            @Field("department") String department,
            @Field("vImage1") String vImage1,
            @Field("userType") String userType,
            @Field("uid") String uid,
            @Field("url") String url
    );

    @FormUrlEncoded
    @POST("add_profile.php")
    Call<UpdateProfileModel> updateProfile(
            @Field("key") String key,
            @Field("name") String name,
            @Field("gender") String gender,
            @Field("phone_no") String phone_no,
            @Field("usertype") String usertype,
            @Field("uid") String uid

    );


}