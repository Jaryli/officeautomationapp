package com.app.officeautomationapp.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.TheMediaAdapter;
import com.app.officeautomationapp.bean.AcceptanceItem;
import com.app.officeautomationapp.fragment.CommonFragment;
import com.app.officeautomationapp.util.CustPagerTransformer;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by pc on 2017/12/4.
 */

public class AcceptanceActivity  extends BaseActivity  {

    private static TextView indicatorTv;
    private View positionView;
    private static ViewPager viewPager;
    private static ArrayList<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用

    public static ArrayList<AcceptanceItem> fragementsData=new ArrayList<>();//ViewPager数据

    private View del;
    private View add;

    static TheMediaAdapter theAdapter;
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

        del= findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragments.remove(viewPager.getCurrentItem());
//                fragments.get(viewPager.getCurrentItem()-1).dragLayout.computeScroll();
                fragementsData.remove(viewPager.getCurrentItem());
                theAdapter.notifyDataSetChanged();
                viewPager.setAdapter(theAdapter);
                viewPager.setCurrentItem(currentItem);
                updateIndicatorTv();
            }
        });
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragments.add(new CommonFragment());
                fragementsData.add(new AcceptanceItem("bbb",viewPager.getAdapter().getCount()));
                theAdapter.notifyDataSetChanged();
                viewPager.setAdapter(theAdapter);
                viewPager.setCurrentItem(currentItem);
                updateIndicatorTv();
            }
        });
        dealStatusBar(); // 调整状态栏高度

        // 2. 初始化ImageLoader
        initImageLoader();

        // 3. 填充ViewPager
        fillViewPager();
    }
    static int currentItem = 0;
    int size=1;

    public static void refresh(int position,String str)
    {
        currentItem++;
        fragementsData.get(position).sethProjectType(str);
        fragments.add(new CommonFragment());
        fragementsData.add(new AcceptanceItem("ccc",viewPager.getAdapter().getCount()));

        viewPager.setAdapter(theAdapter);
        viewPager.setCurrentItem(currentItem);
        theAdapter.notifyDataSetChanged();
        updateIndicatorTv();
//        theAdapter.notifyDataSetChanged();
    }
    /**
     * 填充ViewPager
     */
    private void fillViewPager() {
        indicatorTv = (TextView) findViewById(R.id.indicator_tv);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // 1. viewPager添加parallax效果，使用PageTransformer就足够了
        viewPager.setPageTransformer(false, new CustPagerTransformer(this));

        // 2. viewPager添加adapter
        for (int i = 0; i < size; i++) {
            // 预先准备1个fragment
            fragments.add(new CommonFragment());
            fragementsData.add(new AcceptanceItem("aaa",0));
        }
        theAdapter=new TheMediaAdapter(getSupportFragmentManager(),fragments,fragementsData);
        viewPager.setAdapter(theAdapter);


        // 3. viewPager滑动时，调整指示器
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentItem =position;
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }
    /**
     * 更新指示器
     */
    private static void updateIndicatorTv() {
        int totalNum = viewPager.getAdapter().getCount();
        int currentItem= viewPager.getCurrentItem() + 1;
        indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
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

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024) // 缓冲大小
                .discCacheFileCount(100) // 缓冲文件数目
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        // 2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
}
