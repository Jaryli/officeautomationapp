package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.AddArchJobPostBean;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.ToUserBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/5/10.
 */

public class WorkYonggongActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivWorkBack;
    private LinearLayout layoutSelectDate;
    private TextView tvSelectDate;
    private TextView tvWorkAddressGIS;
    private LinearLayout layoutWorkTaibanAddress;

    private LinearLayout llProjectIdSelect;
    private TextView tvProjectName;
    private EditText etCbren;
    private EditText et_jobAddress;
    private EditText et_jobType;

    private EditText et_jobNum;
    private EditText et_jobDay;
    private EditText et_jobPrice;

    private EditText et_jobRegister;
    private EditText et_jobContent;
    private EditText et_obType1;
    private EditText et_jobNum1;

    private ImageView iv_to_user;
    private TextView tv_to_user;
    private Button btn_post;
    private Button btn_just_post;
    private Button btn_nextStep;

    private LinearLayout ll_nextStep;
    private LinearLayout ll_nextStep2;
    private LinearLayout ll_pve;
    private LinearLayout ll_post;


    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private Context mContext;
    private int maxSelectNum = 9;// 图片最大可选数量
    private List<LocalMedia> selectMedia = new ArrayList<>();

    private AddArchJobPostBean addArchJobPostBean=new AddArchJobPostBean();
    ProgressDialog progressDialog;

    //百度地图
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private String addres="";
    double lon;
    double lati;
    private UserDto userDto;

    private int year = 2016;
    private int month = 10;
    private int day = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_yonggong);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        ivWorkBack=(ImageView)findViewById(R.id.iv_taiban_back);
        ivWorkBack.setOnClickListener(this);
//        layoutSelectDate=(LinearLayout)findViewById(R.id.ll_select_date);
//        layoutSelectDate.setOnClickListener(this);
        tvSelectDate=(TextView)findViewById(R.id.tv_select_date);
        setDate();
        tvWorkAddressGIS=(TextView)findViewById(R.id.tv_work_yonggong_address);
