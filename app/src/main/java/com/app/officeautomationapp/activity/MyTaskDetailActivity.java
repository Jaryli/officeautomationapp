package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.MyTaskBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
 * Created by yu on 2017/7/29.
 */

public class MyTaskDetailActivity extends BaseActivity implements  View.OnClickListener {

    private TextView taskUser;
    private TextView taskStartTime;
    private TextView taskEndTime;
    private TextView title;
    private TextView content;

    private TextView tv_item_add;
    private ImageView iv_mytask_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_detail);
        initView();
        initData();
    }

    private void initView()
    {
        taskUser=(TextView)findViewById(R.id.taskUser);
        taskStartTime=(TextView)findViewById(R.id.taskStartTime);
        taskEndTime=(TextView)findViewById(R.id.taskEndTime);
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);

        tv_item_add=(TextView)findViewById(R.id.tv_item_add);
        tv_item_add.setOnClickListener(this);
        iv_mytask_back=(ImageView)findViewById(R.id.iv_mytask_back);
        iv_mytask_back.setOnClickListener(this);
    }
    private void initData()
    {
        Intent intent=getIntent();
        MyTaskBean myTaskBean = (MyTaskBean) intent.getSerializableExtra("data");
        RequestParams params = new RequestParams(Constants.GetTaskDetail);
        params.addQueryStringParameter("taskId",myTaskBean.getId()+"");
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(this.getApplicationContext(),"user");
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(MyTaskDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(MyTaskDetailActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.get("data").toString());
                            JSONObject myJsonObject = new JSONObject(jsonObject1.get("Task").toString());
                            taskUser.setText("发起人："+myJsonObject.getString("UserTrueName"));
                            taskStartTime.setText("开始时间："+myJsonObject.getString("StartDate"));
                            taskEndTime.setText("结束时间："+myJsonObject.getString("EndDate"));
                            title.setText(myJsonObject.getString("TaskName"));
                            content.setText(myJsonObject.getString("TaskContent"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MyTaskDetailActivity.this,"解析数据异常",Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(MyTaskDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("JAVA", "onCancelled:" + cex);

            }
            @Override
            public void onFinished() {
                Log.e("JAVA", "onFinished:" + "");

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_mytask_back:
                this.finish();
                break;
            case R.id.tv_item_add:
                Intent intent=new Intent(this,MyTaskDoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}