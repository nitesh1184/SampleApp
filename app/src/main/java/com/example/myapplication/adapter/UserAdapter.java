package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.MyDatabase;
import com.example.myapplication.databinding.UserItemBinding;
import com.example.myapplication.model.MyUser;
import com.example.myapplication.utils.ItemClickListener;
import com.example.myapplication.view.AddUser;
import com.example.myapplication.viewModel.UserListViewModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.EmployeeViewHolder> implements
        ItemClickListener {

    private List<MyUser> users;
    private Context mContext;
    private ItemClickListener clickListener;
    private UserListViewModel userModel;

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        UserItemBinding userBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.user_item, viewGroup, false);
        return new EmployeeViewHolder(userBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder employeeViewHolder, int i) {
        final MyUser currentUser = users.get(i);
        employeeViewHolder.userItemBinding.setMyUser(currentUser);
        employeeViewHolder.userItemBinding.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogBox(currentUser,v);
            }
        });
    }

    public void showAlertDialogBox(final MyUser user, final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Please select the action you want to perform ")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        userModel.deleteUsers(user);
                        users.remove(user);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        Fragment myFragment = new AddUser();
                        Bundle args = new Bundle();
                        args.putString("action", "update");
                        args.putString("name",user.userName);
                        args.putString("email",user.userEmail);
                        args.putString("city",user.userCity);
                        args.putInt("id",user.id);
                        myFragment.setArguments(args);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, myFragment).addToBackStack(null).commit();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }




    @Override
    public int getItemCount() {
        if (users != null) {
            return users.size();
        } else {
            return 0;
        }
    }

    public void setUsers(List<MyUser> users,Context context) {
        this.users = users;
        mContext=context;
        notifyDataSetChanged();
        userModel = ViewModelProviders.of((FragmentActivity) context).get(UserListViewModel.class);
    }

    @Override
    public void onClick(View view, int position) {
        if (clickListener != null) clickListener.onClick(view, position);
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private UserItemBinding userItemBinding;

        public EmployeeViewHolder(@NonNull UserItemBinding userItemBinding) {
            super(userItemBinding.getRoot());

            this.userItemBinding = userItemBinding;

        }


    }
}
