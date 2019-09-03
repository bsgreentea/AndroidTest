package com.greentea.connectiontest2;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    final String BASE_URL = "http://165.246.241.177:3000";
//    final String BASE_URL = "http://192.168.56.1:3000";

    @GET("/persons")
    Call<List<Person>> getPeople();

    @GET("/persons/{id}")
    Call<Person> getPerson(
            @Path("id") String id
    );

    @POST("/")
    Call<Person> postPerson(
            @Body Person per
    );
}
