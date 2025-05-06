package com.aqib.mymedreminder.repository;

import android.util.Log;

import com.aqib.mymedreminder.data.entities.User;
import com.aqib.mymedreminder.model.Product;
import com.aqib.mymedreminder.model.ProductResponse;
import com.aqib.mymedreminder.network.MyMedApiInterface;
import com.aqib.mymedreminder.network.MyMedDatabaseRetrofit;
import com.aqib.mymedreminder.utils.GsonParser;
import com.aqib.mymedreminder.utils.Session;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineUserRepository  implements UserRepositoryInterface {




    @Override
    public void login(String cnic, String password, ValidateCallBack validateCallBack) {
        MyMedApiInterface medApiInterface=MyMedDatabaseRetrofit.getMyMedApiInterface();
        medApiInterface.LoginRequest(Session.access_key,"1",cnic,password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(String.valueOf(response.body()));
                                boolean value= Boolean.parseBoolean(jsonObject.getString("status"));
                                if(!value){
                                    validateCallBack.onError(jsonObject.getString("message"));
                                }
                                else if(value){
                                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                                    jsonObject=jsonArray.getJSONObject(0);
                                    User registerCrate= GsonParser.getInstance().convertFromJsonObject(jsonObject,User.class);
                                    validateCallBack.onSuccess(registerCrate);
                                }
                            } catch (JSONException e) {
                                validateCallBack.onError(e.getMessage());
                            }

                        }
                        else {
                            validateCallBack.onError("Error Try Again");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        validateCallBack.onError(t.getMessage());
                    }
                });
    }

    @Override
    public void validateUser(String passportId, String dob, ValidateCallBack validateCallBack) {

    }

    @Override
    public void changePassword(String passportid, String newPassword, SaveCallBack callback) {

    }

    @Override
    public void signUp(User user, SaveCallBack saveCallBack) {
        user.setInsertnewusers("1");
        user.setAccess_key(Session.access_key);
        MyMedApiInterface medApiInterface=MyMedDatabaseRetrofit.getMyMedApiInterface();
        medApiInterface.InsertnewUsers(user).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(String.valueOf(response.body()));
                        boolean value = Boolean.parseBoolean(jsonObject.getString("status"));
                        if (!value) {
                            saveCallBack.onError(jsonObject.getString("message"));
                            // if any error occures
                        } else if (value) {
                            // successful
                            saveCallBack.onSuccess();
                        }
                    } catch (Exception e) {
                        saveCallBack.onError(e.getMessage());
                    }
                }
                else {
                    saveCallBack.onError("Error please try again");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                saveCallBack.onError(t.getMessage());
            }
        });
    }
}
