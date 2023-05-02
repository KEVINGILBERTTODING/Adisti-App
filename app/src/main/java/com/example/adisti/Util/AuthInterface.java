package com.example.adisti.Util;

import com.example.adisti.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthInterface {
    @FormUrlEncoded
    @POST("auth/login")
    Call<UserModel>login(
            @Field("username") String username,
            @Field("password") String password
    );
}
