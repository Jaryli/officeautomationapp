package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
            viewHolder.hll_pinming=(LinearLayout) view.findViewById(R.id.ll_pinming);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hll_pinming.removeAllViews();//这里必须清除之前增加的view
        viewHolder.hProjectName.setText(approvalBean.getProjectName());
        viewHolder.hProjectNum.setText(approvalBean.getApplyCode());
        viewHolder.hProjectData.setText(approvalBean.getApplyDate().substring(0,11));
        List<ProjectMiaomuBean.Detail> list= approvalBean.getDetails();
        if(list.size()>0)
        {
            for(int i=0;i<list.size();i++)
            {
                int left, top, right, bottom;
                left = top = right = bottom = 5;
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(left, top, right, bottom);

                int left2= 15;
                LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params2.setMargins(left2, top, right, bottom);

                final LinearLayout layout=new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                TextView t0 = new TextView(mContext);
                t0.setText("品名：");
                t0.setTextSize(12);
                t0.setTextColor(mContext.getResources().getColor(R.color.buttonyellow));
                t0.setLayoutParams(params);
                layout.addView(t0);

                TextView t1 = new TextView(mContext);
                t1.setText(list.get(i).getSName());
                t1.setTextSize(12);
                t1.setLayoutParams(params);
                layout.addView(t1);

                TextView t2 = new TextView(mContext);
                t2.setText("数量：");
                t2.setTextSize(12);
                t2.setTextColor(mContext.getResources().getColor(R.color.buttonyellow));
                t2.setLayoutParams(params2);
                layout.addView(t2);

                TextView t3 = new TextView(mContext);
                t3.setText(list.get(i).getQuantity()+"");
                t3.setTextSize(12);
                t3.setLayoutParams(params);
                layout.addView(t3);
                viewHolder.hll_pinming.addView(layout);
            }
        }
        return view;
    }

    //实例缓存
    class ViewHolder{
        TextView hProjectName;
        TextView hProjectNum;
        TextView hProjectData;
        LinearLayout hll_pinming;
    }
}
