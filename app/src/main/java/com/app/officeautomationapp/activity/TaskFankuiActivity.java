package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ToUserAdapter;
import com.app.officeautomationapp.bean.FeedBackTaskPostBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;
import com.google.gson.Gson;

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

public class TaskFankuiActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivFankuiBack;


//    private ImageView iv_to_user;
//    private TextView tv_to_user;
    private TextView tv_task_name;
    private EditText et_task_content;

    private Button btn_post;
    private UserDto userDto;

    private FeedBackTaskPostBean feedBackTaskPostBean=new FeedBackTaskPostBean();

    ProgressDialog progressDialog;
    int taskId;
    String taskName;

    private MyGridView mygridview;
    private ToUserAdapter toUserAdapter;
    final ArrayList<SortModel> list=new ArrayList<>();//返回获取,需要在最后面丢上一个空的
    int maxNum=9;
    int resultCodeNum=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_fankui);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        ivFankuiBack=(ImageView)findViewById(R.id.iv_fankui_back);
        ivFankuiBack.setOnClickListener(this);
        taskId=getIntent().getIntExtra("taskId",0);
        taskName=getIntent().getStringExtra("taskName");
        tv_task_name=(TextView)findViewById(R.id.tv_task_name);
        tv_task_name.setText(taskName);
        et_task_content=(EditText)findViewById(R.id.et_task_content);


//        iv_to_user= (ImageView) findViewById(R.id.iv_to_user);
//        iv_to_user.setOnClickListener(this);
//        tv_to_user=(TextView)findViewById(R.id.tv_to_user);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);
        initData();
    }


    private void initData()
    {
        mygridview=(MyGridView)findViewById(R.id.mygridview);
        SortModel sortMode=new SortModel();
        sortMode.setId(0);
        list.add(sortMode);
        //实例化一个适配器
        toUserAdapter=new ToUserAdapter(this,R.layout.item_touser,R.layout.item_touser_add,list,maxNum,resultCodeNum);
        //为GridView设置适配器
        mygridview.setAdapter(toUserAdapter);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(list.get(i).getId()!=0)
                {
                    list.remove(i);
                    toUserAdapter.refresh(list);
                }
            }
        });
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
        if(feedBackTaskPostBean.getToUserIds()==null)
        {
            Toast.makeText(this,"请选择任务指派人!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void post()
    {
        feedBackTaskPostBean.setFeedback(et_task_content.getText().toString());
        feedBackTaskPostBean.setTaskId(taskId);
        Gson gson = new Gson();
        String result = gson.toJson(feedBackTaskPostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.FeedBackTask);
        Log.i("", "post-url:" + Constants.FeedBackTask);
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
                    Toast.makeText(TaskFankuiActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    TaskFankuiActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(TaskFankuiActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_fankui_back:
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
            default:
                break;
        }
    }


    private void getToUserId()
    {
        Intent intent = new Intent();
        intent.putExtra("hasCheckBox", true);
        intent.putExtra("hasDone", true);
        intent.putExtra("code", resultCodeNum);
        intent.putExtra("maxNum", maxNum);
        intent.setClass(TaskFankuiActivity.this, ItemContactsActivity.class);
        startActivityForResult(intent,resultCodeNum);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==resultCodeNum)
        {
            ArrayList<SortModel> result_value = (ArrayList<SortModel>)data.getSerializableExtra("data");
            list.clear();
            SortModel sortMode=new SortModel();
            sortMode.setId(0);
            if(result_value!=null)
            {
                list.addAll(result_value);
            }
            list.add(sortMode);
            toUserAdapter.refresh(list);
            if(result_value.size()>0) {
                String user="";
                for(int i=0;i<result_value.size();i++)
                {
                    user+=result_value.get(i).getId()+",";
                }
                feedBackTaskPostBean.setToUserIds(user.substring(0,user.length()-1));
            }
            else
            {
                feedBackTaskPostBean.setToUserIds(null);
            }
        }
    }

}
