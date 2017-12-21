package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.ProjectMiaomuBean;
import com.app.officeautomationapp.bean.ProjectTujianBean;

import java.util.List;

/**
 * Created by yu on 2017/2/27.
 */
public class ProjectMiaomuAdapter extends ArrayAdapter<ProjectMiaomuBean> {

    private int resourceId;

    private Context mContext;


    public ProjectMiaomuAdapter(Context context, int resource, List<ProjectMiaomuBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectMiaomuBean approvalBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hProjectName=(TextView)view.findViewById(R.id.tx_project_name);
            viewHolder.hProjectNum=(TextView) view.findViewById(R.id.tx_project_num);
            viewHolder.hProjectData=(TextView) view.findViewById(R.id.tx_project_data);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.hProjectName.setText(approvalBean.getProjectName());
        viewHolder.hProjectNum.setText(approvalBean.getApplyCode());
        viewHolder.hProjectData.setText(approvalBean.getApplyDate());
        return view;
    }


    //实例缓存
    class ViewHolder{
        TextView hProjectName;
        TextView hProjectNum;
        TextView hProjectData;
    }
}
