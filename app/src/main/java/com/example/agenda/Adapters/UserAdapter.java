package com.example.agenda.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.agenda.Admin.UsersManagement.UsersList;
import com.example.agenda.Models.Mail;
import com.example.agenda.Models.User;
import com.example.agenda.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mContext ;
    private List<User> mData ;

    public UserAdapter(Context mContext, List<User> lst) {
        this.mContext = mContext;
        this.mData = lst;
    }



    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.user_card_view,parent,false);
        // click listener here
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, final int position) {




        holder.email.setText(mData.get(position).getEmail());
        holder.phone.setText(mData.get(position).getPhone());
        holder.username.setText(mData.get(position).getFull_name());
        holder.role.setText(mData.get(position).getRole());
        holder.delete.setOnClickListener(v -> {



        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {


        TextView username,email,role,phone,delete;
        ImageView img;

        public UserViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name);
            phone = itemView.findViewById(R.id.user_phone);
            email = itemView.findViewById(R.id.user_email);
            img = itemView.findViewById(R.id.user_img_prof);
            delete = itemView.findViewById(R.id.delete_user);
            role = itemView.findViewById(R.id.user_role);
        }
    }
}
