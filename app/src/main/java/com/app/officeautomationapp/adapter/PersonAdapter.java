package com.app.officeautomationapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalDetailActivity;
import com.app.officeautomationapp.activity.ItemContactsActivity;
import com.app.officeautomationapp.bean.ProjectItemBean;
import com.app.officeautomationapp.bean.SortModel;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by yu on 2017/7/15.
 */

public class PersonAdapter extends ArrayAdapter<SortModel> {

    private int resourceId;

    private ImageOptions options;

    private Activity mContext;
    ArrayList<SortModel> mList;
    int maxNum;
    int resultCode;

    public PersonAdapter(Activity context, int resource, ArrayList<SortModel> objects,int maxNum,int resultCode) {
        super(context, resource,objects);
        this.mList = objects;
        this.mContext=context;
        resourceId=resource;
        this.maxNum=maxNum;
        this.resultCode=resultCode;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public SortModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        ProjectItemBean projectItemBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hName=(TextView)view.findViewById(R.id.name);
            viewHolder.hDel=(LinearLayout) view.findViewById(R.id.ll_del);
            viewHolder.hPersonplus=(ImageView) view.findViewById(R.id.iv_personplus);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        if(maxNum>=(position+1))
        {
            if((position+1)==mList.size())//dayu 1
            {
                viewHolder.hName.setVisibility(View.GONE);
                viewHolder.hDel.setVisibility(View.GONE);
                viewHolder.hPersonplus.setVisibility(View.VISIBLE);
                viewHolder.hPersonplus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //召唤神龙
                        Intent intent = new Intent();
                        intent.putExtra("hasCheckBox", true);
                        intent.putExtra("hasDone", true);
                        intent.putExtra("code", resultCode);
                        intent.setClass(mContext, ItemContactsActivity.class);
                        /*
                         * 如果希望启动另一个Activity，并且希望有返回值，则需要使用startActivityForResult这个方法，
                         * 第一个参数是Intent对象，第二个参数是一个requestCode值，如果有多个按钮都要启动Activity，则requestCode标志着每个按钮所启动的Activity
                         */
                        mContext.startActivityForResult(intent,resultCode);
                    }
                });
            }
            else
            {
                viewHolder.hName.setText(mList.get(position).getName());
                viewHolder.hDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.remove(position);
                        refresh(mList);
                    }
                });
            }
        }
        return view;
    }

    public void refresh(ArrayList<SortModel> list) {
        mList = list;
        notifyDataSetChanged();
    }


    //实例缓存
    class ViewHolder{
        TextView hName;
        LinearLayout hDel;
        ImageView hPersonplus;
    }
}
