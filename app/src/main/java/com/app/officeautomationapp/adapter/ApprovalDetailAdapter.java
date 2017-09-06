package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.ApprovalBean;
import com.app.officeautomationapp.bean.FlowHistorie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/2/27.
 */
public class ApprovalDetailAdapter extends ArrayAdapter<FlowHistorie> {

    private int resourceId;

    private Context mContext;

    private  List<FlowHistorie> objectsList;

    private boolean hasMe;


    public ApprovalDetailAdapter(Context context, int resource, List<FlowHistorie> objects,boolean hasMe) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
        objectsList=objects;
        this.hasMe=hasMe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FlowHistorie flowHistorie=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hPic1=(View)view.findViewById(R.id.approval_pic1);
            viewHolder.hPic2=(ImageView)view.findViewById(R.id.approval_pic2);
            viewHolder.hPic3=(View)view.findViewById(R.id.approval_pic3);
            viewHolder.hPic=(TextView)view.findViewById(R.id.approval_pic);
            viewHolder.hTitle=(TextView) view.findViewById(R.id.approval_title);
            viewHolder.hType=(TextView) view.findViewById(R.id.approval_type);
            viewHolder.hTime=(TextView) view.findViewById(R.id.approval_time);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        if(position==0)
        {
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.hPic1.getLayoutParams();
//            layoutParams.width = 0;
//            layoutParams.height = 20;
//            viewHolder.hPic1.setLayoutParams(layoutParams);
        }
        viewHolder.hPic.setText("已审批");
        viewHolder.hType.setText(flowHistorie.getAFHMessage());
        if(hasMe){
            if(position+1==objectsList.size())
            {
                viewHolder.hPic2.setBackground(mContext.getResources().getDrawable(R.mipmap.wait_2x));
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.hPic3.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.height = 20;
                viewHolder.hPic3.setLayoutParams(layoutParams);
                viewHolder.hPic.setText("待审批");
                viewHolder.hType.setText("审批中");
                viewHolder.hType.setTextColor(Color.parseColor("#ff7f00"));
            }
        }
        viewHolder.hTitle.setText(flowHistorie.getUserTrueName());

        viewHolder.hTime.setText(flowHistorie.getCreateTime());
        //viewHolder.hTime.setText(IsToday(approvalBean.getCreateTime())?approvalBean.getCreateTime().split(" ")[1]:approvalBean.getCreateTime().split(" ")[0];
        return view;
    }


    /**
     * 判断是否为今天
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) {
        try
        {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            Date date = null;
            try {
                date = getDateFormat().parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 0) {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();
    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    //实例缓存
    class ViewHolder{
        View hPic1;
        ImageView hPic2;
        View hPic3;
        TextView hPic;
        TextView hTitle;
        TextView hType;
        TextView hTime;
    }
}
