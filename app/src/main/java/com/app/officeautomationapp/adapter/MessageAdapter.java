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
public class MessageAdapter extends ArrayAdapter<MessageBean> {

    private int resourceId;

    private ImageOptions options;

    private Context mContext;


    public MessageAdapter(Context context, int resource, List<MessageBean> objects) {
        super(context, resource,objects);
        //x.Ext.init(this.getApplication());
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        this.mContext=context;
        resourceId=resource;





        options= new ImageOptions.Builder().setFadeIn(true) //淡入效果
        //ImageOptions.Builder()的一些其他属性：
        .setCircular(true) //设置图片显示为圆形
        //.setSquare(true) //设置图片显示为正方形
        //setCrop(true).setSize(200,200) //设置大小
        //.setAnimation(animation) //设置动画
//        .setFailureDrawable(gifDrawable) //设置加载失败的动画
        .setFailureDrawableId(R.mipmap.default_image) //以资源id设置加载失败的动画
//        .setLoadingDrawable(gifDrawable) //设置加载中的动画
        .setLoadingDrawableId(R.mipmap.default_image) //以资源id设置加载中的动画
        //.setIgnoreGif(false) //忽略Gif图片
        //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
        //.setRaduis(int raduis) //设置拐角弧度
        //.setUseMemCache(true) //设置使用MemCache，默认true
         .build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageBean messageBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hPic=(ImageView)view.findViewById(R.id.message_pic);
            viewHolder.hTitle=(TextView) view.findViewById(R.id.message_title);
            viewHolder.hType=(TextView) view.findViewById(R.id.message_type);
            viewHolder.hTime=(TextView) view.findViewById(R.id.message_time);
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
        if(messageBean.getIsRead()==1)
        {
            viewHolder.hPic.setImageResource(R.mipmap.icon_mail_normal2);
        }
        else
        {
            viewHolder.hPic.setImageResource(R.mipmap.icon_mail_normal);
        }

        viewHolder.hTitle.setText(messageBean.getNT_Title().length()>9?messageBean.getNT_Title().substring(0,9)+"...":messageBean.getNT_Title());
        viewHolder.hType.setText(messageBean.getNT_DeptName());
        viewHolder.hTime.setText(IsToday(messageBean.getNT_Date())?messageBean.getNT_Date().split(" ")[1]:messageBean.getNT_Date().split(" ")[0]);
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
        ImageView hPic;
        TextView hTitle;
        TextView hType;
        TextView hTime;
        //ImageView detailImage;
    }
}
