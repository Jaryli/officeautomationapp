package com.app.officeautomationapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.AcceptanceAdapter;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.AddArchTreeFlowPostBean;
import com.app.officeautomationapp.bean.MiaomuPostBean;
import com.app.officeautomationapp.bean.MiaomuTopPostBean;
import com.app.officeautomationapp.bean.ProjectMiaomuBean;
import com.app.officeautomationapp.bean.ProjectTujianBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.bean.TujianPostBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.SpinnerDialogAcceptance;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class AcceptanceActivity extends BaseActivity implements View.OnClickListener {

    private String type;//miaomu tujian
    private List<View> viewList = new ArrayList<>();//ViewPager数据源
    private AcceptanceAdapter myPagerAdapter;//适配器
    private ViewPager viewPager;
    private ProjectMiaomuBean projectMiaomuBean;
    private ProjectTujianBean projectTujianBean;
    private int count;//增加次数
    private int totalCount = 0;
    private View positionView;
    private View del;
    private View post_all;
    private TextView tv_title;
    private Integer projectId;
    private UserDto userDto;


    EditText supplyName;
    EditText wz_name;//物资名称
    Integer wz_id;//物资id
    EditText arriveDate;
    private Integer isPay;
    private String orderCode;

    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private Context mContext;
    private int maxSelectNum = 9;// 图片最大可选数量
    private List<LocalMedia> selectMedia = new ArrayList<>();

    private String addres="";
    double lon;
    double lati;


    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    HashMap<Integer, Object> mapPage = new HashMap<>();

    int codeNum=2;
    int postUserId=0;
    SpinnerDialogAcceptance spinnerDialog;


    @Override
    public void onBackPressed() {
        Log.d("", "onBackPressed()");
        super.onBackPressed();
        getConfirmDialog(AcceptanceActivity.this, "您还未提交验收，确定返回吗?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AcceptanceActivity.this.finish();
            }
        }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            Log.d("", "onKeyDown()");
            getConfirmDialog(AcceptanceActivity.this, "您还未提交验收，确定返回吗?", new DialogInterface.OnClickListener
                    () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AcceptanceActivity.this.finish();
                }
            }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
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
            Log.d("***", addr);
            Log.d("***", latitude+"");
            Log.d("***", longitude+"");
            addres=addr;
            lon=latitude;
            lati=longitude;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance);
        //baidumap
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();


        mContext = this;
        tv_title = (TextView) findViewById(R.id.tv_title);
        userDto = (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(), "user");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(type.equals("miaomu")) {
            projectMiaomuBean = (ProjectMiaomuBean) intent.getSerializableExtra("data");
            projectId = projectMiaomuBean.getProjectId();
            tv_title.setText("苗木验收");
        }
        else
        {
            projectTujianBean = (ProjectTujianBean) intent.getSerializableExtra("data");
            projectId = projectTujianBean.getProjectId();
            tv_title.setText("土建验收");
        }
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
        myPagerAdapter = new AcceptanceAdapter(viewList);//创建适配器实例
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(myPagerAdapter);//为ViewPager设置适配器
        viewPager.setOffscreenPageLimit(3);

        del = findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getConfirmDialog(AcceptanceActivity.this, "是否删除当前验收单?", new DialogInterface.OnClickListener
                        () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delPage();
                    }
                }).show();
            }
        });
        post_all = findViewById(R.id.post_all);
        post_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalCount<3)
                {
                    Toast.makeText(AcceptanceActivity.this,"请先填写验收信息！",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    spinnerDialog=new SpinnerDialogAcceptance(AcceptanceActivity.this,R.style.DialogAnimations_SmileWindow,1,codeNum);
                    spinnerDialog.bindOnSpinerListener(new SpinnerDialogAcceptance.OnDoneClick() {
                        @Override
                        public void onClick() {
                            if(postUserId==0)
                            {
                                Toast.makeText(AcceptanceActivity.this,"请选择经办人",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            {

                                AddArchTreeFlowPostBean addArchTreeFlowPostBean=new AddArchTreeFlowPostBean();
                                addArchTreeFlowPostBean.setOrderCode(orderCode);
                                addArchTreeFlowPostBean.setToUser(postUserId);
                                Gson gson = new Gson();
                                String result = gson.toJson(addArchTreeFlowPostBean);
                                progressDialog = new ProgressDialog(AcceptanceActivity.this);
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressDialog.setMessage("提交中...");
                                progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                                progressDialog.show();
                                String url="";
                                if(type.equals("miaomu"))
                                {
                                    url=Constants.AddArchTreeFlow;
                                }
                                else
                                {
                                    url=Constants.AddArchCivilFlow;
                                }
                                RequestParams params = new RequestParams(url);
                                Log.i("", "post-url:" + url);
                                params.addHeader("access_token", userDto.getAccessToken());
                                params.setBodyContent("'" + result + "'");
                                Log.i("JAVA", "body:" + params.getBodyContent());
                                Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        Log.i("JAVA", "onSuccess result:" + result);
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            int re = jsonObject.getInt("result");
                                            if (re != 1) {
                                                Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                                return;
                                            } else {
                                                Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                AcceptanceActivity.this.finish();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    //请求异常后的回调方法
                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                        Log.i("JAVA", "onError:" + ex);
                                        Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                        }
                    });
                    spinnerDialog.showSpinerDialog();
                }
            }
        });
        dealStatusBar(); // 调整状态栏高度
        addPage();
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

    ProgressDialog progressDialog;

    private void getSupply() {
        Intent intent = new Intent(this, SupplyGetActivity.class);
        startActivityForResult(intent, 0);
    }

    private void getWz() {
        Intent intent = new Intent(this, WzGetActivity.class);
        if(type.equals("miaomu")) {
            intent.putExtra("data", projectMiaomuBean.getApplyCode());
        }
        else
        {
            intent.putExtra("data", projectTujianBean.getApplyCode());
        }
        intent.putExtra("type", type);
        intent.putExtra("mapPage", mapPage);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String data_supply_name = bundle.getString("data_supply_name");
                Log.d("text", data_supply_name);
                this.supplyName.setText(data_supply_name);
            }
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String data_tree_name = bundle.getString("data_tree_name");
                String data_tree_id = bundle.getString("data_tree_id");
                Log.d("text", data_tree_name + " " + data_tree_id);
                this.wz_name.setText(data_tree_name);
                this.wz_id = Integer.parseInt(data_tree_id);
                mapPage.put(count,wz_id);
            }
        }

        if(resultCode==codeNum)
        {
            ArrayList<SortModel> result_value = (ArrayList<SortModel>)data.getSerializableExtra("data");
            if(result_value.size()>0) {
                spinnerDialog.refreshData(result_value.get(0).getName());
                postUserId = result_value.get(0).getId();
            }
        }
    }


    /**
     * 该方法封装了添加页面的代码逻辑实现，参数text为要展示的数据
     */
    public void addPage() {

        count++;
        totalCount++;

        LayoutInflater inflater = LayoutInflater.from(this);//获取LayoutInflater的实例
        View view = null;//调用LayoutInflater实例的inflate()方法来加载页面的布局

        if(type.equals("miaomu")) {
            if (totalCount == 1) {
                final MiaomuTopPostBean miaomuTopPostBean = new MiaomuTopPostBean();
                view = inflater.inflate(R.layout.activity_acceptance_view, null);
                final EditText fee = (EditText) view.findViewById(R.id.fee);
                supplyName = (EditText) view.findViewById(R.id.supplyName);
                supplyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSupply();
                    }
                });
                final EditText remark = (EditText) view.findViewById(R.id.remark);

                TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
                textView.setText("苗木验收表");//展示数据
                TextView projectName = (TextView) view.findViewById(R.id.projectName);
                projectName.setText(projectMiaomuBean.getProjectName());
                TextView buyCode = (TextView) view.findViewById(R.id.buyCode);
                buyCode.setText(projectMiaomuBean.getApplyCode());
                final Button btn_post = (Button) view.findViewById(R.id.btn_post);
                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            miaomuTopPostBean.setBuyCode(projectMiaomuBean.getApplyCode());
                            miaomuTopPostBean.setFee(StringUtils.parseDouble(StringUtils.isEmpty(fee.getText())));
                            miaomuTopPostBean.setProjectId(projectMiaomuBean.getProjectId());
                            miaomuTopPostBean.setProjectName(projectMiaomuBean.getProjectName());
                            miaomuTopPostBean.setRemark(remark.getText().toString());
                            miaomuTopPostBean.setSupplyName(supplyName.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AcceptanceActivity.this, "参数填写有误！", Toast.LENGTH_SHORT).show();
                        }
                        Gson gson = new Gson();
                        String result = gson.toJson(miaomuTopPostBean);
                        progressDialog = new ProgressDialog(AcceptanceActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("提交中...");
                        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                        progressDialog.show();
                        RequestParams params = new RequestParams(Constants.AddTreeApply);
                        Log.i("", "post-url:" + Constants.AddTreeApply);
                        params.addHeader("access_token", userDto.getAccessToken());
                        params.setBodyContent("'" + result + "'");
                        Log.i("JAVA", "body:" + params.getBodyContent());
                        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("JAVA", "onSuccess result:" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int re = jsonObject.getInt("result");
                                    orderCode = jsonObject.getString("orderCode");//获取Code
                                    if (re != 1) {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();

                                        addPage();
                                        Message message = new Message();
                                        message.what = 1;
                                        handler.sendMessage(message);
                                        btn_post.setVisibility(View.INVISIBLE);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("JAVA", "onError:" + ex);
                                Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                });
            } else {
                final MiaomuPostBean miaomuPostBean = new MiaomuPostBean();
                view = inflater.inflate(R.layout.activity_acceptance_view2, null);
                TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
                textView.setText("苗木验收明细单");//展示数据
                wz_name = (EditText) view.findViewById(R.id.wz_name);
                wz_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWz();
                    }
                });
                final EditText numInfo = (EditText) view.findViewById(R.id.numInfo);
                final EditText xiuJianReq = (EditText) view.findViewById(R.id.xiuJianReq);
                final EditText raoGanReq = (EditText) view.findViewById(R.id.raoGanReq);
                arriveDate = (EditText) view.findViewById(R.id.arriveDate);
                arriveDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDate();
                    }
                });
                final EditText ysXiongJing = (EditText) view.findViewById(R.id.ysXiongJing);
                final EditText ysHeight = (EditText) view.findViewById(R.id.ysHeight);
                final EditText ysPengXing = (EditText) view.findViewById(R.id.ysPengXing);
                final SwitchButton isPay = (SwitchButton) view.findViewById(R.id.isPay);
                TextView tv_work_address=(TextView)view.findViewById(R.id.tv_work_address);
                tv_work_address.setText(addres);

                recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
                FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                adapter = new GridImageAdapter(this, onAddPicClickListener, false);
                adapter.setSelectMax(maxSelectNum);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        // 这里可预览图片
                        PictureConfig.getPictureConfig().externalPicturePreview(AcceptanceActivity.this, position, selectMedia);
                    }
                });


                supplyName = (EditText) view.findViewById(R.id.supplyName);
                supplyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSupply();
                    }
                });
                final Button btn_post = (Button) view.findViewById(R.id.btn_post);
                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            if(selectMedia.size()>0)
                            {
                                List<MiaomuPostBean.Pic> list=new ArrayList<>();
                                for (int i=0;i<selectMedia.size();i++)
                                {

                                    MiaomuPostBean.Pic pic=new MiaomuPostBean.Pic();
                                    pic.setLati(lati);
                                    pic.setLon(lon);
                                    pic.setPic(PicBase64Util.encode(selectMedia.get(i).getPath(),20));
                                    list.add(pic);
                                }
                                miaomuPostBean.setPiclist(list);
                            }
                            miaomuPostBean.setImagedata(new String[0]);
                            miaomuPostBean.setSupplyName(supplyName.getText().toString());
                            miaomuPostBean.setApplyCode(orderCode);
                            miaomuPostBean.setArriveDate(arriveDate.getText().toString());
                            miaomuPostBean.setId(wz_id);//
                            miaomuPostBean.setIsPay(isPay.isChecked() ? 1 : 0);//
                            miaomuPostBean.setNumInfo(numInfo.getText().toString().equals("") ? 0 : Integer.parseInt(numInfo.getText().toString()));
                            miaomuPostBean.setRaoGanReq(raoGanReq.getText().toString());
                            miaomuPostBean.setXiuJianReq(xiuJianReq.getText().toString());
                            miaomuPostBean.setYsHeight(ysHeight.getText().toString());
                            miaomuPostBean.setYsPengXing(ysPengXing.getText().toString());
                            miaomuPostBean.setYsXiongJing(ysXiongJing.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AcceptanceActivity.this, "参数填写有误！", Toast.LENGTH_SHORT).show();
                        }
                        Gson gson = new Gson();
                        String result = gson.toJson(miaomuPostBean);
                        progressDialog = new ProgressDialog(AcceptanceActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("提交中...");
                        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                        progressDialog.show();
                        RequestParams params = new RequestParams(Constants.AddTreeDetails);
                        Log.i("", "post-url:" + Constants.AddTreeDetails);
                        params.addHeader("access_token", userDto.getAccessToken());
                        params.setBodyContent("'" + result + "'");
                        Log.i("JAVA", "body:" + params.getBodyContent());
                        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("JAVA", "onSuccess result:" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int re = jsonObject.getInt("result");
                                    if (re != 1) {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        clearData();
                                        addPage();
                                        Message message = new Message();
                                        message.what = 1;
                                        handler.sendMessage(message);
                                        btn_post.setVisibility(View.INVISIBLE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("JAVA", "onError:" + ex);
                                Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                });
            }
        }
        else//tujian
        {
            if (totalCount == 1) {
                final MiaomuTopPostBean miaomuTopPostBean = new MiaomuTopPostBean();
                view = inflater.inflate(R.layout.activity_acceptance_view, null);
                final EditText fee = (EditText) view.findViewById(R.id.fee);
                supplyName = (EditText) view.findViewById(R.id.supplyName);
                supplyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSupply();
                    }
                });
                final EditText remark = (EditText) view.findViewById(R.id.remark);
                LinearLayout ll_remark = (LinearLayout) view.findViewById(R.id.ll_remark);
                ll_remark.setVisibility(View.GONE);
                TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
                textView.setText(type.equals("tujian") ? "土建验收表" : "苗木验收表");//展示数据
                TextView projectName = (TextView) view.findViewById(R.id.projectName);
                projectName.setText(projectTujianBean.getProjectName());
                TextView buyCode = (TextView) view.findViewById(R.id.buyCode);
                buyCode.setText(projectTujianBean.getApplyCode());
                final Button btn_post = (Button) view.findViewById(R.id.btn_post);
                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            miaomuTopPostBean.setBuyCode(projectTujianBean.getApplyCode());
                            miaomuTopPostBean.setFee(StringUtils.parseDouble(StringUtils.isEmpty(fee.getText())));
                            miaomuTopPostBean.setProjectId(projectTujianBean.getProjectId());
                            miaomuTopPostBean.setProjectName(projectTujianBean.getProjectName());
                            miaomuTopPostBean.setRemark(remark.getText().toString());
                            miaomuTopPostBean.setSupplyName(supplyName.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AcceptanceActivity.this, "参数填写有误！", Toast.LENGTH_SHORT).show();
                        }
                        Gson gson = new Gson();
                        String result = gson.toJson(miaomuTopPostBean);
                        progressDialog = new ProgressDialog(AcceptanceActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("提交中...");
                        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                        progressDialog.show();
                        RequestParams params = new RequestParams(Constants.AddCivilApply);
                        Log.i("", "post-url:" + Constants.AddCivilApply);
                        params.addHeader("access_token", userDto.getAccessToken());
                        params.setBodyContent("'" + result + "'");
                        Log.i("JAVA", "body:" + params.getBodyContent());
                        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("JAVA", "onSuccess result:" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int re = jsonObject.getInt("result");
                                    orderCode = jsonObject.getString("orderCode");//获取Code
                                    if (re != 1) {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();

                                        addPage();
                                        Message message = new Message();
                                        message.what = 1;
                                        handler.sendMessage(message);
                                        btn_post.setVisibility(View.INVISIBLE);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("JAVA", "onError:" + ex);
                                Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                });
            } else {
                final TujianPostBean tujianPostBean = new TujianPostBean();
                view = inflater.inflate(R.layout.activity_acceptance_view3, null);
                TextView textView = (TextView) view.findViewById(R.id.text_view);//获取该View对象的TextView实例
                textView.setText("土建验收明细单");//展示数据
                wz_name = (EditText) view.findViewById(R.id.wz_name);
                wz_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getWz();
                    }
                });
                final EditText numInfo = (EditText) view.findViewById(R.id.numInfo);
                final EditText specialRequest = (EditText) view.findViewById(R.id.specialRequest);
                final EditText remark = (EditText) view.findViewById(R.id.remark);
                supplyName = (EditText) view.findViewById(R.id.supplyName);
                supplyName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getSupply();
                    }
                });
                TextView tv_work_address=(TextView)view.findViewById(R.id.tv_work_address);
                tv_work_address.setText(addres);

                recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
                FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                adapter = new GridImageAdapter(this, onAddPicClickListener, false);
                adapter.setSelectMax(maxSelectNum);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        // 这里可预览图片
                        PictureConfig.getPictureConfig().externalPicturePreview(AcceptanceActivity.this, position, selectMedia);
                    }
                });
                final Button btn_post = (Button) view.findViewById(R.id.btn_post);
                btn_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            if(selectMedia.size()>0)
                            {
                                List<TujianPostBean.Pic> list=new ArrayList<>();
                                for (int i=0;i<selectMedia.size();i++)
                                {

                                    TujianPostBean.Pic pic=new TujianPostBean.Pic();
                                    pic.setLati(lati);
                                    pic.setLon(lon);
                                    pic.setPic(PicBase64Util.encode(selectMedia.get(i).getPath(),20));
                                    list.add(pic);
                                }
                                tujianPostBean.setPiclist(list);
                            }

                            tujianPostBean.setImagedata(new String[0]);

                            tujianPostBean.setSupplyName(supplyName.getText().toString());

                            tujianPostBean.setId(wz_id);//
                            tujianPostBean.setNumInfo(numInfo.getText().toString().equals("") ? 0 : Integer.parseInt(numInfo.getText().toString()));
                            tujianPostBean.setApplyCode(orderCode);
                            tujianPostBean.setRemark(remark.getText().toString());
                            tujianPostBean.setSpecialRequest(specialRequest.getText().toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AcceptanceActivity.this, "参数填写有误！", Toast.LENGTH_SHORT).show();
                        }
                        Gson gson = new Gson();
                        String result = gson.toJson(tujianPostBean);
                        progressDialog = new ProgressDialog(AcceptanceActivity.this);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("提交中...");
                        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                        progressDialog.show();
                        RequestParams params = new RequestParams(Constants.AddCivilDetails);
                        Log.i("", "post-url:" + Constants.AddCivilDetails);
                        params.addHeader("access_token", userDto.getAccessToken());
                        params.setBodyContent("'" + result + "'");
                        Log.i("JAVA", "body:" + params.getBodyContent());
                        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("JAVA", "onSuccess result:" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int re = jsonObject.getInt("result");
                                    if (re != 1) {
                                        Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                        return;
                                    } else {
                                        clearData();
                                        addPage();
                                        Message message = new Message();
                                        message.what = 1;
                                        handler.sendMessage(message);
                                        btn_post.setVisibility(View.INVISIBLE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("JAVA", "onError:" + ex);
                                Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                });
            }
        }


        viewList.add(view);//为数据源添加一项数据
        myPagerAdapter.notifyDataSetChanged();//通知UI更新
    }

    private void clearData() {
        selectMedia = null;
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
                        config.setThemeStyle(ContextCompat.getColor(AcceptanceActivity.this, R.color.blue));
                        // 可以自定义底部 预览 完成 文字的颜色和背景色
                        if (!false) {
                            // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                            config.setPreviewColor(ContextCompat.getColor(AcceptanceActivity.this, R.color.white));
                            config.setCompleteColor(ContextCompat.getColor(AcceptanceActivity.this, R.color.white));
                            config.setPreviewBottomBgColor(ContextCompat.getColor(AcceptanceActivity.this, R.color.blue));
                            config.setBottomBgColor(ContextCompat.getColor(AcceptanceActivity.this, R.color.blue));
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

    private int year;
    private int month;
    private int day;

    public void getDate() {
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year1 = t.year;
        int month1 = t.month;
        int date1 = t.monthDay;

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                AcceptanceActivity.this.year = year;
                month = monthOfYear + 1;
                day = dayOfMonth;
                arriveDate.setText(AcceptanceActivity.this.year + "-" + month + "-" + day);

            }
        }, year1, month1, date1).show();

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //更新UI
            switch (msg.what) {
                case 1:
                    viewPager.setCurrentItem(totalCount - 1);
                    break;
            }
        }
    };

    /**
     * 删除当前页面
     */
    public void delPage() {
        final int position = viewPager.getCurrentItem();//获取当前页面位置
        if (position == 0) {
            Toast.makeText(this, "不能删除验收头哦！", Toast.LENGTH_SHORT).show();
        } else if (position + 1 == totalCount) {
            Toast.makeText(this, "不能删除沒有提交的数据哦！", Toast.LENGTH_SHORT).show();
        } else {
            String id="";
            int i=0;
            for (Integer in : mapPage.keySet()) {
                if(i==position)
                {
                    id=mapPage.get(in).toString();
                }
                i++;
            }
            progressDialog = new ProgressDialog(AcceptanceActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("提交中...");
            progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
            progressDialog.show();
            String url="";
            if(type.equals("miaomu"))
            {
                url=Constants.DeleteTreeDetails;
            }
            else
            {
                url=Constants.DeleteCivilDetails;
            }
            RequestParams params = new RequestParams(url);
            Log.i("", "post-url:" + url);
            params.addHeader("access_token", userDto.getAccessToken());
            params.addQueryStringParameter("id", id);
            Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("JAVA", "onSuccess result:" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int re = jsonObject.getInt("result");
                        if (re != 1) {
                            Toast.makeText(AcceptanceActivity.this, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            int i=0;
                            for (Integer in : mapPage.keySet()) {
                                if(i==position)
                                {
                                    mapPage.remove(in);
                                }
                                i++;
                            }
                            totalCount--;
                            viewList.remove(position);//删除一项数据源中的数据
                            myPagerAdapter.notifyDataSetChanged();//通知UI更新
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.i("JAVA", "onError:" + ex);
                    Toast.makeText(AcceptanceActivity.this, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onClick(View view) {

    }
}
