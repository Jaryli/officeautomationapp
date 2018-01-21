package com.app.officeautomationapp.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.WorkFileBean;
import com.bm.library.PhotoView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/9/6.
 */

public class ViewPagerActivity extends BaseActivity {

    private ViewPager mPager;

    private ImageOptions options;
    private Button dingwei;

//    private int[] imgsId = new int[]{R.mipmap.aaa, R.mipmap.bbb, R.mipmap.ccc, R.mipmap.ddd, R.mipmap.ic_launcher, R.mipmap.image003};

//    String[] imageUrlLists={"http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg","http://image.tianjimedia.com/uploadImages/2014/289/01/IGS09651F94M.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_pager);
        options= new ImageOptions.Builder().setFadeIn(true) //淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(false) //设置图片显示为圆形
                //.setSquare(true) //设置图片显示为正方形
                //setCrop(true).setSize(200,200) //设置大小
                //.setAnimation(animation) //设置动画
//        .setFailureDrawable(gifDrawable) //设置加载失败的动画
                .setFailureDrawableId(R.mipmap.default_image_circle) //以资源id设置加载失败的动画
//        .setLoadingDrawable(gifDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.mipmap.default_image_circle) //以资源id设置加载中的动画
                //.setIgnoreGif(false) //忽略Gif图片
                //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                //.setRaduis(int raduis) //设置拐角弧度
                //.setUseMemCache(true) //设置使用MemCache，默认true
                .build();
        dingwei=(Button)findViewById(R.id.dingwei);
        int isWithpos=getIntent().getIntExtra("isWithpos",0);
        String[] imageUrlLists ;
        List<WorkFileBean> list=new ArrayList<>();
        if(isWithpos==1)
        {
           list= (List<WorkFileBean>) getIntent().getSerializableExtra("listWorkFileBean");
            imageUrlLists = new String[list.size()];
            for(int i=0;i<list.size();i++)
            {
                imageUrlLists[i]=list.get(i).getFileImageStr();
            }
        }
        else
        {
            dingwei.setVisibility(View.GONE);
            imageUrlLists=getIntent().getStringArrayExtra("imageUrlLists");
        }


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        final String[] finalImageUrlLists = imageUrlLists;
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return finalImageUrlLists.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ViewPagerActivity.this);
                view.enable();
                view.setScaleType(ImageView.ScaleType.CENTER);
//                view.setImageResource(imageUrlLists[position]);
                x.image().bind(view, finalImageUrlLists[position], options);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        final List<WorkFileBean> finalList = list;
        dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewPagerActivity.this,MapActivity.class);
                intent.putExtra("lon", finalList.get(mPager.getCurrentItem()).getLon());
                intent.putExtra("lati", finalList.get(mPager.getCurrentItem()).getLati());
                startActivity(intent);
            }
        });
    }

}
