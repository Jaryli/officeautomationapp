package com.app.officeautomationapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.MyTaskPostBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/5/10.
 */

public class MyTaskDoActivity extends BaseActivity implements View.OnClickListener {


    private EditText et_jobContent;
    private ImageView ivTaskDoBack;
    private Button btn_post;


    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private Context mContext;
    private int maxSelectNum = 1;// 图片最大可选数量
    private List<LocalMedia> selectMedia = new ArrayList<>();

    private MyTaskPostBean myTaskPostBean=new MyTaskPostBean();
    ProgressDialog progressDialog;
    private int taskId;
    private UserDto userDto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_do);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        taskId=getIntent().getIntExtra("taskId",0);
        ivTaskDoBack=(ImageView)findViewById(R.id.iv_taskdo_back);
        ivTaskDoBack.setOnClickListener(this);
        et_jobContent=(EditText)findViewById(R.id.et_jobContent);

        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);

        mContext = this;
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener,false);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia);
            }
        });

    }

    @Override
    protected void onDestroy() {
        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }


    private void post()
    {

        if(selectMedia.size()>0)
        {
            String str="";
            str=PicBase64Util.encode(selectMedia.get(0).getPath(),20);
            myTaskPostBean.setImagedata(str);

        }
        myTaskPostBean.setTaskId(taskId);
        myTaskPostBean.setTodoContent(et_jobContent.getText().toString());

        Gson gson = new Gson();
        String result = gson.toJson(myTaskPostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.AddTodoDetail);
        Log.i("", "post-url:" + Constants.AddTodoDetail);
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
                    Toast.makeText(MyTaskDoActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    setResult(1, new Intent());
                    MyTaskDoActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(MyTaskDoActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_taskdo_back:
                this.finish();
                break;

            case R.id.btn_post:
                post();
                break;
            default:
                break;
        }
    }






    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
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
                    config.setCopyMode(FunctionConfig.CROP_MODEL_1_1);
                    config.setCompress(false);
                    config.setEnablePixelCompress(true);
                    config.setEnableQualityCompress(true);
                    config.setMaxSelectNum(maxSelectNum);
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
                    if (false) {
                        config.setThemeStyle(ContextCompat.getColor(MyTaskDoActivity.this, R.color.blue));
                        // 可以自定义底部 预览 完成 文字的颜色和背景色
                        if (!false) {
                            // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                            config.setPreviewColor(ContextCompat.getColor(MyTaskDoActivity.this, R.color.white));
                            config.setCompleteColor(ContextCompat.getColor(MyTaskDoActivity.this, R.color.white));
                            config.setPreviewBottomBgColor(ContextCompat.getColor(MyTaskDoActivity.this, R.color.blue));
                            config.setBottomBgColor(ContextCompat.getColor(MyTaskDoActivity.this, R.color.blue));
                        }
                    }
//                    if (false) {
//                        config.setCheckedBoxDrawable(selector);
//                    }

                    // 先初始化参数配置，在启动相册
//                    PictureConfig.init(config);
//                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback);

                    // 只拍照
                    PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback);
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }
    };

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };


}
