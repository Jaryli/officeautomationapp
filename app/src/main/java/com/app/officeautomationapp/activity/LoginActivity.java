package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.common.InitCommon;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by yu on 2017/1/13.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText etUserName;
    private EditText etPassword;

    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=(Button)findViewById(R.id.login);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        loginButton.setOnClickListener(this);
    }

    // 捕获返回键的方法1
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复
            ActivityCollector.finishAll();//退出应用
        }
        return super.onKeyDown(keyCode, event);
    }



    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        ActivityCollector.finishAll();//退出应用
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String userName=etUserName.getText().toString();
                String password=etPassword.getText().toString();
                doLogin(userName, password);
                break;
            default:
                break;

        }
    }
    ProgressDialog progressDialog;

    public void doLogin(String userName, String password)
    {
        loginButton.setClickable(false);
//        loader.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.login);
        Log.i("LoginActivity", "post-url:" + Constants.login);
        params.addQueryStringParameter("userName",userName);
        params.addQueryStringParameter("password",password);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                loginButton.setClickable(true);
//                loader.setVisibility(View.GONE);
                progressDialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(LoginActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        final UserDto userDto = (UserDto) gson.fromJson(jsonObject.get("user").toString(),UserDto.class);
//                        InitCommon.initUserInfo(getApplicationContext(),userDto);//初始化用户信息
                        //初始化用户信息
                        RequestParams params = new RequestParams(Constants.GetUserInfo);
                        Log.i("initUserInfo", "post-url:" + Constants.GetUserInfo);
                        params.addHeader("access_token", userDto.getAccessToken());

                        Callback.Cancelable cancelable2 = x.http().get(params, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("JAVA", "onSuccess result:" + result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int re=jsonObject.getInt("result");
                                    if(re!=1)
                                    {
                                        Log.i("initUserInfo", "error:" + jsonObject.get("msg").toString());
                                        return;
                                    }
                                    else
                                    {
                                        Gson gson = new Gson();
                                        UserInfoBean userInfoBean=new UserInfoBean();
                                        Type type=new TypeToken<UserInfoBean>(){}.getType();
                                        userInfoBean=gson.fromJson(jsonObject.get("data").toString(), type);
                                        SharedPreferencesUtile.saveObject(getApplicationContext(),"userInfo",userInfoBean);
                                        SharedPreferencesUtile.saveObject(getApplicationContext(),"user",userDto);//单例有问题
                                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //请求异常后的回调方法
                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("JAVA", "onError:" + ex);
                            }
                            //主动调用取消请求的回调方法
                            @Override
                            public void onCancelled(CancelledException cex) {
                                Log.i("JAVA", "onCancelled:" + cex);
                            }
                            @Override
                            public void onFinished() {
                                Log.i("JAVA", "onFinished:" + "");
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(LoginActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
                loginButton.setClickable(true);
//                loader.setVisibility(View.GONE);
                progressDialog.hide();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
                loginButton.setClickable(true);
//                loader.setVisibility(View.GONE);
                progressDialog.hide();
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
                loginButton.setClickable(true);
//                loader.setVisibility(View.GONE);
                progressDialog.hide();
            }
        });
        //主动调用取消请求
        //cancelable.cancel();
    }


}
