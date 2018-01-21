package com.app.officeautomationapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.adapter.ToUserAdapter;
import com.app.officeautomationapp.bean.AddArchJobPostBean;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.db.Yonggong;
import com.app.officeautomationapp.db.YonggongDB;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.ImageUtil;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.MyGridView;
import com.app.officeautomationapp.view.OnSpinerItemClick;
import com.app.officeautomationapp.view.SpinnerDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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

//    private ImageView iv_to_user;
//    private TextView tv_to_user;
    private Button btn_post;
    private Button btn_just_post;
    private Button btn_nextStep;

    private LinearLayout ll_nextStep;
    private LinearLayout ll_nextStep2;
    private LinearLayout ll_pve;
    private LinearLayout ll_post;
    private TextView tv_draft;


    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private Context mContext;
    private int maxSelectNum = 9;// 图片最大可选数量
    private List<LocalMedia> selectMedia = new ArrayList<>();

    private AddArchJobPostBean addArchJobPostBean=new AddArchJobPostBean();
    ProgressDialog progressDialog;

    //百度地图
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private String addres="";
    double lon;
    double lati;
    private UserDto userDto;

    YonggongDB yonggongDB;

    private int year = 2016;
    private int month = 10;
    private int day = 8;


    private MyGridView mygridview;
    private ToUserAdapter toUserAdapter;
    final ArrayList<SortModel> list=new ArrayList<>();//返回获取,需要在最后面丢上一个空的
    int maxNum=1;
    int resultCodeNum=126;

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
        layoutWorkTaibanAddress=(LinearLayout)findViewById(R.id.ll_work_taiban_address);
        layoutWorkTaibanAddress.setOnClickListener(this);

        tv_draft=(TextView)findViewById(R.id.tv_draft);
        tv_draft.setText("存草稿");

        yonggongDB = YonggongDB.getIntance();
        Yonggong yonggong=yonggongDB.loadYonggong();
        if(yonggong!=null)
        {
            tv_draft.setText("取草稿");
        }
        tv_draft.setOnClickListener(this);

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
//        iv_to_user= (ImageView) findViewById(R.id.iv_to_user);
//        iv_to_user.setOnClickListener(this);
//        tv_to_user=(TextView)findViewById(R.id.tv_to_user);
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
        adapter = new GridImageAdapter(WorkYonggongActivity.this, onAddPicClickListener);
        adapter.setList(selectMedia);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectMedia.size() > 0) {
                    LocalMedia media = selectMedia.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(WorkYonggongActivity.this).externalPicturePreview(position, selectMedia);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(WorkYonggongActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(WorkYonggongActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        //百度地图
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll");//gcj02：国测局坐标；//bd09ll：百度经纬度坐标；//bd09：百度墨卡托坐标；
        option.setScanSpan(0);//可选，设置发起定位请求的间隔，int类型，单位ms//如果设置为0，则代表单次定位，即仅定位一次，默认为0//如果设置非0，需设置1000ms以上才有效
        option.setOpenGps(true);//使用高精度和仅用设备两种定位模式的，参数必须设置为true//可选，设置是否使用gps，默认false
        mLocationClient.setLocOption(option);
        mLocationClient.start();

        initData();
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

            case R.id.tv_draft:
                try
                {
                    if("存草稿".equals(tv_draft.getText()))
                    {
                        if(initValidate()) {
                            String str="";
                            for (int i=0;i<selectMedia.size();i++)
                            {
                                str+=selectMedia.get(i).getPath()+";";
                            }
                            str=str.substring(0,str.length()-1);
                            Yonggong yonggong=new Yonggong(addArchJobPostBean.getProjectId(),addArchJobPostBean.getProjectName(),str);
                            yonggongDB.saveYonggong(yonggong);
                            Toast.makeText(this,"保存成功!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Yonggong yonggong=yonggongDB.loadYonggong();
                        tvProjectName.setText(yonggong.getProjectName());
                        addArchJobPostBean.setProjectId(yonggong.getProjectId());
                        addArchJobPostBean.setWorkName("现场用工签工单审批");
                        addArchJobPostBean.setProjectName(yonggong.getProjectName());
                        selectMedia.clear();
                        if(!"".equals(yonggong.getImagedata()))
                        {
                            String s[]=yonggong.getImagedata().split(";");
                            for(int i=0;i<s.length;i++)
                            {
                                LocalMedia localMedia=new LocalMedia();
                                localMedia.setPath(s[i]);
                                localMedia.setMimeType(1);
                                selectMedia.add(localMedia);
                            }
                        }
                        adapter.setList(selectMedia);
                        adapter.notifyDataSetChanged();
                        tv_draft.setText("存草稿");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(this,"操作失败!",Toast.LENGTH_SHORT).show();
                }

                break;
//            case R.id.ll_select_date:
//                getDate(v);
//                break;
            case R.id.ll_work_taiban_address:
                Intent intent=new Intent(WorkYonggongActivity.this,MapActivity.class);
                intent.putExtra("lon",lon);
                intent.putExtra("lati",lati);
                startActivity(intent);
                break;
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
                        final ArrayList<String> items=new ArrayList<>();
                        final int[] itemsId;
                        itemsId=new int[list.size()];
                        for(int i=0;i<list.size();i++)
                        {
                            items.add(list.get(i).getProjectName());
                            itemsId[i]=list.get(i).getProjectId();
                        }


                        final SpinnerDialog spinnerDialog=new SpinnerDialog(WorkYonggongActivity.this,items,"选择我的工程",R.style.DialogAnimations_SmileWindow);

                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick()
                        {
                            @Override
                            public void onClick(String item, int position)
                            {
                                tvProjectName.setText(items.get(position).length()>15?items.get(position).substring(0,12)+"...":items.get(position));
                                addArchJobPostBean.setProjectId(itemsId[position]);
//                                addArchJobPostBean.setWorkName("用功申请流程");
                                addArchJobPostBean.setProjectName(items.get(position));
                            }
                            @Override
                            public void onTextChange(String text) {

                            }
                        });
                        spinnerDialog.showSpinerDialog();

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

    private void initData()
    {
        mygridview=(MyGridView)findViewById(R.id.mygridview);
        SortModel sortMode=new SortModel();
        sortMode.setId(0);
        list.add(sortMode);
        //实例化一个适配器
        toUserAdapter=new ToUserAdapter(this,R.layout.item_touser,R.layout.item_touser_add,list,maxNum,resultCodeNum);
        //为GridView设置适配器
        mygridview.setAdapter(toUserAdapter);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list.get(i).getId()!=0)
                {
                    list.remove(i);
                    toUserAdapter.refresh(list);
                }
            }
        });
    }


    private void getToUserId()
    {
        Intent intent = new Intent();
        intent.putExtra("hasCheckBox", true);
        intent.putExtra("hasDone", true);
        intent.putExtra("code", resultCodeNum);
        intent.putExtra("maxNum", maxNum);
        intent.setClass(WorkYonggongActivity.this, ItemContactsActivity.class);
        startActivityForResult(intent,resultCodeNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==resultCodeNum)
        {
            ArrayList<SortModel> result_value = (ArrayList<SortModel>)data.getSerializableExtra("data");
            list.clear();
            SortModel sortMode=new SortModel();
            sortMode.setId(0);
            if(result_value!=null)
            {
                list.addAll(result_value);
            }
            list.add(sortMode);
            toUserAdapter.refresh(list);
            if(result_value.size()>0) {
                addArchJobPostBean.setToUser(result_value.get(0).getId());
            }
            else
            {
                addArchJobPostBean.setToUser(0);
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectMediabBack = PictureSelector.obtainMultipleResult(data);
                    if(selectMediabBack!=null&&selectMediabBack.size()>0)
                    {
                        ImageUtil.addTimePic(selectMediabBack,WorkYonggongActivity.this);
                        selectMedia.add(selectMediabBack.get(0));
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectMedia) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectMedia);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
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
        public void onAddPicClick() {
            boolean mode = false;//只能拍照
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(WorkYonggongActivity.this)
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
                        .selectionMedia(selectMedia)// 是否传入已选图片
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
                PictureSelector.create(WorkYonggongActivity.this)
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




    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Log.e("***", addr);
            Log.e("***", latitude+"");
            Log.e("***", longitude+"");
            Log.e("***radius", radius+"");
            Log.e("***errorCode", errorCode+"");
            addres=addr;
            lon=longitude;
            lati=latitude;

            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }


}
