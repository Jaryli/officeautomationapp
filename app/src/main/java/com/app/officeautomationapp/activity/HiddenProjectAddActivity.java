package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.HiddenPorjectPostBean;
import com.app.officeautomationapp.bean.HiddenProjectBean;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.ImageUtil;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CS-711701-00027 on 2017/5/23.
 */

public class HiddenProjectAddActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private LinearLayout llProjectIdSelect;
    private TextView tvHiddenProjectName;
    private EditText etHiddenSupplyName;
    private EditText etHiddenWorkContent;
    private Button btnHiddenPost;




    private int maxSelectNum = 3;// 图片最大可选数量
    private Context mContext;
    private List<LocalMedia> selectMedia1 = new ArrayList<>();
    private RecyclerView recyclerView1;
    private GridImageAdapter adapter1;
    private List<LocalMedia> selectMedia2 = new ArrayList<>();
    private RecyclerView recyclerView2;
    private GridImageAdapter adapter2;
    private List<LocalMedia> selectMedia3 = new ArrayList<>();
    private RecyclerView recyclerView3;
    private GridImageAdapter adapter3;

    private String value;//update or add
    private int pic1=0;//用来标记图标是否有变动，变动就压缩
    private int pic2=0;
    private int pic3=0;
    private UserDto userDto;


    private HiddenPorjectPostBean hiddenPorjectPostBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_project_add);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");

        Intent intent = getIntent();
        value = intent.getStringExtra("method");
        HiddenProjectBean hiddenProjectBean=new HiddenProjectBean();
        boolean b1=false;
        boolean b2=false;
        boolean b3=false;
        if(value.equals("update")) {
            hiddenProjectBean = (HiddenProjectBean) intent.getSerializableExtra("data");
            b1=!"".equals(hiddenProjectBean.getBeforeWorkPhoto());//是否显示删除和增加
            b2=!"".equals(hiddenProjectBean.getWorkingPhoto());
            b3=!"".equals(hiddenProjectBean.getAfterWorkPhoto());
        }
        hiddenPorjectPostBean=new HiddenPorjectPostBean();
        ivBack=(ImageView)findViewById(R.id.iv_hidden_add_back);
        ivBack.setOnClickListener(this);
        llProjectIdSelect=(LinearLayout)findViewById(R.id.ll_project_id_select);
        llProjectIdSelect.setOnClickListener(this);
        tvHiddenProjectName=(TextView)findViewById(R.id.tv_hidden_project_name);
        etHiddenSupplyName=(EditText)findViewById(R.id.et_hidden_supply_name);
        etHiddenWorkContent=(EditText)findViewById(R.id.et_hidden_work_content);
        btnHiddenPost=(Button)findViewById(R.id.btn_hidden_post);
        btnHiddenPost.setOnClickListener(this);

        mContext = this;
        recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
        FullyGridLayoutManager manager1 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(manager1);

        adapter1 = new GridImageAdapter(HiddenProjectAddActivity.this, onAddPicClickListener1);
        adapter1.setList(selectMedia1);
        adapter1.setSelectMax(maxSelectNum);

        recyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectMedia1.size() > 0) {
                    LocalMedia media = selectMedia1.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPicturePreview(position, selectMedia1);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new GridImageAdapter(HiddenProjectAddActivity.this, onAddPicClickListener2);
        adapter2.setList(selectMedia2);
        adapter2.setSelectMax(maxSelectNum);
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectMedia2.size() > 0) {
                    LocalMedia media = selectMedia2.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPicturePreview(position, selectMedia2);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        recyclerView3 = (RecyclerView) findViewById(R.id.recycler3);
        FullyGridLayoutManager manager3 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(manager3);
        adapter3 = new GridImageAdapter(HiddenProjectAddActivity.this, onAddPicClickListener3);
        adapter3.setList(selectMedia3);
        adapter3.setSelectMax(maxSelectNum);
        recyclerView3.setAdapter(adapter3);
        adapter3.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectMedia3.size() > 0) {
                    LocalMedia media = selectMedia3.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPicturePreview(position, selectMedia3);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(HiddenProjectAddActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });



        if(value.equals("update"))
        {
            tvHiddenProjectName.setText(hiddenProjectBean.getProjectName());
            etHiddenWorkContent.setText(hiddenProjectBean.getWorkContent());
            etHiddenSupplyName.setText(hiddenProjectBean.getSupplyName());
            hiddenPorjectPostBean.setId(hiddenProjectBean.getId());
            hiddenPorjectPostBean.setProjectId(hiddenProjectBean.getProjectId());

            if(!hiddenProjectBean.getBeforeWorkPhoto().equals("")&&hiddenProjectBean.getBeforeWorkPhoto()!=null) {
                for(String s:hiddenProjectBean.getBeforeWorkPhotoStr())
                {
//                    dowloadPic(s, hiddenProjectBean.getBeforeWorkPhoto());
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressed(false);
//                    localMedia.setPath(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getBeforeWorkPhoto());
//                    localMedia.setThumbnails(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getBeforeWorkPhoto());
                    localMedia.setPath(s);
                    localMedia.setCompressPath(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setMimeType(1);
                    selectMedia1.add(localMedia);
                }
                adapter1.setList(selectMedia1);
                adapter1.notifyDataSetChanged();
                pic1=1;
            }
            if(!hiddenProjectBean.getWorkingPhoto().equals("")&&hiddenProjectBean.getWorkingPhoto()!=null) {
                for(String s:hiddenProjectBean.getWorkingPhotoStr())
                {
//                    dowloadPic(s, hiddenProjectBean.getWorkingPhoto());
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressed(false);
//                    localMedia.setPath(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getWorkingPhoto());
//                    localMedia.setThumbnails(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getWorkingPhoto());
                    localMedia.setPath(s);
                    localMedia.setCompressPath(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setMimeType(1);
                    selectMedia2.add(localMedia);
                }
                adapter2.setList(selectMedia2);
                adapter2.notifyDataSetChanged();
                pic2=1;
            }

            if(!hiddenProjectBean.getAfterWorkPhoto().equals("")&&hiddenProjectBean.getAfterWorkPhoto()!=null) {
                for(String s:hiddenProjectBean.getAfterWorkPhotoStr()) {
//                    dowloadPic(s, hiddenProjectBean.getAfterWorkPhoto());

                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressed(false);
//                    localMedia.setPath(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getAfterWorkPhoto());
//                    localMedia.setThumbnails(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getAfterWorkPhoto());
                    localMedia.setPath(s);
                    localMedia.setCompressPath(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setMimeType(1);
                    selectMedia3.add(localMedia);
                }
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
                pic3=1;
            }

        }
    }



    //下载缓存图片
//    private void dowloadPic(String downloadUrl,String saveFileName)
//    {
//        XDownloadUtil.DownLoadFile(downloadUrl, saveFileName, new MyCallBack<File>() {
//
//            @Override
//            public void onSuccess(File result) {
//                super.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                super.onError(ex, isOnCallback);
//                Toast.makeText(HiddenProjectAddActivity.this, "图片加载失败",Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }



    private GridImageAdapter.onAddPicClickListener onAddPicClickListener1 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = false;//只能拍照
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
//                        .selectionMedia(selectMedia)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(null)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        }

    };
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = false;//只能拍照
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
//                        .selectionMedia(selectMedia)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST+2);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(null)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST+2);//结果回调onActivityResult code
            }
        }

    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            boolean mode = false;//只能拍照
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .selectionMode(PictureConfig.MULTIPLE)// 多选PictureConfig.MULTIPLE or 单选PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .synOrAsy(true)//同步true或异步false 压缩 默认同步
                        //.compressSavePath(getPath())//压缩图片保存地址
                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
