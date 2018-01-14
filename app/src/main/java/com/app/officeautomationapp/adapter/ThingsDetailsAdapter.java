package com.app.officeautomationapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ItemContactsActivity;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.bean.ThingBean;
import com.app.officeautomationapp.view.SpinnerDialogThingsDetails;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;

/**
 * Created by yu on 2017/7/15.
 */

public class ThingsDetailsAdapter extends ArrayAdapter<ThingBean> {

    private int resourceId;
    private Activity mContext;
    ArrayList<ThingBean> mList;
    private SpinnerDialogThingsDetails dialog;

    public ThingsDetailsAdapter(Activity context, int resource,ArrayList<ThingBean> objects,SpinnerDialogThingsDetails spinnerDialogThingsDetails) {
        super(context, resource,objects);
        this.mList = objects;
        this.mContext=context;
        this.dialog=spinnerDialogThingsDetails;
        resourceId=resource;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ThingBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ThingBean thingBean=getItem(position);//获得实例
        View view;
        ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
        view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        viewHolder=new ViewHolder();
        viewHolder.hname=(TextView)view.findViewById(R.id.name);
        viewHolder.hnum=(TextView)view.findViewById(R.id.num);
        viewHolder.hunits=(TextView)view.findViewById(R.id.units);
        viewHolder.hprice=(TextView)view.findViewById(R.id.price);
        viewHolder.hno=(TextView)view.findViewById(R.id.no);
        viewHolder.hsel=(TextView)view.findViewById(R.id.sel);
        viewHolder.hsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent =mContext.getIntent();
                //这里使用bundle绷带来传输数据
                Bundle bundle =new Bundle();
                //传输的内容仍然是键值对的形式
                bundle.putInt("data_thing_id", thingBean.getResId());//回发的消息
                bundle.putString("data_thing_name", thingBean.getResName()+"-"+thingBean.getSpecialModel());
                intent.putExtras(bundle);
                mContext.setResult(mContext.RESULT_OK,intent);
                dialog.closeSpinerDialog();
                mContext.finish();
                }
            });
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hname.setText(thingBean.getCateName());
        viewHolder.hnum.setText(thingBean.getStockNum()+"");
        viewHolder.hunits.setText(thingBean.getUnits());
        viewHolder.hprice.setText(thingBean.getPriceInfo()+"");
        viewHolder.hno.setText(thingBean.getResCode());

        return view;

    }

    public void refresh(ArrayList<ThingBean> list) {
        mList = list;
        notifyDataSetChanged();
    }


    //实例缓存
    class ViewHolder{
        TextView hname;
        TextView hnum;
        TextView hunits;
        TextView hprice;
        TextView hno;
        TextView hsel;
    }

}
