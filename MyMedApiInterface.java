package com.aqib.mymedreminder.network;

import com.aqib.mymedreminder.data.entities.User;
import com.aqib.mymedreminder.model.ProductResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyMedApiInterface {


    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("mymedapi.php")
    Call<JsonObject>
    LoginRequest(@Query("access_key") String access_key,
                 @Query("loginrequest") String loginrequest,
                 @Query("id") String id,
                 @Query("password") String password
    );


    @POST("mymedapi.php")
    Call<JsonObject> InsertnewUsers(@Body User user);



}
