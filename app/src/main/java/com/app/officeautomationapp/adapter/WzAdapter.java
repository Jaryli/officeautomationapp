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
import com.app.officeautomationapp.bean.WzBean;

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
public class WzAdapter extends ArrayAdapter<WzBean> {

    private int resourceId;

    private Context mContext;


    public WzAdapter(Context context, int resource, List<WzBean> objects) {
        super(context, resource,objects);
        this.mContext=context;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WzBean wzBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hname=(TextView)view.findViewById(R.id.name);
            viewHolder.hnum=(TextView) view.findViewById(R.id.num);
            viewHolder.hsnum=(TextView) view.findViewById(R.id.snum);
            viewHolder.hxj=(TextView) view.findViewById(R.id.xj);
            viewHolder.hheigth=(TextView) view.findViewById(R.id.heigth);
            viewHolder.hpj=(TextView) view.findViewById(R.id.pj);
            viewHolder.hunit=(TextView) view.findViewById(R.id.unit);
            viewHolder.hcheckedNum=(TextView)view.findViewById(R.id.checkedNum);
            view.setTag(viewHolder);
        }else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.hname.setText(wzBean.getTreeName());
        viewHolder.hnum.setText(wzBean.getACNumInfo()+"");
        viewHolder.hsnum.setText(wzBean.getApplyMainCode());
        viewHolder.hxj.setText(wzBean.getACXiongJing());
        viewHolder.hheigth.setText(wzBean.getACHeight());
        viewHolder.hpj.setText(wzBean.getACPengXing());
        viewHolder.hunit.setText(wzBean.getUnits());
        viewHolder.hcheckedNum.setText(wzBean.getCheckedNum()+"");
        return view;
    }

    //实例缓存
    class ViewHolder{
        TextView hname;
        TextView hnum;
        TextView hsnum;
        TextView hxj;
        TextView hheigth;
        TextView hpj;
        TextView hunit;
        TextView hcheckedNum;
    }
}
