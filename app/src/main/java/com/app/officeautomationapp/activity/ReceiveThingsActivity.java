package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.GridImageAdapter;
import com.app.officeautomationapp.adapter.ToUserAdapter;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.ReceiveThingsPostBean;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.bean.ToUserBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.FullyGridLayoutManager;
import com.app.officeautomationapp.util.PicBase64Util;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;
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
 * Created by yu on 2017/4/17.
 */
public class ReceiveThingsActivity extends BaseActivity implements View.OnClickListener  {

    private ImageView ivReceiveThingsBack;
    private LinearLayout ll_project_select;
    private TextView tv_history;
    private TextView tv_project_name;
    private LinearLayout ll_thing_select;
    private TextView tv_thing_name;
//    private ImageView iv_to_user;
//    private TextView tv_to_user;
    private EditText et_num;
    private EditText et_remark;
    private Button btn_post;
    private UserDto userDto;

    private ReceiveThingsPostBean receiveThingsPostBean=new ReceiveThingsPostBean();
    ProgressDialog progressDialog;

    private MyGridView mygridview;
    private ToUserAdapter toUserAdapter;
    final ArrayList<SortModel> list=new ArrayList<>();//返回获取,需要在最后面丢上一个空的
    int maxNum=1;
    int resultCodeNum=128;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_things);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        ivReceiveThingsBack=(ImageView)findViewById(R.id.iv_receive_things_back);
        ivReceiveThingsBack.setOnClickListener(this);
        ll_project_select=(LinearLayout)findViewById(R.id.ll_project_select);
        ll_project_select.setOnClickListener(this);
        ll_thing_select=(LinearLayout)findViewById(R.id.ll_thing_select);
        ll_thing_select.setOnClickListener(this);
        tv_project_name=(TextView)findViewById(R.id.tv_project_name);
        tv_thing_name=(TextView)findViewById(R.id.tv_thing_name);
        et_num=(EditText)findViewById(R.id.et_num);
        et_remark=(EditText)findViewById(R.id.et_remark);
        btn_post=(Button)findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);

//        iv_to_user= (ImageView) findViewById(R.id.iv_to_user);
//        iv_to_user.setOnClickListener(this);
//        tv_to_user=(TextView)findViewById(R.id.tv_to_user);
        tv_history=(TextView)findViewById(R.id.tv_history);
        tv_history.setOnClickListener(this);

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_receive_things_back:
                this.finish();
                break;
            case R.id.iv_to_user:
                getToUserId();
                break;
            case R.id.ll_project_select:
                getProjectId();
                break;
            case R.id.ll_thing_select:
                getThingId();
                break;
            case R.id.btn_post:
                post();
                break;
            case R.id.tv_history:
                Intent intent=new Intent(ReceiveThingsActivity.this,MyApprovalReceiveActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void getThingId()
    {
        Intent intent = new Intent(this,ThingsGetActivity2.class);
        startActivityForResult(intent,0);
    }


    private void post()
    {

        receiveThingsPostBean.setRemark(et_remark.getText().toString());
        receiveThingsPostBean.setNum(Integer.parseInt(et_num.getText().toString()));
        Gson gson = new Gson();
        String result = gson.toJson(receiveThingsPostBean);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.addReceiveThing);
        Log.i("", "post-url:" + Constants.addReceiveThing);
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
                    Toast.makeText(ReceiveThingsActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    ReceiveThingsActivity.this.finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(ReceiveThingsActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String data_thing_name =null;
            int data_thing_id=0;
            if(bundle!=null) {
                data_thing_name = bundle.getString("data_thing_name");
                data_thing_id = bundle.getInt("data_thing_id");
                Log.d("text",data_thing_name);
                tv_thing_name.setText(data_thing_name);
                receiveThingsPostBean.setResId(data_thing_id);
            }

        }
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
                receiveThingsPostBean.setToUser(result_value.get(0).getId());
            }
            else
            {
                receiveThingsPostBean.setToUser(0);
            }
        }

    }

    private void getProjectId()
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.getMyProject);
        Log.i("MessageDetailActivity", "post-url:" + Constants.getMyProject);
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
                        Toast.makeText(ReceiveThingsActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        List<MyProjectBean> list=new ArrayList<MyProjectBean>();
                        Type type=new TypeToken<List<MyProjectBean>>(){}.getType();
                        list=gson.fromJson(jsonObject.get("dataList").toString(), type);
                        final ArrayList<String> items=new ArrayList<>();
                        final int[] itemsId;
                        itemsId=new int[list.size()];
                        for(int i=0;i<list.size();i++)
                        {
                            items.add(list.get(i).getProjectName());
                            itemsId[i]=list.get(i).getProjectId();
                        }


                        final SpinnerDialog spinnerDialog=new SpinnerDialog(ReceiveThingsActivity.this,items,"选择我的工程",R.style.DialogAnimations_SmileWindow);

                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick()
                        {
                            @Override
                            public void onClick(String item, int position)
                            {
                                tv_project_name.setText(items.get(position).length()>10?items.get(position).substring(0,8)+"...":items.get(position));
                                receiveThingsPostBean.setProjectId(itemsId[position]);
                            }

                            @Override
                            public void onTextChange(String text) {

                            }
                        });
                        spinnerDialog.showSpinerDialog();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(ReceiveThingsActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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


    private void getToUserId()
    {
        Intent intent = new Intent();
        intent.putExtra("hasCheckBox", true);
        intent.putExtra("hasDone", true);
        intent.putExtra("code", resultCodeNum);
        intent.putExtra("maxNum", maxNum);
        intent.setClass(ReceiveThingsActivity.this, ItemContactsActivity.class);
        startActivityForResult(intent,resultCodeNum);
    }



}
