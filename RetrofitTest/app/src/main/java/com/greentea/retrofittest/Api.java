package com.greentea.retrofittest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    final String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")
    Call<List<HeroExample>> getHeroes();

}
