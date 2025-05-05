package com.aqib.mymedreminder.data.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "users" , indices = { @Index(value = "passportId",unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String dob;

    private String passportId;

    private String disease;

    private String password;

    public User() {
    }

    public User(String name, String dob, String passportId, String disease, String password) {
        this.name = name;
        this.dob = dob;
        this.passportId = passportId;
        this.disease = disease;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
