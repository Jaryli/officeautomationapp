package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.MyTaskBean;

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
public class MytaskAdapter extends ArrayAdapter<MyTaskBean> {

    private int resourceId;


    public MytaskAdapter(Context context, int resource, List<MyTaskBean> objects) {
        super(context, resource,objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyTaskBean myTaskBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hPic=(TextView)view.findViewById(R.id.tv_task_p);
            viewHolder.hTitle=(TextView) view.findViewById(R.id.tv_task_title);
            viewHolder.hTime=(TextView) view.findViewById(R.id.tv_task_time);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hPic.setText(myTaskBean.getTaskStatusStr());
        viewHolder.hTitle.setText(myTaskBean.getTaskName().length()>9?myTaskBean.getTaskName().substring(0,9)+"...":myTaskBean.getTaskName());
        viewHolder.hTime.setText(IsToday(myTaskBean.getStartDate())?myTaskBean.getStartDate().split(" ")[1]:myTaskBean.getStartDate().split(" ")[0]);
        return view;
    }

    /**
     * 判断是否为今天
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) {

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
        TextView hTime;
        //ImageView detailImage;
    }
}
