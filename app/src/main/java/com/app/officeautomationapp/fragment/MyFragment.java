package com.app.officeautomationapp.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.WorkQingjiaActivity;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.UpdatePhotoPostBean;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.RoundImageView;
import com.app.officeautomationapp.view.XutilImageOptions;
import com.google.gson.Gson;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

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
    private UserDto userDto;
    ProgressDialog progressDialog;
    public static final int UPDATE_PHOTO = 1;
    private String photoUrl;

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
        rl_userinfo=(RelativeLayout)getActivity().findViewById(R.id.rl_userinfo);
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
                default:
                    break;
            }
        }

    };

    private List<LocalMedia> selectMedia = new ArrayList<>();
    private void clickMyphoto()
    {
        // 进入相册
        /**
         * type --> 1图片 or 2视频
         * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
         * maxSelectNum --> 可选择图片的数量
         * selectMode         --> 单选 or 多选
         * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
         * isPreview    --> 是否打开预览选项
         * isCrop       --> 是否打开剪切选项
         * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
         * ThemeStyle -->主题颜色
         * CheckedBoxDrawable -->图片勾选样式
         * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
         * cropH-->裁剪高度 值不能小于100
         * isCompress -->是否压缩图片
         * setEnablePixelCompress 是否启用像素压缩
         * setEnableQualityCompress 是否启用质量压缩
         * setRecordVideoSecond 录视频的秒数，默认不限制
         * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
         * setImageSpanCount -->每行显示个数
         * setCheckNumMode 是否显示QQ选择风格(带数字效果)
         * setPreviewColor 预览文字颜色
         * setCompleteColor 完成文字颜色
         * setPreviewBottomBgColor 预览界面底部背景色
         * setBottomBgColor 选择图片页面底部背景色
         * setCompressQuality 设置裁剪质量，默认无损裁剪
         * setSelectMedia 已选择的图片
         * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
         * 注意-->type为2时 设置isPreview or isCrop 无效
         * 注意：Options可以为空，默认标准模式
         */
        FunctionConfig config = new FunctionConfig();
        config.setType(1);
        config.setCopyMode(FunctionConfig.CROP_MODEL_3_4);
        config.setCompress(false);
        config.setEnablePixelCompress(true);
        config.setEnableQualityCompress(true);
        config.setMaxSelectNum(1);
        config.setSelectMode(FunctionConfig.MODE_MULTIPLE);
        config.setShowCamera(true);
        config.setEnablePreview(true);
        config.setEnableCrop(false);
        config.setPreviewVideo(true);
        config.setRecordVideoDefinition(FunctionConfig.HIGH);// 视频清晰度
        config.setRecordVideoSecond(60);// 视频秒数
        config.setCropW(0);
        config.setCropH(0);
        config.setMaxB(0);
        config.setCheckNumMode(false);
        config.setCompressQuality(100);
        config.setImageSpanCount(4);
        config.setSelectMedia(selectMedia);
        config.setCompressFlag(1);// 1 系统自带压缩 2 luban压缩
        config.setCompressW(0);
        config.setCompressH(0);
        if (true) {
            config.setThemeStyle(ContextCompat.getColor(getContext(), R.color.blue));
            // 可以自定义底部 预览 完成 文字的颜色和背景色
            if (!false) {
                // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                config.setPreviewColor(ContextCompat.getColor(getContext(), R.color.white));
                config.setCompleteColor(ContextCompat.getColor(getContext(), R.color.white));
                config.setPreviewBottomBgColor(ContextCompat.getColor(getContext(), R.color.blue));
                config.setBottomBgColor(ContextCompat.getColor(getContext(), R.color.blue));
            }
        }
        // 先初始化参数配置，在启动相册
        PictureConfig.init(config);
        PictureConfig.getPictureConfig().openPhoto(getContext(), resultCallback);

    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            //提交
            Log.e("**********",resultList.get(0).getPath());
            String photoStr=PicBase64Util.encode(resultList.get(0).getPath(),20);
            post(photoStr);
        }
    };


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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_userinfo:

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
            default:
                break;
        }
    }
}
