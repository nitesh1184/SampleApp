package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.MyDatabase;
import com.example.myapplication.data.UserDao;
import com.example.myapplication.model.MyUser;

import java.util.List;

public class AddUserViewModel extends AndroidViewModel {
    private UserDao repository;
    private  LiveData<List<MyUser>> result;

    public AddUserViewModel(@NonNull Application application) {
        super(application);

        repository = MyDatabase
                .getInstance(getApplication())
                .userDao();

    }

    public void addUser(MyUser user) {
        repository.insertUser(user);
    }

    public void updateUser(MyUser user) {
        repository.updateUser(user);
    }
}