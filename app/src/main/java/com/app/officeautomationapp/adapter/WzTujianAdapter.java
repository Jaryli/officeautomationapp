package com.app.officeautomationapp.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.WzBean;
import com.app.officeautomationapp.bean.WzTujianBean;

import java.util.List;

/**
 * Created by yu on 2017/2/27.
 */
public class WzTujianAdapter extends ArrayAdapter<WzTujianBean> {

    private int resourceId;

    private Context mContext;


    public WzTujianAdapter(Context context, int resource, List<WzTujianBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WzTujianBean wzBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hname=(TextView)view.findViewById(R.id.name);
            viewHolder.hnum=(TextView) view.findViewById(R.id.num);
            viewHolder.units=(TextView) view.findViewById(R.id.units);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.hname.setText(wzBean.getCivilName());
        viewHolder.hnum.setText(wzBean.getCivilNum()+"");
        viewHolder.units.setText(wzBean.getCivilUnits());
        return view;
    }

    //实例缓存
    class ViewHolder{
        TextView hname;
        TextView hnum;
        TextView units;
    }
}
