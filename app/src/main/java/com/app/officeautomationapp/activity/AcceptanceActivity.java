package com.app.officeautomationapp.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.AcceptanceAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class AcceptanceActivity extends BaseActivity implements View.OnClickListener {


    private List<View> viewList = new ArrayList<>();//ViewPager数据源
    private AcceptanceAdapter myPagerAdapter;//适配器
    private ViewPager viewPager;
    private int count = 0; //页面展示的数据，无实际作用
    private int totalCount = 0;
    private View positionView;
    private View del;
    private View add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        // 1. 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow()
                        .getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        positionView = findViewById(R.id.position_view);
        myPagerAdapter = new AcceptanceAdapter(viewList);//创建适配器实例
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(myPagerAdapter);//为ViewPager设置适配器
        viewPager.setOffscreenPageLimit(3);

        del= findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delPage();
            }
        });
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "页面" + count;
                count++;
                totalCount++;
                addPage(text);
            }
        });
        dealStatusBar(); // 调整状态栏高度
        init();
    }

    private void init()
    {
        String text = "页面" + count;
        count++;
        totalCount++;
        addPage(text);
    }


    /**
     * 调整沉浸式菜单的title
     */
    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = positionView.getLayoutParams();
            lp.height = statusBarHeight;
            positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 为选项菜单设置点击事件监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1://添加页面的点击事件
                String text = "页面" + count;
                count++;
                totalCount++;
                addPage(text);
                break;
            case 2://删除页面的点击事件
                delPage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     *该方法封装了添加页面的代码逻辑实现，参数text为要展示的数据
     */
    public void addPage(String text){
        LayoutInflater inflater = LayoutInflater.from(this);//获取LayoutInflater的实例
        View view = null;//调用LayoutInflater实例的inflate()方法来加载页面的布局
        if(totalCount==1)
        {
            view =inflater.inflate(R.layout.activity_acceptance_view, null);
            TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
            textView.setText(text);//展示数据

        }
        else
        {
            view =inflater.inflate(R.layout.activity_acceptance_view2, null);
            TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
            textView.setText(text);//展示数据
        }

        viewList.add(view);//为数据源添加一项数据
        myPagerAdapter.notifyDataSetChanged();//通知UI更新
    }
    /**
     * 删除当前页面
     */
    public void delPage(){
        int position = viewPager.getCurrentItem();//获取当前页面位置
        if(position==0)
        {
            Toast.makeText(this,"不能删除头页哦！",Toast.LENGTH_SHORT).show();
        }
        else {
            totalCount--;
            viewList.remove(position);//删除一项数据源中的数据
            myPagerAdapter.notifyDataSetChanged();//通知UI更新
        }

    }

    @Override
    public void onClick(View view) {

    }
}
