package com.aqib.mymedreminder.data.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aqib.mymedreminder.data.dao.UserDao;
import com.aqib.mymedreminder.data.entities.User;

@Database(entities = {User.class}, version =1 )
public abstract class AppDatabase extends  RoomDatabase {

    public abstract UserDao userDao();

    private static volatile AppDatabase instance;

   public static AppDatabase getINSTANCE(Context context){
       if(instance==null){
           synchronized (AppDatabase.class){
               if(instance==null){
                   instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"mymed_database")
                           .build();
               }
           }
       }
       return instance;
   }
}
