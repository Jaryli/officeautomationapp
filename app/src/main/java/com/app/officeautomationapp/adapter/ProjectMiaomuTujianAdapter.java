package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.ApprovalBean;
import com.app.officeautomationapp.bean.ProjectMiaomuTujianBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/2/27.
 */
public class ProjectMiaomuTujianAdapter extends ArrayAdapter<ProjectMiaomuTujianBean> {

    private int resourceId;

    private Context mContext;


    public ProjectMiaomuTujianAdapter(Context context, int resource, List<ProjectMiaomuTujianBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectMiaomuTujianBean approvalBean=getItem(position);//获得实例
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
