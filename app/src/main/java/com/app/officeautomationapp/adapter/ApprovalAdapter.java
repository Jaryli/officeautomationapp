package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.ApprovalBean;
import com.app.officeautomationapp.bean.MessageBean;

import org.xutils.image.ImageOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/2/27.
 */
public class ApprovalAdapter extends ArrayAdapter<ApprovalBean> {

    private int resourceId;

    private Context mContext;


    public ApprovalAdapter(Context context, int resource, List<ApprovalBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ApprovalBean approvalBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
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
        //xutil2.0 废弃
        //xUtilsImageLoader imageLoader=new xUtilsImageLoader(mContext);
        //viewHolder.hPic.setImageDrawable(imageLoader.display(viewHolder.hPic,hiddenProjectBean.getPic()));

//        x.image().bind(viewHolder.hPic, messageBean.getPic(), options);

        viewHolder.hPic.setText(getPicText(approvalBean.getAFF_Name()));
        viewHolder.hTitle.setText(approvalBean.getAFW_Name().length()>22?approvalBean.getAFW_Name().substring(0,20)+"...":approvalBean.getAFW_Name());
        viewHolder.hType.setText(approvalBean.getAFF_Name());
        viewHolder.hTime.setText("");
        //viewHolder.hTime.setText(IsToday(approvalBean.getCreateTime())?approvalBean.getCreateTime().split(" ")[1]:approvalBean.getCreateTime().split(" ")[0]);
        return view;
    }

    private String getPicText(String str)
    {
        if(str.indexOf("请假")>-1)
        {
            return "假";
        }
        else if(str.indexOf("用章")>-1)
        {
            return "章";
        }
        else if(str.indexOf("用工")>-1)
        {
            return "工";
        }
        else
        {
            return str.substring(0,1);
        }
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
        TextView hPic;
        TextView hTitle;
        TextView hType;
        TextView hTime;
    }
}
