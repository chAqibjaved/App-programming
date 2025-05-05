package com.aqib.mymedreminder.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.aqib.mymedreminder.data.entities.User;

@Dao
public interface UserDao {

    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users where passportId=:passportId limit 1")
    User getUserByPassportId(String passportId);

    @Query("SELECT * FROM users WHERE passportId = :passportId AND dob = :dob")
    User getUserByPassporIDAndDob(String passportId, String dob);

    @Query("SELECT * FROM users where passportId=:passportId and password=:password limit 1")
    User getUserByIdAndPassword(String passportId,String password);

    @Query("UPDATE users SET password = :newPassword WHERE passportId = :passportId")
    int updateUserPassword(String passportId, String newPassword);

    @Query("UPDATE users SET name = :name, dob = :dob, disease = :disease WHERE passportId = :passportId")
    int updateUserProfile(String name, String dob, String disease, String passportId);

}
