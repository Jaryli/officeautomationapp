package com.app.officeautomationapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.officeautomationapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CS-711701-00027 on 2017/4/12.
 */

public class ApprovalSelectAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Item> items=new ArrayList<Item>();

    public ApprovalSelectAdapter(Context context,String[] titles,int selectItem)
    {
        super();
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < titles.length; i++)
        {
            Item item= new Item(titles[i],selectItem==i?context.getResources().getDrawable(R.drawable.text_view_used):context.getResources().getDrawable(R.drawable.text_view_normal),selectItem==i?context.getResources().getColor(R.color.ontColor):context.getResources().getColor(R.color.tColor));
            items.add(item);
        }
    }

    public void changeSelected(Context context,int selectItem)
    {
        for (int i = 0; i < items.size(); i++)
        {
            items.get(i).setBackground(context.getResources().getDrawable(R.drawable.text_view_normal));
            items.get(i).setTextColor(context.getResources().getColor(R.color.tColor));
        }

        items.get(selectItem).setBackground(context.getResources().getDrawable(R.drawable.text_view_used));
        items.get(selectItem).setTextColor(context.getResources().getColor(R.color.ontColor));
    }

    @Override
    public int getCount() {
        if (null != items)
        {
            return items.size();
        } else
        {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null)
        {
            view = inflater.inflate(R.layout.activity_approval_select_item, null);
            viewHolder = new ViewHolder();
            viewHolder.rb = (TextView) view.findViewById(R.id.tv1);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.rb.setText(items.get(i).getName());

        viewHolder.rb.setTextColor(items.get(i).getTextColor());
        viewHolder.rb.setBackground(items.get(i).getBackground());
        return view;
    }

    class ViewHolder
    {
        public TextView rb;
    }

    //子类属性定义
    class Item
    {
        private String name;
        private Drawable background;
        private int textColor;

        public Item(String title,Drawable background,int textColor)
        {
            super();
            this.name = title;
            this.background =background;
            this.textColor=textColor;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getBackground() {
            return background;
        }

        public void setBackground(Drawable background) {
            this.background = background;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }
    }
}
