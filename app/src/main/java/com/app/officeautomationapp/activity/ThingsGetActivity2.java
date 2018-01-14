package com.app.officeautomationapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.MyApprovalReceiveThingsAdapter;
import com.app.officeautomationapp.adapter.RecyclerAdapter;
import com.app.officeautomationapp.adapter.SecondaryListAdapter;
import com.app.officeautomationapp.bean.MyApprovalReceiveBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.RvDividerItemDecoration;
import com.app.officeautomationapp.view.SpinnerDialogThingsDetails;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yu on 2017/8/16.
 */

public class ThingsGetActivity2 extends BaseActivity implements View.OnClickListener  {
    private ImageView iv_approval_back;
    private static List<MyApprovalReceiveBean> listMyApprovalReceiveBean=new ArrayList<MyApprovalReceiveBean>();
    private UserDto userDto;
    RecyclerAdapter adapter;
    private List<SecondaryListAdapter.DataTree<String, String>> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things_get2);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        initView();
//        initData();
    }

    private void initView() {

        iv_approval_back = (ImageView) findViewById(R.id.iv_approval_back);
        iv_approval_back.setOnClickListener(this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new RvDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new RecyclerAdapter(this,this);

        rv.setAdapter(adapter);
        initCate();
    }

    public void clickItem(int groupIndex,int itemIndex)
    {
//        Toast.makeText(this, datas.get(groupIndex).getSubItems().get(itemIndex).toString().replace(" -品牌：",""), Toast.LENGTH_SHORT).show();
        SpinnerDialogThingsDetails spinnerDialog=new SpinnerDialogThingsDetails(ThingsGetActivity2.this,R.style.DialogAnimations_SmileWindow,userDto,datas.get(groupIndex).getSubItems().get(itemIndex).toString().replace(" -品牌：",""));
        spinnerDialog.showSpinerDialog();
    }


    private void initCate(){
        RequestParams params = new RequestParams(Constants.GetCateList);
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
                        Toast.makeText(ThingsGetActivity2.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==null||jsonObject.get("data").equals("")||jsonObject.get("data").toString().equals("[]"))
                        {
                            Toast.makeText(ThingsGetActivity2.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            JSONArray jsonArray=jsonObject.getJSONArray("data");
                            if(jsonArray!=null)
                            {
                                for(int i=0;i<jsonArray.length();i++)
                                {
//                                    datas.add(new SecondaryListAdapter.DataTree<String, String>(new JSONObject(jsonArray.get(i).toString()).getString("CateName"), new
//                                            ArrayList<String>(){{add("sub 0"); add("sub 1"); add("sub 2");}}));
                                    initItem(i,new JSONObject(jsonArray.get(i).toString()).getInt("Id"),new JSONObject(jsonArray.get(i).toString()).getString("CateName"));

                                }
//                                adapter.setData(datas);
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(ThingsGetActivity2.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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



    private void initItem(int index , int cateId, final String cateName){
        RequestParams params = new RequestParams(Constants.GetResNameList+"?cateId="+cateId);
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
                        Toast.makeText(ThingsGetActivity2.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        JSONArray name=jsonObject.getJSONArray("data");
                        if(name!=null)
                        {
                            ArrayList<String> arrayList=new ArrayList<String>();
                            for(int i=0;i<name.length();i++)
                            {
                                arrayList.add(" -品牌："+name.getString(i));
                            }
                            datas.add(new SecondaryListAdapter.DataTree<String, String>(cateName, arrayList));
                            adapter.setData(datas);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(ThingsGetActivity2.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            case R.id.iv_approval_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
