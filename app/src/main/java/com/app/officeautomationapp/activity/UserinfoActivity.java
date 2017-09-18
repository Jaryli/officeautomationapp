package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.app.officeautomationapp.bean.AddArchLeavePostBean;
import com.app.officeautomationapp.bean.ToUserBean;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.util.StringUtils;
import com.app.officeautomationapp.view.OnSpinerItemClick;
import com.app.officeautomationapp.view.SpinnerDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

public class UserinfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_userinfo_back;

    private TextView tv_username;
    private TextView tv_realname;
    private TextView tv_phone;
    private TextView tv_department;
    private TextView tv_jobinfo;
    private TextView tv_sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView();
        initData();
    }

    private void initView()
    {
        iv_userinfo_back=(ImageView)findViewById(R.id.iv_userinfo_back);
        iv_userinfo_back.setOnClickListener(this);
        tv_username=(TextView)findViewById(R.id.tv_username);
        tv_realname=(TextView)findViewById(R.id.tv_realname);
        tv_phone=(TextView)findViewById(R.id.tv_phone);
        tv_department=(TextView)findViewById(R.id.tv_department);
        tv_jobinfo=(TextView)findViewById(R.id.tv_jobinfo);
        tv_sex=(TextView)findViewById(R.id.tv_sex);
    }

    private void initData()
    {
        UserInfoBean userInfoBean= (UserInfoBean) SharedPreferencesUtile.readObject(getApplicationContext(),"userInfo");
        tv_username.setText(userInfoBean.getUserName());
        tv_realname.setText(userInfoBean.getUserTrueName());
        tv_phone.setText(userInfoBean.getUserMobile());
        tv_department.setText(userInfoBean.getDeptName());
        tv_jobinfo.setText(userInfoBean.getUserRole());
        tv_sex.setText(userInfoBean.getUserSex());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userinfo_back:
                this.finish();
                break;
            default:
                break;
        }
    }



}
