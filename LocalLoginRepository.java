package com.aqib.mymedreminder.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.aqib.mymedreminder.data.dao.UserDao;
import com.aqib.mymedreminder.data.database.AppDatabase;
import com.aqib.mymedreminder.data.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalLoginRepository implements UserRepositoryInterface {

    private final UserDao userDao;
    private final ExecutorService executorService;


    public LocalLoginRepository(Context context) {
        AppDatabase db=AppDatabase.getINSTANCE(context);
        userDao=db.userDao();
        executorService= Executors.newSingleThreadExecutor();
    }




    @Override
    public void signUp(User user, SaveCallBack saveCallBack) {
        executorService.execute(() -> {
            // Check if user with the same passportId already exists
            User existingUser = userDao.getUserByPassportId(user.getPassportId());
            if (existingUser != null) {
                // User already exists
                new Handler(Looper.getMainLooper()).post(() -> saveCallBack.onError("A user with this ID already exists."));

            } else {
                // Insert new user into the database
                long newUserId = userDao.insertUser(user);
                if (newUserId != -1) {
                    new Handler(Looper.getMainLooper()).post(() -> saveCallBack.onSuccess());
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> saveCallBack.onError("Failed to sign up. Please try again."));

                }
            }
        });
    }

    @Override
    public void login(String cnic, String password, ValidateCallBack validateCallBack) {
        executorService.execute(() ->{
            User user=userDao.getUserByIdAndPassword(cnic,password);
            if(user != null){
                new Handler(Looper.getMainLooper()).post(()-> validateCallBack.onSuccess(user));
            }
            else {
                new Handler(Looper.getMainLooper()).post(()->validateCallBack.onError("Invalid credentials"));
            }
        });
    }

    @Override
    public void validateUser(String passportId, String dob, ValidateCallBack validateCallBack) {
        executorService.execute(() ->{
            User user=userDao.getUserByPassporIDAndDob(passportId,dob);
            if(user != null){
                new Handler(Looper.getMainLooper()).post(()-> validateCallBack.onSuccess(user));
            }
            else {
                new Handler(Looper.getMainLooper()).post(()->validateCallBack.onError("Invalid credentials"));
            }
        });
    }

    @Override
    public void changePassword(String passportid, String newPassword, SaveCallBack callback) {
        executorService.execute(() ->{
            int  updateCount=userDao.updateUserPassword(passportid,newPassword);
            if(updateCount > 0){
                new Handler(Looper.getMainLooper()).post(()-> callback.onSuccess());
            }
            else {
                new Handler(Looper.getMainLooper()).post(()->callback.onError("Failed to change password"));
            }
        });
    }
}
