package com.app.officeautomationapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.app.officeautomationapp.fragment.CommonFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class TheMediaAdapter extends FragmentStatePagerAdapter {
    private ArrayList<CommonFragment> fragments;
    private final String[] imageArray = {"assets://image1.jpg", "assets://image2.jpg", "assets://image3.jpg", "assets://image4.jpg", "assets://image5.jpg"};
    private FragmentManager fm;

    public TheMediaAdapter(FragmentManager fm, ArrayList<CommonFragment> fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;

    }


    @Override

    public Fragment getItem(int arg0) {
        CommonFragment commonFragment=fragments.get(arg0);
        commonFragment.bindData(imageArray[arg0 % imageArray.length]);
        return commonFragment;

    }


    @Override

    public int getCount() {
        return fragments.size();
    }


    @Override

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    public void setFragments(ArrayList fragments) {
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();

        }

        this.fragments = fragments;
        notifyDataSetChanged();

    }

}
