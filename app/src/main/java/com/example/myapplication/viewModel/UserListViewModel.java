package com.example.myapplication.viewModel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.MyDatabase;
import com.example.myapplication.model.MyUser;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {
    private final LiveData<List<MyUser>> users;

    public UserListViewModel(@NonNull Application application) {
        super(application);

        users = MyDatabase
                .getInstance(getApplication())
                .userDao()
                .getAllUsers();
    }

    public LiveData<List<MyUser>> getUsers() {
        return users;
    }
    public void deleteUsers(MyUser user) {
        MyDatabase
                .getInstance(getApplication())
                .userDao()
                .deleteUser(user);
    }
    }