//        layoutWorkTaibanAddress=(LinearLayout)findViewById(R.id.ll_work_taiban_address);
//        layoutWorkTaibanAddress.setOnClickListener(this);

        llProjectIdSelect=(LinearLayout)findViewById(R.id.ll_project_id_select);
        llProjectIdSelect.setOnClickListener(this);
        tvProjectName=(TextView)findViewById(R.id.tv_project_name);
        etCbren=(EditText)findViewById(R.id.et_cbren);
        et_jobAddress=(EditText)findViewById(R.id.et_jobAddress);
        et_jobType=(EditText)findViewById(R.id.et_jobType);
        et_jobNum=(EditText)findViewById(R.id.et_jobNum);
        et_jobDay=(EditText)findViewById(R.id.et_jobDay);
        et_jobPrice=(EditText)findViewById(R.id.et_jobPrice);
        et_jobRegister=(EditText)findViewById(R.id.et_jobRegister);
        et_obType1=(EditText)findViewById(R.id.et_obType1);
        et_jobNum1=(EditText)findViewById(R.id.et_jobNum1);
        et_jobContent=(EditText)findViewById(R.id.et_jobContent);
        iv_to_user= (ImageView) findViewById(R.id.iv_to_user);
        iv_to_user.setOnClickListener(this);
        tv_to_user=(TextView)findViewById(R.id.tv_to_user);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);
        btn_just_post=(Button)findViewById(R.id.btn_just_post);
        btn_just_post.setOnClickListener(this);
        btn_nextStep=(Button)findViewById(R.id.btn_nextStep);
        btn_nextStep.setOnClickListener(this);
        ll_pve=(LinearLayout)findViewById(R.id.ll_pve);
        ll_post=(LinearLayout)findViewById(R.id.ll_post);
        ll_nextStep=(LinearLayout)findViewById(R.id.ll_nextStep);
        ll_nextStep2=(LinearLayout)findViewById(R.id.ll_nextStep2);

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

        //百度地图
        mLocationClient = new LocationClient(getApplicationContext());
        initLocation();//初始化
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvWorkAddressGIS.setText("当前位置:"+addres);
                    break;
            }
        };
    };


    private boolean initValidate()
    {
        if(addArchJobPostBean.getProjectId()<1)
        {
            Toast.makeText(this,"请选择工程!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(selectMedia.size()<1)
        {
            Toast.makeText(this,"请添加图片!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void post()
    {
        if(selectMedia.size()>0)
        {
            List<AddArchJobPostBean.Pic> list=new ArrayList<>();
            for (int i=0;i<selectMedia.size();i++)
            {

                AddArchJobPostBean.Pic pic=new AddArchJobPostBean.Pic();
                pic.setLati(lati);
                pic.setLon(lon);
                pic.setPic(PicBase64Util.encode(selectMedia.get(i).getPath(),20));
                list.add(pic);
            }
            addArchJobPostBean.setPiclist(list);
        }

        addArchJobPostBean.setImagedata(new String[0]);


        addArchJobPostBean.setFlowGuid(Constants.FlowGuidaddArchJob);
        addArchJobPostBean.setWorkName("现场用工签工单审批");
//        addArchMachinePostBean.setMorning(Double.parseDouble(etMorning.getText().toString()));
        addArchJobPostBean.setCbren(etCbren.getText().toString());

        addArchJobPostBean.setGis(addres);
        addArchJobPostBean.setJobAddress(et_jobAddress.getText().toString());
        addArchJobPostBean.setJobType(et_jobType.getText().toString());
        addArchJobPostBean.setJobNum(et_jobNum.getText().toString());
        addArchJobPostBean.setJobContent(et_jobContent.getText().toString());
        addArchJobPostBean.setJobDay(StringUtils.parseDouble(StringUtils.isEmpty(et_jobDay.getText())));
        addArchJobPostBean.setJobPrice(StringUtils.parseDouble(StringUtils.isEmpty(et_jobPrice.getText())));
        addArchJobPostBean.setJobRegister(et_jobRegister.getText().toString());
        addArchJobPostBean.setJobDayInfo(et_jobContent.getText().toString());
        addArchJobPostBean.setJobType1(et_obType1.getText().toString());
        addArchJobPostBean.setJobNum1(et_jobNum1.getText().toString());

        Gson gson = new Gson();
        String result = gson.toJson(addArchJobPostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.addArchJob);
        Log.i("", "post-url:" + Constants.addArchJob);
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
                    Toast.makeText(WorkYonggongActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    WorkYonggongActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(WorkYonggongActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_taiban_back:
                this.finish();
                break;
//            case R.id.ll_select_date:
//                getDate(v);
//                break;
//            case R.id.ll_work_taiban_address:
//                mLocationClient.start();
//                break;
            case R.id.ll_project_id_select:
                getProjectId();
                break;
            case R.id.iv_to_user:
                getToUserId();
                break;
            case R.id.btn_post:
                if(initValidate()) {
                    post();
                }
                break;
            case R.id.btn_just_post:
                if(initValidate()) {
                    post();
                }
                break;
            case R.id.btn_nextStep:
                ll_nextStep.clearAnimation();
                ll_nextStep2.clearAnimation();
                ll_post.clearAnimation();
                ll_nextStep.setVisibility(View.VISIBLE);
                ll_nextStep2.setVisibility(View.VISIBLE);
                ll_post.setVisibility(View.VISIBLE);
                ll_pve.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }


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
                        Toast.makeText(WorkYonggongActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
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
                        new AlertDialog.Builder(WorkYonggongActivity.this)
                                .setTitle("选择我的工程")
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tvProjectName.setText(items[i]);
                                        addArchJobPostBean.setProjectId(itemsId[i]);
//                                        addArchJobPostBean.setWorkName(items[i]);
                                        addArchJobPostBean.setProjectName(items[i]);
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
                Toast.makeText(WorkYonggongActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
            }
        });
    }


    private String[] users;
    private int[] usersId;
    private void getToUserId()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.getToUser);
        Log.i("MessageDetailActivity", "post-url:" + Constants.getToUser);
        params.addHeader("access_token", userDto.getAccessToken());
        params.addBodyParameter("realName","");
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(WorkYonggongActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        List<ToUserBean> list=new ArrayList<ToUserBean>();
                        Type type=new TypeToken<List<ToUserBean>>(){}.getType();
                        list=gson.fromJson(jsonObject.get("data").toString(), type);
                        users=new String[list.size()];
                        usersId=new int[list.size()];
                        for(int i=0;i<list.size();i++)
                        {
                            users[i]=list.get(i).getUserTrueName();
                            usersId[i]=list.get(i).getUserId();
                        }
                        new AlertDialog.Builder(WorkYonggongActivity.this)
                                .setTitle("选择审批人")
                                .setItems(users, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tv_to_user.setText(users[i]);
                                        tv_to_user.setVisibility(View.VISIBLE);
                                        addArchJobPostBean.setToUser(usersId[i]);
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
                Toast.makeText(WorkYonggongActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
            }
        });
    }

    public void setDate()
    {
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year1 = t.year;
        int month1 = t.month+1;
        int date1 = t.monthDay;
        tvSelectDate.setText(year1 + "-" + month1 + "-" + date1);//定死当前时间
        addArchJobPostBean.setJobDate(year1 + "-" + month1 + "-" + date1);
    }


    // 点击事件,日期
    public void getDate(View v) {
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year1 = t.year;
        int month1 = t.month;
        int date1 = t.monthDay;

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                WorkYonggongActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                tvSelectDate.setText(year + "-" + month + "-" + day);
                addArchJobPostBean.setJobDate(year + "-" + month + "-" + day);
            }
        }, year1, month1, date1).show();

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
                        config.setThemeStyle(ContextCompat.getColor(WorkYonggongActivity.this, R.color.blue));
                        // 可以自定义底部 预览 完成 文字的颜色和背景色
                        if (!false) {
                            // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                            config.setPreviewColor(ContextCompat.getColor(WorkYonggongActivity.this, R.color.white));
                            config.setCompleteColor(ContextCompat.getColor(WorkYonggongActivity.this, R.color.white));
                            config.setPreviewBottomBgColor(ContextCompat.getColor(WorkYonggongActivity.this, R.color.blue));
                            config.setBottomBgColor(ContextCompat.getColor(WorkYonggongActivity.this, R.color.blue));
                        }
                    }
//                    if (false) {
//                        config.setCheckedBoxDrawable(selector);
//                    }

                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
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


    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

//        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息
            lon=location.getLongitude();
            lati=location.getLatitude();
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                addres=location.getAddrStr();//获取当前位置
//                tvWorkAddressGIS.setText("当前位置:"+addres);
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                addres=location.getAddrStr();//获取当前位置
//                tvWorkAddressGIS.setText("当前位置:"+addres);

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
                addres=location.getAddrStr();//获取当前位置
//                tvWorkAddressGIS.setText("当前位置:"+addres);

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }


}
