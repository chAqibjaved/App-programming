package com.aqib.mymedreminder.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyMedDatabaseRetrofit {

 //   private static final String BASE_URL = "http://medreminder.wuaze.com/Api/";

    private static final String BASE_URL = "https://app.everydayintradaytips.com/Mymed/Api/";

 //   private static final String BASE_URL = "https://dummyjson.com/";


    private static volatile Retrofit retrofit;

    public static Retrofit getINSTANCE(){
        if(retrofit==null){
            synchronized (MyMedDatabaseRetrofit.class){
                Gson gson = new GsonBuilder()
                        .create();
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
        }
        return retrofit;
    }

    public static MyMedApiInterface getMyMedApiInterface() {
        return getINSTANCE().create(MyMedApiInterface.class);
    }
}
