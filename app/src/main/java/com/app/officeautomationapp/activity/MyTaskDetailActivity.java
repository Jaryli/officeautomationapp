package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.MytaskDetailAdapter;
import com.app.officeautomationapp.bean.MyTaskBean;
import com.app.officeautomationapp.bean.MyTaskDoDetailsBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;
import com.app.officeautomationapp.view.PopupMytaskDetail;
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
 * Created by yu on 2017/7/29.
 */

public class MyTaskDetailActivity extends BaseActivity implements  View.OnClickListener {

    private TextView taskUser;
    private TextView taskStartTime;
    private TextView taskEndTime;
    private TextView title;
    private TextView content;
    private MyGridView myGridView;
    private View line1;

    private TextView tv_item_add;
    private ImageView iv_mytask_back;
    private MyTaskBean myTaskBean;
    private MytaskDetailAdapter mytaskDetailAdapter;
    private UserDto userDto;

    private int isMyTask;//是否是我的任務1，還是任务督办2
    private PopupMytaskDetail popupMytaskDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask_detail);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        Intent intent=getIntent();
        myTaskBean = (MyTaskBean) intent.getSerializableExtra("data");
        isMyTask=intent.getIntExtra("isMyTask",1);
        initView();
        initData();
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
        if(resultCode>0)
        {
            initData();
        }
    }

    private void initView()
    {
        taskUser=(TextView)findViewById(R.id.taskUser);
        taskStartTime=(TextView)findViewById(R.id.taskStartTime);
        taskEndTime=(TextView)findViewById(R.id.taskEndTime);
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.content);

        tv_item_add=(TextView)findViewById(R.id.tv_item_add);
        if(isMyTask==2)
        {
            tv_item_add.setText("更多");
        }
        else
        {
            tv_item_add.setText("提交办理");
        }
        tv_item_add.setOnClickListener(this);
        iv_mytask_back=(ImageView)findViewById(R.id.iv_mytask_back);
        iv_mytask_back.setOnClickListener(this);
        myGridView=(MyGridView)findViewById(R.id.mygridview);
        line1=(View)findViewById(R.id.line1);
    }
    private void initData()
    {
        RequestParams params = new RequestParams(Constants.GetTaskDetail);
        params.addQueryStringParameter("taskId",myTaskBean.getId()+"");
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
                            Gson gson = new Gson();
                            MyTaskBean myTaskBean=new MyTaskBean();
                            Type type=new TypeToken<MyTaskBean>(){}.getType();
                            myTaskBean=gson.fromJson(jsonObject1.get("Task").toString(), type);

                            Gson gson2 = new Gson();
                            List<MyTaskDoDetailsBean> list=new ArrayList<MyTaskDoDetailsBean>();
                            Type type2=new TypeToken<List<MyTaskDoDetailsBean>>(){}.getType();
                            list=gson2.fromJson(jsonObject1.get("Details").toString(), type2);

                            if(list !=null&&list.size()>0)
                            {
                                mytaskDetailAdapter=new MytaskDetailAdapter(MyTaskDetailActivity.this,R.layout.item_task_detail, list);
                                myGridView.setAdapter(mytaskDetailAdapter);
                            }
                            else
                            {
                                line1.setVisibility(View.GONE);
                            }
                            taskUser.setText("发起人："+myTaskBean.getUserTrueName());
                            taskStartTime.setText("开始时间："+myTaskBean.getStartDate());
                            taskEndTime.setText("结束时间："+myTaskBean.getEndDate());
                            title.setText(myTaskBean.getTaskName());
                            content.setText("任务内容："+myTaskBean.getTaskContent());
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
                if(isMyTask==2)
                {
                    showTopRightPopMenu();
                }
                else
                {
                    Intent intent=new Intent(this,MyTaskDoActivity.class);
                    intent.putExtra("taskId",myTaskBean.getId());
                    startActivityForResult(intent,1);//1代表刷新
                }

                break;
            case R.id.ll_popmenu_fankui:
                Intent intent=new Intent(this,TaskFankuiActivity.class);
                intent.putExtra("taskId",myTaskBean.getId());
                intent.putExtra("taskName",myTaskBean.getTaskName());
                startActivity(intent);
                break;
            case R.id.ll_popmenu_over:
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(MyTaskDetailActivity.this);
                normalDialog.setIcon(R.mipmap.icon_finish_red);
                normalDialog.setTitle("标记完成");
                normalDialog.setMessage("你确定标记完成?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                post();
                            }
                        });
                normalDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                // 显示
                normalDialog.show();
                break;
            default:
                break;
        }
    }
    ProgressDialog progressDialog;

    private void post()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.CloseTask);
        Log.i("", "post-url:" + Constants.CloseTask);
        params.addHeader("access_token", userDto.getAccessToken());
        params.setBodyContent("'{\"taskId\":"+myTaskBean.getId()+"}'");
        Log.i("JAVA", "body:" + params.getBodyContent());
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    Toast.makeText(MyTaskDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    if(re==1)
                    {
                        progressDialog.dismiss();
                        popupMytaskDetail.dismiss();
                        MyTaskDetailActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(MyTaskDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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


    /**
     * 显示右上角popup菜单
     */
    private void showTopRightPopMenu() {
        if (popupMytaskDetail == null) {
            //(activity,onclicklistener,width,height)
            popupMytaskDetail = new PopupMytaskDetail(MyTaskDetailActivity.this, this, 360, 240);
            //监听窗口的焦点事件，点击窗口外面则取消显示
            popupMytaskDetail.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        popupMytaskDetail.dismiss();
                    }
                }
            });
        }
        //设置默认获取焦点
        popupMytaskDetail.setFocusable(true);
        //以某个控件的x和y的偏移量位置开始显示窗口
        popupMytaskDetail.showAsDropDown(tv_item_add, 0, 0);
        //如果窗口存在，则更新
        popupMytaskDetail.update();
    }
}
