package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MiaomuBean;
import com.app.officeautomationapp.bean.TujianBean;

import org.xutils.image.ImageOptions;

import java.util.List;

/**
 * Created by yu on 2017/2/27.
 */
public class TujianAdapter extends ArrayAdapter<TujianBean> {

    private int resourceId;

    private ImageOptions options;

    private Context mContext;


    public TujianAdapter(Context context, int resource, List<TujianBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TujianBean tujianBean=getItem(position);//获得实例
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
        viewHolder.hName.setText(tujianBean.getCivilName()==null?"":tujianBean.getCivilName().toString());
        viewHolder.hUnits.setText(tujianBean.getCivilUnits()==null?"":tujianBean.getCivilUnits().toString());
        viewHolder.hNum.setText(tujianBean.getCivilNum()==null?"":tujianBean.getCivilNum().toString());
        return view;
    }


    //实例缓存
    class ViewHolder{
        TextView hName;
        TextView hUnits;
        TextView hNum;
    }
}
