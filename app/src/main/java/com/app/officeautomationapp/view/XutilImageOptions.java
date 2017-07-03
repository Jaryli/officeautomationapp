package com.app.officeautomationapp.view;

import com.app.officeautomationapp.R;

import org.xutils.image.ImageOptions;

/**
 * Created by CS-711701-00027 on 2017/4/14.
 */

public abstract class XutilImageOptions {


    private static ImageOptions options;

    public static ImageOptions getCommonOptions()
    {
        options= new ImageOptions.Builder().setFadeIn(true) //淡入效果
                //ImageOptions.Builder()的一些其他属性：
                //.setCircular(true) //设置图片显示为圆形
                //.setSquare(true) //设置图片显示为正方形
                //setCrop(true).setSize(200,200) //设置大小
                //.setAnimation(animation) //设置动画
                //.setFailureDrawable(Drawable failureDrawable) //设置加载失败的动画
                .setFailureDrawableId(R.mipmap.default_image) //以资源id设置加载失败的动画
                //.setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
                .setLoadingDrawableId(R.mipmap.default_image) //以资源id设置加载中的动画
                //.setIgnoreGif(false) //忽略Gif图片
                //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                //.setRaduis(int raduis) //设置拐角弧度
                //.setUseMemCache(true) //设置使用MemCache，默认true
                .build();
        return options;
    }
}
