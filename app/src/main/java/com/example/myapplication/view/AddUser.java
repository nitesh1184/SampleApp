package com.example.myapplication.view;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.AddUserFragmentBinding;
import com.example.myapplication.databinding.UserListFragmentBinding;
import com.example.myapplication.model.MyUser;
import com.example.myapplication.viewModel.AddUserViewModel;

public class AddUser extends Fragment {

    private AddUserViewModel mViewModel;
    String name,email,city,value;
    int id;
    AddUserFragmentBinding aBinding;

    public static AddUser newInstance() {
        return new AddUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        aBinding= DataBindingUtil.inflate(
                inflater, R.layout.add_user_fragment, container, false);
        View view = aBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddUserViewModel.class);
        value = getArguments().getString("action");
        if(value=="update"){
            name = getArguments().getString("name");
            email= getArguments().getString("email");
            city = getArguments().getString("city");
            id=getArguments().getInt("id");
        }
        else{
            name="";
            email="";
            city="";

        }

       aBinding.etname.setText(name);
        aBinding.etemail.setText(email);
       aBinding.etcity.setText(city);
       aBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MyUser user=new MyUser();
               user.setUserEmail(aBinding.etemail.getText().toString());
               user.setUserName(aBinding.etname.getText().toString());
               user.setUserCity(aBinding.etcity.getText().toString());
               if(value=="update"){
                   user.setId(id);
                   mViewModel.updateUser(user);
                   redirectToList();
               }
               else{
                   mViewModel.addUser(user);
                   redirectToList();
               }
           }
       });



        // TODO: Use the ViewModel
    }

    public void redirectToList(){
        Fragment fragment = new user_list();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}