package com.example.adisti.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApi {

    public static final String IP_ADDRESS = "192.168.100.6";
    public static final String BASE_URL = "http://"+IP_ADDRESS+"/adisti-jr/api/";
    public static final String URL_PHOTO_PROFILE = "http://"+IP_ADDRESS+"/adisti-jr/uploads/photo_profile/";
    public static final String URL_DOWNLOAD_PROPOSAL = "http://"+IP_ADDRESS+"/adisti-jr/api/pengaju/downloadProposal/";
    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
