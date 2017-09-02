package com.app.officeautomationapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.NextStep;
import com.app.officeautomationapp.bean.ProjectItemBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/7/15.
 */

public class ApprovalNextStepAdapter extends ArrayAdapter<NextStep> {

    private int resourceId;


    private Context mContext;
    List<NextStep> mList;

    public ApprovalNextStepAdapter(Context context, int resource, List<NextStep> objects) {
        super(context, resource,objects);
        this.mList = objects;
        this.mContext=context;
        resourceId=resource;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NextStep getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.hTitle=(TextView) view.findViewById(R.id.text_item);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hTitle.setText(mList.get(position).getAFS_Name());
        return view;
    }

    public void refresh(ArrayList<NextStep> list) {
        mList = list;
        notifyDataSetChanged();
    }


    //实例缓存
    class ViewHolder{
        TextView hTitle;
    }
}
