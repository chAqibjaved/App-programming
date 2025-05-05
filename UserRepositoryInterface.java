package com.aqib.mymedreminder.repository;

import com.aqib.mymedreminder.data.entities.User;

public interface UserRepositoryInterface {

    void login(String cnic, String password, ValidateCallBack validateCallBack);

    void validateUser(String passportId,String dob,ValidateCallBack validateCallBack);

    void changePassword(String passportid, String newPassword, SaveCallBack callback);
    void signUp(User user, SaveCallBack saveCallBack);
    interface ValidateCallBack{
        void onSuccess(User user);
        void onError(String errormessage);
    }
    interface SaveCallBack {
        void onSuccess();
        void onError(String errorMessage);
    }

}
