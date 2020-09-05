package com.example.myapplication.view;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.UserAdapter;
import com.example.myapplication.databinding.UserListFragmentBinding;
import com.example.myapplication.model.MyUser;
import com.example.myapplication.viewModel.UserListViewModel;

import java.util.List;

public class user_list extends Fragment {

    private UserListViewModel mViewModel;
    private UserAdapter uAdapter;
    UserListFragmentBinding userBinding;
    private UserListViewModel userModel;

    public static user_list newInstance() {
        return new user_list();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         userBinding= DataBindingUtil.inflate(
                inflater, R.layout.user_list_fragment, container, false);
        View view = userBinding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        RecyclerView recyclerView = userBinding.viewUser;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        uAdapter = new UserAdapter();
        recyclerView.setAdapter(uAdapter);
        getAllUser();
//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                String text = mStrings[position];
//                YourImageClass img = mImages[position];
//                Intent i = new Intent(getActivity(), ShowFullImageActivity.class);
//                i.putExtra("TEXT", text);
//                i.putExtra("IMAGE", img); // <-- Assumed you image is Parcelable
//                startActivity(i);
//            }
//        }
        userBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddUser();
                Bundle args = new Bundle();
                args.putString("action", "add");
                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void getAllUser() {
        mViewModel.getUsers().observe(this, new Observer<List<MyUser>>() {
            @Override
            public void onChanged(@Nullable List<MyUser> users) {
                if(users!=null || users.size()!=0){
                    userBinding.noText.setVisibility(View.GONE);
                    uAdapter.setUsers(users,getActivity());
                }
                else{
                    userBinding.noText.setText("No User Found");
                    userBinding.noText.setVisibility(View.VISIBLE);

                }

            }
        });
    }



}