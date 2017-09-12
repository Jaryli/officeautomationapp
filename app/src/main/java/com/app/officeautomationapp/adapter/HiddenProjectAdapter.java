package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.HiddenProjectBean;

import org.xutils.BuildConfig;
import org.xutils.image.GifDrawable;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yu on 2017/2/27.
 */
public class HiddenProjectAdapter  extends ArrayAdapter<HiddenProjectBean> {

    private int resourceId;

    private ImageOptions options;

    private Context mContext;


    public HiddenProjectAdapter(Context context, int resource, List<HiddenProjectBean> objects) {
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
        HiddenProjectBean hiddenProjectBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hPic=(ImageView)view.findViewById(R.id.hidden_project_pic);
            viewHolder.hName=(TextView) view.findViewById(R.id.hidden_project_name);
            viewHolder.hTime=(TextView) view.findViewById(R.id.hidden_project_time);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        //xutil2.0 废弃
        //xUtilsImageLoader imageLoader=new xUtilsImageLoader(mContext);
        //viewHolder.hPic.setImageDrawable(imageLoader.display(viewHolder.hPic,hiddenProjectBean.getPic()));

        String pic ="";
        if(hiddenProjectBean.getBeforeWorkPhoto()!=null&&hiddenProjectBean.getBeforeWorkPhoto()!="")
        {
            pic=hiddenProjectBean.getBeforeWorkPhotoStr()[0];
        }
        if(hiddenProjectBean.getWorkingPhoto()!=null&&hiddenProjectBean.getWorkingPhoto()!="")
        {
            pic=hiddenProjectBean.getWorkingPhotoStr()[0];
        }
        if(hiddenProjectBean.getAfterWorkPhoto()!=null&&hiddenProjectBean.getAfterWorkPhoto()!="")
        {
            pic=hiddenProjectBean.getAfterWorkPhotoStr()[0];
        }

        x.image().bind(viewHolder.hPic, pic, options);
        viewHolder.hName.setText(hiddenProjectBean.getProjectName());
        viewHolder.hTime.setText(hiddenProjectBean.getCreateTime());

        return view;
    }


    //实例缓存
    class ViewHolder{
        ImageView hPic;
        TextView hName;
        TextView hTime;
        //ImageView detailImage;
    }
}
