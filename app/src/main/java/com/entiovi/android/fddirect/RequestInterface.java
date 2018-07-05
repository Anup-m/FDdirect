package com.entiovi.android.fddirect;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    //TODO change endpoints as required
    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}