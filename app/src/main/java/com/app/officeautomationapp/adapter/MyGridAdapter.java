package com.app.officeautomationapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.ProjectItemBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/7/15.
 */

public class MyGridAdapter extends ArrayAdapter<ProjectItemBean> {

    private int resourceId;

    private ImageOptions options;

    private Context mContext;
    ArrayList<ProjectItemBean> mList;

    public MyGridAdapter(Context context, int resource, ArrayList<ProjectItemBean> objects) {
        super(context, resource,objects);
        //x.Ext.init(this.getApplication());
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        this.mList = objects;
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
    public int getCount() {
        return mList.size();
    }

    @Override
    public ProjectItemBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        ProjectItemBean projectItemBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hPic=(ImageView)view.findViewById(R.id.image_item);
            viewHolder.hTitle=(TextView) view.findViewById(R.id.text_item);
            viewHolder.hNum=(TextView) view.findViewById(R.id.item_num);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        if(mList.get(getCount() - position - 1).getLocalPic()!=null&&!"".equals(mList.get(getCount() - position - 1).getLocalPic()))//加载本地图片
        {
            viewHolder.hPic.setBackground(mList.get(getCount() - position - 1).getLocalPic());
        }
        else
        {
            x.image().bind(viewHolder.hPic, mList.get(getCount() - position - 1).getMenuImageStr(), options);
        }
        viewHolder.hTitle.setText(mList.get(getCount() - position - 1).getMenuTitle().toString());
        if(mList.get(getCount() - position - 1).getNum()>0) {
            viewHolder.hNum.setVisibility(View.VISIBLE);
            viewHolder.hNum.setText(mList.get(getCount() - position - 1).getNum()+"");
        }
        return view;
    }

    public void refresh(ArrayList<ProjectItemBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

//    public void update(int index,ListView listview){
//        //得到第一个可见item项的位置
//        int visiblePosition = listview.getFirstVisiblePosition();
//        //得到指定位置的视图，对listview的缓存机制不清楚的可以去了解下
//        View view = listview.getChildAt(index - visiblePosition);
//        ViewHolder holder = (ViewHolder) view.getTag();
//        holder.hNum=(TextView) view.findViewById(R.id.bar_num);
//        setData(holder,index);
//    }
//    private void setData(ViewHolder holder,int index){
//        ProjectItemBean projectItemBean = list.get(index);
//        holder.hNum.setVisibility(View.VISIBLE);
//        holder.hNum.setText(projectItemBean.getNum());
//    }



    //实例缓存
    class ViewHolder{
        ImageView hPic;
        TextView hTitle;
        TextView hNum;
//        TextView hType;
//        TextView hTime;
        //ImageView detailImage;
    }
}
