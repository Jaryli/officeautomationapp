package com.app.officeautomationapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.app.officeautomationapp.R;

import org.xutils.x;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.Ext.init(this.getApplication());//xutils初始化
        Log.d("BaseActivity",getClass().getSimpleName());
        setContentView(R.layout.activity_base);
        ActivityCollector.addActivity(this);
        //Android 6.0判断用户是否授予定位权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
//            //判断是否具有权限
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                //判断是否需要向用户解释为什么需要申请该权限
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    Toast.makeText(BaseActivity.this,"自Android 6.0开始需要打开位置权限",Toast.LENGTH_SHORT).show();
//                }
//                //请求权限
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                        REQUEST_CODE_ACCESS_COARSE_LOCATION);
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
