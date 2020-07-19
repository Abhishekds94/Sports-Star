package com.abhishek.sportsstar.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhishek.sportsstar.data.network.SportsAPI;
import com.abhishek.sportsstar.data.network.SportsAPIClient;
import com.bumptech.glide.Glide;

public class BaseFragment extends Fragment {

    protected View rootView;
    protected Activity activity;
    protected SportsAPI client;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        client = SportsAPIClient.getClient(activity);
    }

    /**
     * Show toats
     * @param message toast message String
     */
    protected void showToast(String message) {
        Toast.makeText(activity,
                message,
                Toast.LENGTH_SHORT).show();
    }

    protected void throwError(Throwable throwable){
        if (null != throwable) {
            showToast(throwable.getMessage());
        }
    }

    /**
     * Load image
     * @param targetImageView Imageview reference
     * @param url             Web url to load image
     */
    protected void loadImage(ImageView targetImageView, String url) {
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(targetImageView);
    }

    protected void loadImage1(ImageView targetImageView1, String url1) {
        Glide.with(this)
                .load(url1)
                .fitCenter()
                .into(targetImageView1);
    }

}
