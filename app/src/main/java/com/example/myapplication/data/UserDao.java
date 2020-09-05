package com.example.myapplication.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.model.MyUser;

import java.util.List;

@Dao
public interface UserDao {


    @Query("SELECT * FROM  user ORDER BY id DESC")
    LiveData<List<MyUser>> getAllUsers();



    @Insert
    void insertUser(MyUser... user);

    @Update
    void updateUser(MyUser... user);

    @Delete
    void deleteUser(MyUser... user);

}
