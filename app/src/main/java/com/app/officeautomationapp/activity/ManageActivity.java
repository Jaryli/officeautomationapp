package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.util.SharedPreferencesUtile;

/**
 * Created by yu on 2017/5/10.
 */

public class ManageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_userinfo_back;
    private LinearLayout logout;
    private RelativeLayout rl_law;
    private RelativeLayout rl_help;
    private RelativeLayout rl_about;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
        initData();
    }

    private void initView()
    {
        iv_userinfo_back=(ImageView)findViewById(R.id.iv_userinfo_back);
        iv_userinfo_back.setOnClickListener(this);
        rl_law=(RelativeLayout)findViewById(R.id.rl_law);
        rl_law.setOnClickListener(this);
        rl_help=(RelativeLayout)findViewById(R.id.rl_help);
        rl_help.setOnClickListener(this);
        rl_about=(RelativeLayout)findViewById(R.id.rl_about);
        rl_about.setOnClickListener(this);
        logout=(LinearLayout)findViewById(R.id.logout);
        logout.setOnClickListener(this);

    }

    private void initData()
    {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userinfo_back:
                this.finish();
                break;
            case R.id.rl_law:
                Toast.makeText(this,"本产品适用中华人民共和国法律!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_help:
                Toast.makeText(this,"请联系管理员!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_about:
                Toast.makeText(this,"当前版本V1.0",Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            default:
                break;
        }
    }



}
