package com.thereza.cmed_task02.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.thereza.cmed_task02.R;
import com.thereza.cmed_task02.data.AppConstant;
import com.thereza.cmed_task02.model.User;
import com.thereza.cmed_task02.model.UserList;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private ArrayList<User> userList;
    private Context mContext;

    public UserAdapter(ArrayList<User> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {

        if (userList.get(position).getGender().equals(AppConstant.FEMALE)){
            Glide.with(mContext).load(AppConstant.WOMEN_PRO_PIC_BASE_URL+userList.get(position).getPhoto()+AppConstant.EXTENTION)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .dontAnimate()
                    .into(holder.ivProPic);
        }else {
            Glide.with(mContext).load(AppConstant.MEN_PRO_PIC_BASE_URL+userList.get(position).getPhoto()+AppConstant.EXTENTION)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new CropCircleTransformation(mContext))
                    .dontAnimate()
                    .into(holder.ivProPic);
        }

        holder.tvName.setText(userList.get(position).getFirstName()+" "+userList.get(position).getLastName());
        holder.tvMobile.setText("Mobile: "+userList.get(position).getPhones().getMobile());
        holder.tvEmail.setText("Email: "+userList.get(position).getEmail().get(0));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProPic;
        TextView tvName, tvMobile,tvEmail;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivProPic = itemView.findViewById(R.id.iv_pro_pic);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMobile = itemView.findViewById(R.id.tv_mobile);
            tvEmail = itemView.findViewById(R.id.tv_email);
        }
    }


}
