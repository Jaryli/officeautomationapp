package com.app.officeautomationapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.MessageReadRecordBean;
import com.app.officeautomationapp.common.Constants;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/5/6.
 */

public class MessageDetailActivity extends BaseActivity implements  View.OnClickListener {

    private ImageView ivmessageBack;
    private LinearLayout progressBar;
    private TextView tvMessageDepartment;
    private TextView tvMessageTitle;
    private TextView tvMessageTime;
    private ImageView ivMessageLooked;
    private WebView webView;

    private String[] items;
    private String messageID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        ivmessageBack=(ImageView)findViewById(R.id.iv_message_back);
        ivmessageBack.setOnClickListener(this);
        progressBar=(LinearLayout)findViewById(R.id.ll_progressBar);

        tvMessageDepartment=(TextView)findViewById(R.id.tv_message_department);
        tvMessageTitle=(TextView)findViewById(R.id.tv_message_title);
        tvMessageTime=(TextView)findViewById(R.id.tv_message_time);
        ivMessageLooked=(ImageView)findViewById(R.id.iv_message_looked);
        ivMessageLooked.setOnClickListener(this);
        webView=(WebView)findViewById(R.id.webView);

        Intent intent=getIntent();
        MessageBean messageBean = (MessageBean) intent.getSerializableExtra("data");
        Log.e("MessageDetail***",messageBean.getNT_Title());
        //绑定数据 webview
        tvMessageTitle.setText(messageBean.getNT_Title());
        tvMessageDepartment.setText("发布部门:"+messageBean.getNT_DeptName());
        tvMessageTime.setText("发布时间:"+messageBean.getNT_Date());


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        webView.loadDataWithBaseURL(null,messageBean.getNT_Content(), "text/html",  "utf-8", null);
        messageID=messageBean.getNT_Id()+"";
        progressBar.setVisibility(View.GONE);

        //调用一下详情接口，表示已读
        getMessageDetail();
    }

    private void getMessageDetail()
    {

        RequestParams params = new RequestParams(Constants.getGetMessageDetail);
        Log.i("MessageDetailActivity", "post-url:" + Constants.getGetMessageDetail);
        params.addQueryStringParameter("noticeId",messageID);
        UserDto userDto= (UserDto)SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
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

    ProgressDialog progressDialog;
    private void getLookedPersons()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.getReadRecords);
        Log.i("MessageDetailActivity", "post-url:" + Constants.getReadRecords);
        params.addQueryStringParameter("noticeId",messageID);
        UserDto userDto= (UserDto)SharedPreferencesUtile.readObject(getApplicationContext(),"user");
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
                        Toast.makeText(MessageDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        List<MessageReadRecordBean> list=new ArrayList<MessageReadRecordBean>();
                        Type type=new TypeToken<List<MessageReadRecordBean>>(){}.getType();
                        list=gson.fromJson(jsonObject.get("data").toString(), type);
                        items=new String[list.size()];
                        for(int i=0;i<list.size();i++)
                        {
                            items[i]=list.get(i).getUserTrueName();
                        }
                        new AlertDialog.Builder(MessageDetailActivity.this)
                                .setTitle("已查看人员")
                                .setItems(items, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(MessageDetailActivity.this,items[i],Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MessageDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_message_back:
                this.finish();
                break;
            case R.id.iv_message_looked:
                getLookedPersons();
                break;

            default:
                break;

        }
    }
}
