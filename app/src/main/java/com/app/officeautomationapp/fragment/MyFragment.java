package com.app.officeautomationapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ManageActivity;
import com.app.officeautomationapp.activity.UserinfoActivity;
import com.app.officeautomationapp.bean.UpdatePhotoPostBean;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.CacheUtils;
import com.app.officeautomationapp.util.CleanUtils;
import com.app.officeautomationapp.util.ImageUtil;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.RoundImageView;
import com.app.officeautomationapp.view.XutilImageOptions;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/3/18.
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    RoundImageView ivMyPhoto;
    private TextView tv_username;
    private TextView tv_department;
    RelativeLayout rl_userinfo;
    RelativeLayout rl_password;
    RelativeLayout rl_manage;
    private UserDto userDto;
    ProgressDialog progressDialog;
    public static final int UPDATE_PHOTO = 1;
    private String photoUrl;

    private RelativeLayout rl_cache;
    private TextView tv_cache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,null);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getContext(),"user");
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        UserInfoBean userInfoBean= (UserInfoBean) SharedPreferencesUtile.readObject(getContext().getApplicationContext(),"userInfo");
        tv_username=(TextView)view.findViewById(R.id.tv_username);
        tv_username.setText(userInfoBean.getUserTrueName());
        tv_department=(TextView)view.findViewById(R.id.tv_department);
        tv_department.setText(userInfoBean.getDeptName());
        ivMyPhoto=(RoundImageView)view.findViewById(R.id.iv_my_photo);
        x.image().bind(ivMyPhoto,userInfoBean.getPhotoUrl(), XutilImageOptions.getCommonOptions());
        ivMyPhoto.setOnClickListener(this);
        rl_userinfo=(RelativeLayout)view.findViewById(R.id.rl_userinfo);
        rl_userinfo.setOnClickListener(this);
        rl_password=(RelativeLayout)view.findViewById(R.id.rl_password);
        rl_password.setOnClickListener(this);
        rl_manage=(RelativeLayout)view.findViewById(R.id.rl_manage);
        rl_manage.setOnClickListener(this);
        rl_cache=(RelativeLayout)view.findViewById(R.id.rl_cache);
        rl_cache.setOnClickListener(this);
        tv_cache=(TextView)view.findViewById(R.id.tv_cache);
        caculateCacheSize();
    }
    //创建一个Handler
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PHOTO:
                    //在这里可以进行UI操作
                    if(photoUrl!=null)
                    {
                        x.image().bind(ivMyPhoto,Constants.IMG_PATH+photoUrl, XutilImageOptions.getCommonOptions());
                    }
                    break;
                case CLEAN_FAIL:
                    Toast.makeText(getActivity(),"清除失败",Toast.LENGTH_SHORT).show();
                    break;
                case CLEAN_SUC:
                    Toast.makeText(getActivity(),"清除成功",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    private List<LocalMedia> selectMedia = new ArrayList<>();
    private void clickMyphoto()
    {
        boolean mode = true;//只能拍照
        if (mode) {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(MyFragment.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量
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
                    .enableCrop(true)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(null)// 是否传入已选图片
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
            PictureSelector.create(MyFragment.this)
                    .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(1)// 最大图片选择数量
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
                    .selectionMedia(selectMedia)// 是否传入已选图片
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择
                    selectMedia = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectMedia) {
                        Log.i("图片-----》", media.getPath());
                    }
//                    ImageUtil.addTimePic(selectMedia,getContext());
                    String photoStr=PicBase64Util.encode(selectMedia.get(0).getCutPath(),50);
                    post(photoStr);
                    break;
            }
        }
    }

    private void post(String photoStr)
    {
        UpdatePhotoPostBean updatePhotoPostBean=new UpdatePhotoPostBean();
        updatePhotoPostBean.setImageData(photoStr);
        Gson gson = new Gson();
        String result = gson.toJson(updatePhotoPostBean);
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.UpdatePhoto);
        Log.i("", "post-url:" + Constants.UpdatePhoto);
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
                    Toast.makeText(getContext(),jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    photoUrl=jsonObject.getString("photo");
                    Message message = new Message();
                    message.what = UPDATE_PHOTO;
                    handler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(getContext(),"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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


    //------****** 缓存相关****----------
    private final int CLEAN_SUC=1001;
    private final int CLEAN_FAIL=1002;
    private void onClickCleanCache() {
        getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearAppCache();
                tv_cache.setText("0KB");
            }
        }).show();
    }
    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }


    private CacheUtils mCacheUtils1;
    private CacheUtils mCacheUtils2;
    String  cachedir=Environment.getExternalStorageDirectory()+ "/oa/";

    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
//        File filesDir = getActivity().getFilesDir();
//        File cacheDir = getActivity().getCacheDir();

        //缓存
//        mCacheUtils1= CacheUtils.getInstance(filesDir);
//        mCacheUtils2= CacheUtils.getInstance(cacheDir);
//        fileSize += mCacheUtils1.getCacheSize();
//        fileSize += mCacheUtils2.getCacheSize();
        fileSize += getDirSize(new File(cachedir));
        if (fileSize > 0)
            cacheSize = formatFileSize(fileSize);
        tv_cache.setText(cacheSize);
    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 清除app缓存
     *
     * @param
     */
    public void clearAppCache() {

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
//                    mCacheUtils1.clear();
//                    mCacheUtils2.clear();
                    CleanUtils.cleanCustomCache(cachedir);
                    msg.what = CLEAN_SUC;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = CLEAN_FAIL;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_userinfo:
                startActivity(new Intent(getActivity(),UserinfoActivity.class));
                break;
            case R.id.rl_password:
                Toast.makeText(getActivity(),"暂未开放!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_manage:
                startActivity(new Intent(getActivity(), ManageActivity.class));
                break;
            case R.id.iv_my_photo:
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
                normalDialog.setIcon(R.mipmap.icon_person_update);
                normalDialog.setTitle("更换头像");
                normalDialog.setMessage("你确定更换头像?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clickMyphoto();
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                // 显示
                normalDialog.show();

                break;
            case R.id.rl_cache:
                onClickCleanCache();
                break;
            default:
                break;
        }
    }
}
