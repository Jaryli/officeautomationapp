package com.app.officeautomationapp.adapter;



import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalReceiveActivity;
import com.app.officeautomationapp.activity.MyApprovalReceiveActivity;
import com.app.officeautomationapp.bean.MyApprovalReceiveBean;
import com.app.officeautomationapp.bean.ReceiveThingsCheckApplyPostBean;
import com.app.officeautomationapp.bean.ReceiveThingsCheckBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.view.MoreTextView;
import com.app.officeautomationapp.view.SpinnerDialog2;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/2/27.
 */
public class MyApprovalReceiveThingsAdapter extends ArrayAdapter<MyApprovalReceiveBean> {

    private int resourceId;

    private Context mContext;


    ProgressDialog progressDialog;

    private UserDto userDto;

    public MyApprovalReceiveThingsAdapter(Context context, int resource, List<MyApprovalReceiveBean> objects, UserDto userDto) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
        this.userDto=userDto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyApprovalReceiveBean receiveThingsCheckBean=getItem(position);//获得实例
        View view;
        final ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hTitle=(MoreTextView) view.findViewById(R.id.approval_title);

            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hTitle.setText(getText(receiveThingsCheckBean));
        return view;
    }


    private String getText(MyApprovalReceiveBean receiveThingsCheckBean)
    {
        StringBuffer str=new StringBuffer();
        str.append("物品名称:"+receiveThingsCheckBean.getResName());
        str.append("\n");
        str.append("分配领用:"+receiveThingsCheckBean.getTypeInfo());
        str.append("\n");
        str.append("领用时间:"+receiveThingsCheckBean.getUseDate());
        str.append("\n");
        str.append("归还时间:"+receiveThingsCheckBean.getBackDate());
        str.append("\n");
        str.append("申请时间:"+receiveThingsCheckBean.getApplyDate());
        str.append("\n");
        str.append("关联工程:"+receiveThingsCheckBean.getProjectName());
        str.append("\n");
        str.append("申请数量:"+receiveThingsCheckBean.getNumInfo());
        str.append("\n");
        str.append("审批记录:"+receiveThingsCheckBean.getRemark());

        return str.toString();
    }



    //实例缓存
    class ViewHolder{
        MoreTextView hTitle;
    }
}
