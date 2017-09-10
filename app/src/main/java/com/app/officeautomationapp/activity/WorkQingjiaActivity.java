package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.bean.AddArchLeavePostBean;
import com.app.officeautomationapp.bean.AddArchSignPostBean;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.ToUserBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.OnSpinerItemClick;
import com.app.officeautomationapp.view.SpinnerDialog;
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

public class WorkQingjiaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivWorkBack;


    private ImageView iv_to_user;
    private TextView tv_to_user;
    private TextView tv_leaveType1;
    private TextView tv_leaveType2;
    private EditText et_leaveDays;
    private LinearLayout ll_startDate;
    private TextView tv_startDate;
    private LinearLayout ll_endDate;
    private TextView tv_endDate;
    private EditText et_reason;

    private Button btn_post;




    private AddArchLeavePostBean addArchLeavePostBean=new AddArchLeavePostBean();
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_qingjia);

        ivWorkBack=(ImageView)findViewById(R.id.iv_taiban_back);
        ivWorkBack.setOnClickListener(this);

        tv_leaveType1=(TextView)findViewById(R.id.tv_leaveType1);
        tv_leaveType1.setOnClickListener(this);
        tv_leaveType2=(TextView)findViewById(R.id.tv_leaveType2);
        tv_leaveType2.setOnClickListener(this);
        et_leaveDays=(EditText)findViewById(R.id.et_leaveDays);

        ll_startDate=(LinearLayout)findViewById(R.id.ll_startDate);
        ll_startDate.setOnClickListener(this);
        tv_startDate=(TextView)findViewById(R.id.tv_startDate);
        ll_endDate=(LinearLayout)findViewById(R.id.ll_endDate);
        ll_endDate.setOnClickListener(this);
        tv_endDate=(TextView)findViewById(R.id.tv_endDate);
        et_reason=(EditText)findViewById(R.id.et_reason);

        iv_to_user= (ImageView) findViewById(R.id.iv_to_user);
        iv_to_user.setOnClickListener(this);
        tv_to_user=(TextView)findViewById(R.id.tv_to_user);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        if(progressDialog!=null) {
            progressDialog.dismiss();
        }
        super.onDestroy();

    }

    private boolean initValidate()
    {
        if("".equals(tv_leaveType1.getText()))
        {
            Toast.makeText(this,"请选择请假类型!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(tv_leaveType2.getText()))
        {
            Toast.makeText(this,"请选择具体类型!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(et_leaveDays.getText()))
        {
            Toast.makeText(this,"请输入请假天数!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(tv_startDate.getText()))
        {
            Toast.makeText(this,"请选择请假开始时间!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if("".equals(tv_endDate.getText()))
        {
            Toast.makeText(this,"请选择请假结束时间!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(addArchLeavePostBean.getToUser()<1)
        {
            Toast.makeText(this,"请选择审批人!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    private void post()
    {
        addArchLeavePostBean.setLeaveDays(StringUtils.parseDouble(StringUtils.isEmpty(et_leaveDays.getText())));

        addArchLeavePostBean.setReason(et_reason.getText().toString());

        Gson gson = new Gson();
        String result = gson.toJson(addArchLeavePostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.addArchLeave);
        Log.i("", "post-url:" + Constants.addArchLeave);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
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
                    Toast.makeText(WorkQingjiaActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    WorkQingjiaActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(WorkQingjiaActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_to_user:
                getToUserId();
                break;
            case R.id.btn_post:
                if(initValidate()) {
                    post();
                }
                break;
            case R.id.ll_startDate:
                getDate(v,0);
                break;
            case R.id.ll_endDate:
                getDate(v,1);
                break;
            case R.id.tv_leaveType1:
                getType(0);
                break;
            case R.id.tv_leaveType2:
                if(addArchLeavePostBean.getLeaveType1()!=null&&!"".equals(addArchLeavePostBean.getLeaveType1()))
                {
                    getType(1);
                }
                else
                {
                    Toast.makeText(this,"请先选择请假类型！",Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    final String[] items1=new String[2];
    final String[] items_gong=new String[6];//公假
    final String[] items_si=new String[4];//私假


    private  void getType(int i)
    {
        switch (i)
        {
            case 0://大假
                items1[0]="公休假";
                items1[1]="私假";
                new AlertDialog.Builder(WorkQingjiaActivity.this)
                        .setItems(items1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tv_leaveType1.setText(items1[i]);
                                addArchLeavePostBean.setLeaveType1(items1[i]);
                                tv_leaveType2.setText("");
                                addArchLeavePostBean.setLeaveType2("");
                            }
                        }).show();
                break;
            case 1://具体假
                items_gong[0]="年休假";
                items_gong[1]="探亲假";
                items_gong[2]="婚假";
                items_gong[3]="产假";
                items_gong[4]="公事";
                items_gong[5]="出差";

                items_si[0]="病假";
                items_si[1]="事假";
                items_si[2]="私事";
                items_si[3]="其他";
                if(addArchLeavePostBean.getLeaveType1().equals(items1[0]))
                {
                    new AlertDialog.Builder(WorkQingjiaActivity.this)
                            .setItems(items_gong, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv_leaveType2.setText(items_gong[i]);
                                    addArchLeavePostBean.setLeaveType2(items_gong[i]);
                                }
                            }).show();
                }
                if(addArchLeavePostBean.getLeaveType1().equals(items1[1]))
                {
                    new AlertDialog.Builder(WorkQingjiaActivity.this)
                            .setItems(items_si, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv_leaveType2.setText(items_si[i]);
                                    addArchLeavePostBean.setLeaveType2(items_si[i]);
                                }
                            }).show();
                }

                break;

        }



    }

    private int year = 2017;
    private int month = 6;
    private int day = 8;
    private int hour=0;
    private int min=0;
    // 点击事件,日期
    public void getDate(View v, final int i) {
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        int year1 = t.year;
        int month1 = t.month;
        int date1 = t.monthDay;

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                WorkQingjiaActivity.this.year = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                new TimePickerDialog(WorkQingjiaActivity.this,new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hour=hourOfDay;
                        min=minute;
                        if(i==0)
                        {
                            tv_startDate.setText(WorkQingjiaActivity.this.year + "-" + month + "-" + day+" "+hour+":"+min+":00");
                            addArchLeavePostBean.setStartDate(WorkQingjiaActivity.this.year + "-" + month + "-" + day+" "+hour+":"+min+":00");
                        }
                        if(i==1)
                        {
                            tv_endDate.setText(WorkQingjiaActivity.this.year + "-" + month + "-" + day+" "+hour+":"+min+":00");
                            addArchLeavePostBean.setEndDate(WorkQingjiaActivity.this.year + "-" + month + "-" + day+" "+hour+":"+min+":00");
                        }
                    }
                },0,0,true).show();
            }
        }, year1, month1, date1).show();

    }




    private ArrayList<String> users=new ArrayList<>();
    private int[] usersId;
    private void getToUserId()
    {
        final SpinnerDialog spinnerDialog=new SpinnerDialog(WorkQingjiaActivity.this,users,"选择审批人",R.style.DialogAnimations_SmileWindow);

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick()
        {
            @Override
            public void onClick(String item, int position)
            {
                tv_to_user.setText(users.get(position));
                tv_to_user.setVisibility(View.VISIBLE);
                addArchLeavePostBean.setToUser(usersId[position]);
            }

            @Override
            public void onTextChange(String text) {
                progressDialog= new ProgressDialog(WorkQingjiaActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("加载中...");
                progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                progressDialog.show();
                RequestParams params = new RequestParams(Constants.getToUser);
                Log.i("MessageDetailActivity", "post-url:" + Constants.getToUser);
                UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
                params.addHeader("access_token", userDto.getAccessToken());
                params.addBodyParameter("realName",text);
                Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("JAVA", "onSuccess result:" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int re=jsonObject.getInt("result");
                            if(re!=1)
                            {
                                Toast.makeText(WorkQingjiaActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            {
                                Gson gson = new Gson();
                                List<ToUserBean> list=new ArrayList<ToUserBean>();
                                Type type=new TypeToken<List<ToUserBean>>(){}.getType();
                                list=gson.fromJson(jsonObject.get("data").toString(), type);
                                users=new ArrayList<>();
                                usersId=new int[list.size()];
                                for(int i=0;i<list.size();i++)
                                {
                                    users.add(list.get(i).getUserTrueName());
                                    usersId[i]=list.get(i).getUserId();
                                }
                                spinnerDialog.setItems(users);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("JAVA", "onError:" + ex);
                        Toast.makeText(WorkQingjiaActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
        spinnerDialog.showSpinerDialog();
    }







}
