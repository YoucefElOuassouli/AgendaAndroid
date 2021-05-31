package com.example.agenda.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.Admin.AdminHome;
import com.example.agenda.Home.LoginPage;
import com.example.agenda.Models.Mail;
import com.example.agenda.Models.User;
import com.example.agenda.R;

import java.util.ArrayList;
import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder> {


    private Context mContext ;
    private List<Mail> mData ;

    public MailAdapter(Context mContext, List<Mail> lst) {
        this.mContext = mContext;
        this.mData = lst;
    }



    @NonNull
    @Override
    public MailAdapter.MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.mail_card_view,parent,false);
        // click listener here
        return new MailAdapter.MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MailAdapter.MailViewHolder holder, final int position) {


        holder.titre.setText(mData.get(position).getFrom());

        holder.desc.setText(mData.get(position).getContent());







    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MailViewHolder extends RecyclerView.ViewHolder {


        TextView titre, desc;
        TextView more;

        public MailViewHolder(View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.mail_receiver_user);
            desc = itemView.findViewById(R.id.mail_receiver_content);
            more = itemView.findViewById(R.id.More);
        }
    }

}
