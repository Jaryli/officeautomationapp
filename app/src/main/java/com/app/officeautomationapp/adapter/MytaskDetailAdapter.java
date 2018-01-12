package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ViewPagerActivity;
import com.app.officeautomationapp.bean.MyTaskBean;
import com.app.officeautomationapp.bean.MyTaskDoDetailsBean;

import org.xutils.image.ImageOptions;
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
public class MytaskDetailAdapter extends ArrayAdapter<MyTaskDoDetailsBean> {

    private int resourceId;

    private ImageOptions options;
    private Context context;

    public MytaskDetailAdapter(Context context, int resource, List<MyTaskDoDetailsBean> objects) {
        super(context, resource,objects);
        this.context=context;
        resourceId=resource;

        options= new ImageOptions.Builder().setFadeIn(true) //淡入效果
                //ImageOptions.Builder()的一些其他属性：
                .setCircular(false) //设置图片显示为圆形
                //.setSquare(true) //设置图片显示为正方形
                //setCrop(true).setSize(200,200) //设置大小
                //.setAnimation(animation) //设置动画
//        .setFailureDrawable(gifDrawable) //设置加载失败的动画
                .setFailureDrawableId(R.mipmap.default_image_circle) //以资源id设置加载失败的动画
//        .setLoadingDrawable(gifDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.mipmap.default_image_circle) //以资源id设置加载中的动画
                //.setIgnoreGif(false) //忽略Gif图片
                //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                //.setRaduis(int raduis) //设置拐角弧度
                //.setUseMemCache(true) //设置使用MemCache，默认true
                .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyTaskDoDetailsBean myTaskDoDetailsBean=getItem(position);//获得实例
        View view;
        final ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hName=(TextView)view.findViewById(R.id.name);
            viewHolder.hTime=(TextView) view.findViewById(R.id.time);
            viewHolder.hContent=(TextView) view.findViewById(R.id.content);
            viewHolder.hPic=(ImageView) view.findViewById(R.id.pic);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hName.setText(myTaskDoDetailsBean.getUserTrueName());
        viewHolder.hTime.setText(myTaskDoDetailsBean.getCreateTime());
//        viewHolder.hTime.setText(IsToday(myTaskDoDetailsBean.getCreateTime())?myTaskDoDetailsBean.getCreateTime().split(" ")[1]:myTaskDoDetailsBean.getCreateTime().split(" ")[0]);
        viewHolder.hContent.setText(myTaskDoDetailsBean.getTodoContent());
        x.image().bind(viewHolder.hPic, myTaskDoDetailsBean.getImageUrlStr(), options);
        viewHolder.hPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = new String[1];
                str[0]=myTaskDoDetailsBean.getImageUrlStr();
                Intent intent=new Intent(context,ViewPagerActivity.class);
                intent.putExtra("imageUrlLists",str);
                context.startActivity(intent);
            }
        });
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
        TextView hName;
        TextView hTime;
        TextView hContent;
        ImageView hPic;
    }
}
