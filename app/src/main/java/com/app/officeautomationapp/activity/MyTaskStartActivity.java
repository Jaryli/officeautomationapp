package com.app.officeautomationapp.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.app.officeautomationapp.adapter.ToUserAdapter;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.bean.TaskPostBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.MyGridView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by CS-711701-00027 on 2017/9/13.
 */

public class MyTaskStartActivity  extends BaseActivity implements  View.OnClickListener {
    private ImageView iv_taskstart_back;
    private EditText et_name;
    private LinearLayout ll_startDate;
    private TextView tv_startDate;
    private LinearLayout ll_endDate;
    private TextView tv_endDate;
    private EditText et_content;
    private MyGridView mygridview;
    private ToUserAdapter toUserAdapter;
    final ArrayList<SortModel> list1=new ArrayList<>();//返回获取,需要在最后面丢上一个空的
    private final int codeNum1=3;
    TaskPostBean taskPostBean=new TaskPostBean();
    private Button btn_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_start);
        initView();
        initData();
    }

    private void initView()
    {
        iv_taskstart_back=(ImageView)findViewById(R.id.iv_taskstart_back);
        iv_taskstart_back.setOnClickListener(this);
        et_name=(EditText)findViewById(R.id.et_name);
        ll_startDate=(LinearLayout)findViewById(R.id.ll_startDate);
        ll_startDate.setOnClickListener(this);
        tv_startDate=(TextView)findViewById(R.id.tv_startDate);
        ll_endDate=(LinearLayout)findViewById(R.id.ll_endDate);
        ll_endDate.setOnClickListener(this);
        tv_endDate=(TextView)findViewById(R.id.tv_endDate);
        et_content=(EditText)findViewById(R.id.et_content);
        mygridview=(MyGridView)findViewById(R.id.mygridview);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);
    }

    private void initData()
    {
        SortModel sortMode=new SortModel();
        sortMode.setId(0);
        list1.add(sortMode);
        //实例化一个适配器
        toUserAdapter=new ToUserAdapter(this,R.layout.item_touser,R.layout.item_touser_add,list1,10,codeNum1);
        //为GridView设置适配器
        mygridview.setAdapter(toUserAdapter);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list1.get(i).getId()!=0)
                {
                    list1.remove(i);
                    toUserAdapter.refresh(list1);
                }
            }
        });
    }


    /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==codeNum1)
        {
            ArrayList<SortModel> result_value = (ArrayList<SortModel>)data.getSerializableExtra("data");
            list1.clear();
            SortModel sortMode=new SortModel();
            sortMode.setId(0);
            if(result_value!=null)
            {
                list1.addAll(result_value);
            }
            list1.add(sortMode);
            toUserAdapter.refresh(list1);
        }
    }


    private boolean initValidate()
    {
        if("".equals(et_name.getText()))
        {
            Toast.makeText(this,"请填写任务名称!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(taskPostBean.getStartDate()==null)
        {
            Toast.makeText(this,"请选择开始时间!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(taskPostBean.getEndDate()==null)
        {
            Toast.makeText(this,"请选择结束时间!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(list1.size()<2)
        {
            Toast.makeText(this,"请选择指派人!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    ProgressDialog progressDialog;
    private void postData()
    {
        String userIds="";
        if(list1.size()>1)
        {
            for(int i=0;i<list1.size()-1;i++)
            {
                userIds+=list1.get(i).getId()+",";
            }
        }
        taskPostBean.setId(0);
        taskPostBean.setUserIds(userIds.substring(0,userIds.length()-1));
        taskPostBean.setTaskName(et_name.getText().toString());
        taskPostBean.setTaskContent(et_content.getText().toString());

        Gson gson = new Gson();
        String result = gson.toJson(taskPostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.PublishTask);
        Log.i("", "post-url:" + Constants.PublishTask);
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
                    Toast.makeText(MyTaskStartActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    if(re==1) {
                        setResult(4, new Intent());
                        MyTaskStartActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(MyTaskStartActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
        switch (view.getId()) {
            case R.id.iv_taskstart_back:
                this.finish();
                break;
            case R.id.ll_startDate:
                getDate(view,0);
                break;
            case R.id.ll_endDate:
                getDate(view,1);
                break;
            case R.id.btn_post:
                if(initValidate()) {
                    postData();
                }
                break;
            default:
                break;
        }

    }

    private int year = 2017;
    private int month = 6;
    private int day = 8;

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
                MyTaskStartActivity.this.year = year;
                month = monthOfYear+1;
                day = dayOfMonth;
                if(i==0)
                {
                    tv_startDate.setText(MyTaskStartActivity.this.year + "-" + month + "-" + day);
                    taskPostBean.setStartDate(MyTaskStartActivity.this.year + "-" + month + "-" + day);
                }
                if(i==1)
                {
                    tv_endDate.setText(MyTaskStartActivity.this.year + "-" + month + "-" + day);
                    taskPostBean.setEndDate(MyTaskStartActivity.this.year + "-" + month + "-" + day);
                }
            }
        }, year1, month1, date1).show();

    }
}
