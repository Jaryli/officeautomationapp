package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ThingsGetActivity;
import com.app.officeautomationapp.adapter.ThingsDetailsAdapter;
import com.app.officeautomationapp.bean.MiaomuDetailPostBean;
import com.app.officeautomationapp.bean.ThingBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
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
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialogThingsDetails {

    Activity context;
    AlertDialog alertDialog;
    int style;
    UserDto userDto;
    ProgressDialog progressDialog;
    String resName;
    ThingsDetailsAdapter adapter;
    MyGridView mygridview;

    public SpinnerDialogThingsDetails(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialogThingsDetails(Activity activity, int style, UserDto userDto,String resName) {
        this.context = activity;
        this.style=style;
        this.userDto=userDto;
        this.resName=resName;
    }



    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_things_details, null);

        mygridview = (MyGridView) v.findViewById(R.id.mygridview);
        getData();

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;
        alertDialog.setCancelable(true);
        alertDialog.show();

    }
    public void closeSpinerDialog()
    {
        alertDialog.dismiss();
    }


    private void getData()
    {
        progressDialog= new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.GetResInfoList);
        Log.i("MessageDetailActivity", "post-url:" + Constants.GetResInfoList);
        params.addHeader("access_token", userDto.getAccessToken());
        params.addBodyParameter("resNameOrSpecial",resName);
        params.addBodyParameter("resName",resName);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(context,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        ArrayList<ThingBean> list=new ArrayList<ThingBean>();
                        Type type=new TypeToken<List<ThingBean>>(){}.getType();
                        list=gson.fromJson(jsonObject.get("data").toString(), type);

                        if(list!=null&&list.size()>0)
                        {

                            if(adapter!=null)
                            {
                                adapter.clear();
                            }
                            adapter= new ThingsDetailsAdapter(context, R.layout.item_things_detail, list,SpinnerDialogThingsDetails.this);
                            mygridview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            closeSpinerDialog();
                            Toast.makeText(context,"没有可用数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(context,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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