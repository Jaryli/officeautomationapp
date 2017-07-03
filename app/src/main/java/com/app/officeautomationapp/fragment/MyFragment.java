package com.app.officeautomationapp.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.view.RoundImageView;
import com.app.officeautomationapp.view.XutilImageOptions;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by yu on 2017/3/18.
 */
public class MyFragment extends Fragment{

    RoundImageView ivMyPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,null);
        ivMyPhoto=(RoundImageView)view.findViewById(R.id.iv_my_photo);
        x.image().bind(ivMyPhoto, "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1887792679,709769868&fm=117&gp=0.jpg", XutilImageOptions.getCommonOptions());
        return view;
    }
}
