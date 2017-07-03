package com.app.officeautomationapp.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalWorkActivity;
import com.app.officeautomationapp.activity.HiddenProjectActivity;
import com.app.officeautomationapp.activity.MessageActivity;
import com.app.officeautomationapp.activity.ProjectItemActivity;
import com.app.officeautomationapp.activity.ReceiveThingsActivity;
import com.app.officeautomationapp.activity.StartWorkActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/3/18.
 */
public class WorkFragment extends Fragment  implements View.OnClickListener{

    private RollPagerView rollPagerView;

    private ViewPager viewPager;

    private Fragment[] fragments;

    private List<ImageView> mDots;//定义一个集合存储三个dot

    private Button btnHiddenProject;//按钮
    private Button btnWorkApproval;
    private Button btnReceiveThings;
    private Button btnStartWork;
    private Button btnMessage;


    private LinearLayout llMessage;
    private LinearLayout llManageItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_work,null);
        //work header图片轮播
        rollPagerViewSet(view);
        //dot
        viewPagerSet(view);
        viewPager.setAdapter(new MyAdpater(this.getFragmentManager()));

        btnHiddenProject=(Button)view.findViewById(R.id.btn_hidden_project);
        btnHiddenProject.setOnClickListener(this);
        btnWorkApproval=(Button) view.findViewById(R.id.btn_work_approval);
        btnWorkApproval.setOnClickListener(this);
        btnReceiveThings=(Button) view.findViewById(R.id.btn_receive_things);
        btnReceiveThings.setOnClickListener(this);
        btnStartWork=(Button)view.findViewById(R.id.btn_start_work);
        btnStartWork.setOnClickListener(this);
        llMessage=(LinearLayout)view.findViewById(R.id.ll_message);
        llMessage.setOnClickListener(this);
        llManageItem=(LinearLayout)view.findViewById(R.id.ll_manageItem);
        llManageItem.setOnClickListener(this);
        btnMessage=(Button)view.findViewById(R.id.btn_message);
        btnMessage.setOnClickListener(this);



        return view;
    }


    //work header图片轮播 strat
    private void rollPagerViewSet(View view) {
        rollPagerView= (RollPagerView) view.findViewById(R.id.rollViewpager);//获取对应控件
        rollPagerView.setPlayDelay(3000);//*播放间隔
        rollPagerView.setAnimationDurtion(500);//透明度
        rollPagerView.setAdapter(new rollViewpagerAdapter());//配置适配器

    }



    private class rollViewpagerAdapter extends StaticPagerAdapter {

        private int[] res={R.mipmap.timg1
                ,R.mipmap.timg2,
                R.mipmap.timg3,
                R.mipmap.timg4};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView=new ImageView(container.getContext());
            imageView.setImageResource(res[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return res.length;
        }
    }


//工作的列表
    private void viewPagerSet(View view)
    {
        viewPager=(ViewPager)view.findViewById(R.id.workViewPager);
        fragments=new Fragment[3];
        fragments[0]=new ButtonGroup1Fragment();
        fragments[1]=new ButtonGroup2Fragment();
        fragments[2]=new ButtonGroup3Fragment();

        mDots = new ArrayList<ImageView>();
        ImageView dotFirst = (ImageView) view.findViewById(R.id.dot_first);
        ImageView dotFSecond = (ImageView) view.findViewById(R.id.dot_second);
        ImageView dotThrid = (ImageView) view.findViewById(R.id.dot_thrid);
        mDots.add(dotFirst);
        mDots.add(dotFSecond);
        mDots.add(dotThrid);
        mDots.get(0).setImageResource(R.mipmap.dot_i);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDots.get(0).setImageResource(R.mipmap.dot_n);
                mDots.get(1).setImageResource(R.mipmap.dot_n);
                mDots.get(2).setImageResource(R.mipmap.dot_n);
                mDots.get(position).setImageResource(R.mipmap.dot_i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class  MyAdpater extends FragmentPagerAdapter
    {

        public MyAdpater(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hidden_project:
                startActivity(new Intent(getActivity(), HiddenProjectActivity.class));
                break;
            case R.id.btn_work_approval:
                startActivity(new Intent(getActivity(), ApprovalWorkActivity.class));
                break;
            case R.id.btn_receive_things:
                startActivity(new Intent(getActivity(), ReceiveThingsActivity.class));
                break;
            case R.id.btn_start_work:
                startActivity(new Intent(getActivity(), StartWorkActivity.class));
                break;
            case R.id.ll_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.btn_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.ll_manageItem:
                startActivity(new Intent(getActivity(), ProjectItemActivity.class));
                break;



            default:
                break;
        }
    }


}