//                        .selectionMedia(selectMedia)// 是否传入已选图片
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()//显示多少秒以内的视频or音频也可适用
                        //.recordVideoSecond()//录制视频秒数 默认60s
                        .forResult(PictureConfig.CHOOSE_REQUEST+3);//结果回调onActivityResult code
            } else {
                // 单独拍照
                PictureSelector.create(HiddenProjectAddActivity.this)
                        .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .enablePreviewAudio(true) // 是否可播放音频
                        .isCamera(true)// 是否显示拍照按钮
                        .enableCrop(false)// 是否裁剪
                        .compress(true)// 是否压缩
                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .withAspectRatio(16, 9)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                        .circleDimmedLayer(false)// 是否圆形裁剪
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .openClickSound(false)// 是否开启点击声音
                        .selectionMedia(null)// 是否传入已选图片
                        .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                        //.rotateEnabled() // 裁剪是否可旋转图片
                        //.scaleEnabled()// 裁剪是否可放大缩小图片
                        //.videoQuality()// 视频录制质量 0 or 1
                        //.videoSecond()////显示多少秒以内的视频or音频也可适用
                        .forResult(PictureConfig.CHOOSE_REQUEST+3);//结果回调onActivityResult code
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectMediabBack1 = PictureSelector.obtainMultipleResult(data);
                    if(selectMediabBack1!=null&&selectMediabBack1.size()>0)
                    {
                        ImageUtil.addTimePic(selectMediabBack1,HiddenProjectAddActivity.this);
                        selectMedia1.add(selectMediabBack1.get(0));
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectMedia1) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter1.setList(selectMedia1);
                    adapter1.notifyDataSetChanged();
                    break;
                case PictureConfig.CHOOSE_REQUEST+2:
                    // 图片选择结果回调
                    List<LocalMedia> selectMediabBack2 = PictureSelector.obtainMultipleResult(data);
                    if(selectMediabBack2!=null&&selectMediabBack2.size()>0)
                    {
                        ImageUtil.addTimePic(selectMediabBack2,HiddenProjectAddActivity.this);
                        selectMedia2.add(selectMediabBack2.get(0));
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectMedia2) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter2.setList(selectMedia2);
                    adapter2.notifyDataSetChanged();
                    break;
                case PictureConfig.CHOOSE_REQUEST+3:
                    // 图片选择结果回调
                    List<LocalMedia> selectMediabBack3 = PictureSelector.obtainMultipleResult(data);
                    if(selectMediabBack3!=null&&selectMediabBack3.size()>0)
                    {
                        ImageUtil.addTimePic(selectMediabBack3,HiddenProjectAddActivity.this);
                        selectMedia3.add(selectMediabBack3.get(0));
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectMedia3) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter3.setList(selectMedia3);
                    adapter3.notifyDataSetChanged();
                    break;
            }
        }
    }


    private void post()
    {
        if(selectMedia1.size()>0&&pic1==0)
        {
            String[] str=new String[selectMedia1.size()];
            for(int i=0;i<selectMedia1.size();i++)
            {
                str[i]=PicBase64Util.encode(selectMedia1.get(i).getPath(),20);
            }
            hiddenPorjectPostBean.setBeforeWorkPhoto(str);

        }
        if(selectMedia2.size()>0&&pic2==0) {
            String[] str=new String[selectMedia2.size()];
            for(int i=0;i<selectMedia2.size();i++)
            {
                str[i]=PicBase64Util.encode(selectMedia2.get(i).getPath(),20);
            }
            hiddenPorjectPostBean.setWorkingPhoto(str);
        }
        if(selectMedia3.size()>0&&pic3==0) {
            String[] str=new String[selectMedia3.size()];
            for(int i=0;i<selectMedia3.size();i++)
            {
                str[i]=PicBase64Util.encode(selectMedia3.get(i).getPath(),20);
            }
            hiddenPorjectPostBean.setAfterWorkPhoto(str);
        }
//        if(value.equals("update")){//更新
//            if(selectMedia1.size()>0&&pic1>0) {//改变图了
//                hiddenPorjectPostBean.setBeforeWorkPhoto(PicBase64Util.encode(selectMedia1.get(0).getCompressPath(),20));
//            }
//            else if(selectMedia1.size()>0&&pic1==0) {//图没变
//                hiddenPorjectPostBean.setBeforeWorkPhoto(PicBase64Util.encode(selectMedia1.get(0).getPath(),100));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setBeforeWorkPhoto("");
//            }
//            if(selectMedia2.size()>0&&pic2>0) {
//                hiddenPorjectPostBean.setWorkingPhoto(PicBase64Util.encode(selectMedia2.get(0).getCompressPath(),20));
//            }
//            else if(selectMedia2.size()>0&&pic2==0) {
//                hiddenPorjectPostBean.setWorkingPhoto(PicBase64Util.encode(selectMedia2.get(0).getPath(),100));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setWorkingPhoto("");
//            }
//            if(selectMedia3.size()>0&&pic3>0) {
//                hiddenPorjectPostBean.setAfterWorkPhoto(PicBase64Util.encode(selectMedia3.get(0).getCompressPath(),20));
//            }
//            else if(selectMedia3.size()>0&&pic3==0) {
//                hiddenPorjectPostBean.setAfterWorkPhoto(PicBase64Util.encode(selectMedia3.get(0).getPath(),100));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setAfterWorkPhoto("");
//            }
//        }
//        else
//        {
//            if(selectMedia1.size()>0) {
//                hiddenPorjectPostBean.setBeforeWorkPhoto(PicBase64Util.encode(selectMedia1.get(0).getCompressPath(),20));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setBeforeWorkPhoto("");
//            }
//            if(selectMedia2.size()>0) {
//                hiddenPorjectPostBean.setWorkingPhoto(PicBase64Util.encode(selectMedia2.get(0).getCompressPath(),20));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setWorkingPhoto("");
//            }
//            if(selectMedia3.size()>0) {
//                hiddenPorjectPostBean.setAfterWorkPhoto(PicBase64Util.encode(selectMedia3.get(0).getCompressPath(),20));
//            }
//            else
//            {
//                hiddenPorjectPostBean.setAfterWorkPhoto("");
//            }
//        }

        hiddenPorjectPostBean.setSupplyName(etHiddenSupplyName.getText().toString());
        hiddenPorjectPostBean.setWorkContent(etHiddenWorkContent.getText().toString());


        Gson gson = new Gson();
        String result = gson.toJson(hiddenPorjectPostBean);
        Log.i("HiddenProjectAdd", "postbody:" + result);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.getSaveHiddenProject);
        Log.i("HiddenProjectAdd", "post-url:" + Constants.getSaveHiddenProject);
        params.addHeader("access_token", userDto.getAccessToken());
        params.setBodyContent("'"+result+"'");
        Log.i("JAVA", "body:" + params.getBodyContent());
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    Toast.makeText(HiddenProjectAddActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    if(re==1) {
                        setResult(5, new Intent());
                        HiddenProjectAddActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(HiddenProjectAddActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
                progressDialog.hide();
            }
        });

    }



    ProgressDialog progressDialog;
    private String[] items;
    private int[] itemsId;
    private void getProjectId()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.getMyProject);
        Log.i("MessageDetailActivity", "post-url:" + Constants.getMyProject);
        params.addHeader("access_token", userDto.getAccessToken());

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(HiddenProjectAddActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        List<MyProjectBean> list=new ArrayList<MyProjectBean>();
                        Type type=new TypeToken<List<MyProjectBean>>(){}.getType();
                        list=gson.fromJson(jsonObject.get("dataList").toString(), type);
                        items=new String[list.size()];
                        itemsId=new int[list.size()];
                        for(int i=0;i<list.size();i++)
                        {
                            items[i]=list.get(i).getProjectName();
                            itemsId[i]=list.get(i).getProjectId();
                        }
                        new AlertDialog.Builder(HiddenProjectAddActivity.this)
                                .setTitle("选择我的工程")
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        Toast.makeText(HiddenProjectAddActivity.this,items[i],Toast.LENGTH_SHORT).show();
                                        tvHiddenProjectName.setText(items[i]);
                                        hiddenPorjectPostBean.setProjectId(itemsId[i]);
                                    }
                                })
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(HiddenProjectAddActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hidden_add_back:
                this.finish();
                break;
            case R.id.ll_project_id_select:
                this.getProjectId();
                break;
            case R.id.btn_hidden_post:
                post();
                break;
            default:
                break;

        }
    }



}
