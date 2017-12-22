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
import com.app.officeautomationapp.bean.MiaomuBean;

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
public class MiaomuAdapter extends ArrayAdapter<MiaomuBean> {

    private int resourceId;

    private ImageOptions options;

    private Context mContext;


    public MiaomuAdapter(Context context, int resource, List<MiaomuBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MiaomuBean miaomuBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hName=(TextView)view.findViewById(R.id.name);
            viewHolder.hUnits=(TextView) view.findViewById(R.id.units);
            viewHolder.hNum=(TextView) view.findViewById(R.id.num);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hName.setText(miaomuBean.getTreeName()==null?"":miaomuBean.getTreeName().toString());
        viewHolder.hUnits.setText(miaomuBean.getUnits()==null?"":miaomuBean.getUnits().toString());
        viewHolder.hNum.setText(miaomuBean.getYSNumInfo()==null?"":miaomuBean.getYSNumInfo().toString());
        return view;
    }


    //实例缓存
    class ViewHolder{
        TextView hName;
        TextView hUnits;
        TextView hNum;
    }
}
