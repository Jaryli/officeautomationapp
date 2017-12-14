package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.AcceptanceAdapter;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.ProjectMiaomuTujianBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class AcceptanceActivity extends BaseActivity implements View.OnClickListener {

    private String type;//miaomu tujian
    private List<View> viewList = new ArrayList<>();//ViewPager数据源
    private AcceptanceAdapter myPagerAdapter;//适配器
    private ViewPager viewPager;
    private ProjectMiaomuTujianBean projectMiaomuTujianBean;
    private int count = 0; //页面展示的数据，无实际作用
    private int totalCount = 0;
    private View positionView;
    private View del;
    private View add;
    private TextView tv_title;
    private Integer projectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);

        Intent intent = getIntent();
        projectMiaomuTujianBean = (ProjectMiaomuTujianBean) intent.getSerializableExtra("data");
        type = intent.getStringExtra("type");
        projectId=projectMiaomuTujianBean.getProjectId();
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(type.equals("tujian") ? "土建验收" : "苗木验收");
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

        del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delPage();
            }
        });
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage();
            }
        });
        dealStatusBar(); // 调整状态栏高度
        addPage();
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
     * 该方法封装了添加页面的代码逻辑实现，参数text为要展示的数据
     */
    public void addPage() {
        count++;
        totalCount++;
        LayoutInflater inflater = LayoutInflater.from(this);//获取LayoutInflater的实例
        View view = null;//调用LayoutInflater实例的inflate()方法来加载页面的布局
        if (totalCount == 1) {
            view = inflater.inflate(R.layout.activity_acceptance_view, null);
            TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
            textView.setText(type.equals("tujian") ? "土建验收表" : "苗木验收表");//展示数据
            TextView projectName = (TextView) view.findViewById(R.id.projectName);
            projectName.setText(projectMiaomuTujianBean.getProjectName());
            TextView buyCode = (TextView) view.findViewById(R.id.buyCode);
            buyCode.setText(projectMiaomuTujianBean.getApplyCode());
            final Button btn_post = (Button) view.findViewById(R.id.btn_post);
            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //post
                    addPage();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    btn_post.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            view = inflater.inflate(R.layout.activity_acceptance_view2, null);
            TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
            textView.setText(type.equals("tujian") ? "土建验收明细单" : "苗木验收明细单");//展示数据

            final Button btn_post = (Button) view.findViewById(R.id.btn_post);
            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //post
                    addPage();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    btn_post.setVisibility(View.INVISIBLE);
                }
            });
        }

        viewList.add(view);//为数据源添加一项数据
        myPagerAdapter.notifyDataSetChanged();//通知UI更新
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //更新UI
            switch (msg.what)
            {
                case 1:
                    viewPager.setCurrentItem(totalCount-1);
                    break;
            }

        }
    };

    /**
     * 删除当前页面
     */
    public void delPage() {
        int position = viewPager.getCurrentItem();//获取当前页面位置
        if (position == 0) {
            Toast.makeText(this, "不能删除头页哦！", Toast.LENGTH_SHORT).show();
        } else {
            totalCount--;
            viewList.remove(position);//删除一项数据源中的数据
            myPagerAdapter.notifyDataSetChanged();//通知UI更新
        }

    }

    @Override
    public void onClick(View view) {

    }
}
