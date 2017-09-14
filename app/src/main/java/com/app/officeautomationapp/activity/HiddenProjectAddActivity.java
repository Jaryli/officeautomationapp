package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.app.officeautomationapp.util.MyCallBack;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.XDownloadUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

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

        adapter1 = new GridImageAdapter(this, onAddPicClickListener1,b1);
        adapter1.setSelectMax(maxSelectNum);
        recyclerView1.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia1);
            }
        });

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new GridImageAdapter(this, onAddPicClickListener2,b2);
        adapter2.setSelectMax(maxSelectNum);
        recyclerView2.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia2);
            }
        });

        recyclerView3 = (RecyclerView) findViewById(R.id.recycler3);
        FullyGridLayoutManager manager3 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(manager3);
        adapter3 = new GridImageAdapter(this, onAddPicClickListener3,b3);
        adapter3.setSelectMax(maxSelectNum);
        recyclerView3.setAdapter(adapter3);
        adapter3.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia3);
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
                    localMedia.setThumbnails(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setType(1);
                    selectMedia1.add(localMedia);
                }
                adapter1.setList(selectMedia1);
                adapter1.notifyDataSetChanged();
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
                    localMedia.setThumbnails(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setType(1);
                    selectMedia2.add(localMedia);
                }
                adapter2.setList(selectMedia2);
                adapter2.notifyDataSetChanged();
            }

            if(!hiddenProjectBean.getAfterWorkPhoto().equals("")&&hiddenProjectBean.getAfterWorkPhoto()!=null) {
                for(String s:hiddenProjectBean.getAfterWorkPhotoStr()) {
//                    dowloadPic(s, hiddenProjectBean.getAfterWorkPhoto());

                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setCompressed(false);
//                    localMedia.setPath(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getAfterWorkPhoto());
//                    localMedia.setThumbnails(XDownloadUtil.IMAGE_SDCARD_MADER + hiddenProjectBean.getAfterWorkPhoto());
                    localMedia.setPath(s);
                    localMedia.setThumbnails(s);
                    localMedia.setNum(1);
                    localMedia.setPosition(1);
                    localMedia.setType(1);
                    selectMedia3.add(localMedia);
                }
                adapter3.setList(selectMedia3);
                adapter3.notifyDataSetChanged();
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
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
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
                    config.setCompressQuality(30);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(selectMedia1);
                    config.setCompressFlag(1);// 1 系统自带压缩 2 luban压缩
                    config.setCompressW(0);
                    config.setCompressH(0);
                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
//                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback1);

                    // 只拍照
                    PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback1);
                    break;
                case 1:
                    // 删除图片
                    selectMedia1.remove(position);
                    adapter1.notifyItemRemoved(position);
                    break;
            }
        }
    };

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback1 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
//            selectMedia1 = resultList;//选址图片的时候
            if(resultList.size()>0)//只拍照的时候
            {
                for(LocalMedia localMedia:resultList)
                {
                    selectMedia1.add(localMedia);
                }
            }
            Log.i("callBack_result", selectMedia1.size() + "");
            if (selectMedia1 != null) {
                adapter1.setList(selectMedia1);
                pic1++;
                adapter1.notifyDataSetChanged();
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
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
                    config.setCompressQuality(30);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(selectMedia2);
                    config.setCompressFlag(1);// 1 系统自带压缩 2 luban压缩
                    config.setCompressW(0);
                    config.setCompressH(0);
                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
//                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback2);

                    // 只拍照
                    PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback2);
                    break;
                case 1:
                    // 删除图片
                    selectMedia2.remove(position);
                    adapter2.notifyItemRemoved(position);
                    break;
            }
        }
    };

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback2 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            //            selectMedia2 = resultList;//选址图片的时候
            if(resultList.size()>0)//只拍照的时候
            {
                for(LocalMedia localMedia:resultList)
                {
                    selectMedia2.add(localMedia);
                }
            }
            Log.i("callBack_result", selectMedia2.size() + "");
            if (selectMedia2 != null) {
                adapter2.setList(selectMedia2);
                pic2++;
                adapter2.notifyDataSetChanged();
            }
        }
    };

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener3 = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
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
                    config.setCompressQuality(30);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(selectMedia3);
                    config.setCompressFlag(1);// 1 系统自带压缩 2 luban压缩
                    config.setCompressW(0);
                    config.setCompressH(0);
                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
//                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback3);

                    // 只拍照
                    PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback3);
                    break;
                case 1:
                    // 删除图片
                    selectMedia3.remove(position);
                    adapter3.notifyItemRemoved(position);
                    break;
            }
        }
    };


    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback3 = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            //            selectMedia3 = resultList;//选址图片的时候
            if(resultList.size()>0)//只拍照的时候
            {
                for(LocalMedia localMedia:resultList)
                {
                    selectMedia3.add(localMedia);
                }
            }
            Log.i("callBack_result", selectMedia3.size() + "");
            if (selectMedia3 != null) {
                adapter3.setList(selectMedia3);
                pic3++;
                adapter3.notifyDataSetChanged();

            }
        }
    };

    private void post()
    {
        if(selectMedia1.size()>0&&pic1>0)
        {
            String[] str=new String[selectMedia1.size()];
            for(int i=0;i<selectMedia1.size();i++)
            {
                str[i]=PicBase64Util.encode(selectMedia1.get(i).getPath(),20);
            }
            hiddenPorjectPostBean.setBeforeWorkPhoto(str);

        }
        if(selectMedia2.size()>0&&pic2>0) {
            String[] str=new String[selectMedia2.size()];
            for(int i=0;i<selectMedia2.size();i++)
            {
                str[i]=PicBase64Util.encode(selectMedia2.get(i).getPath(),20);
            }
            hiddenPorjectPostBean.setWorkingPhoto(str);
        }
        if(selectMedia3.size()>0&&pic3>0) {
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
