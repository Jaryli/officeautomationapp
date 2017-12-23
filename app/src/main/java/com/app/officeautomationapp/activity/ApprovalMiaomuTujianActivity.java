package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.MiaomuAdapter;
import com.app.officeautomationapp.adapter.TujianAdapter;
import com.app.officeautomationapp.bean.MiaomuBean;
import com.app.officeautomationapp.bean.TujianBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
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
 * Created by yu on 2017/3/18.
 */
public class ApprovalMiaomuTujianActivity extends BaseActivity implements  View.OnClickListener {

    private List<MiaomuBean> listMiaomuBean=new ArrayList<MiaomuBean>();
    private List<TujianBean> listTujianBean=new ArrayList<TujianBean>();

    private ListView listView;
    private MiaomuAdapter miaomuAdapter;
    private TujianAdapter tujianAdapter;
    private ImageView ivBack;
    private TextView tv_title;


    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();
    private UserDto userDto;

    int page = 0;
    Integer approvalType;
    Integer workId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_miaomutujian);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        Intent intent=getIntent();
        approvalType=intent.getIntExtra("approvalType",0);
        workId=intent.getIntExtra("workId",0);
        initView();

        if(approvalType==6)
        {
            initMiaomuData();
        }
        if(approvalType==7)
        {
            initTujianData();
        }

    }


    private void initView() {
        listView=(ListView)findViewById(R.id.lv_message);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.sryt_swipe_listview_message);
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);
        ivBack=(ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        tv_title=(TextView) findViewById(R.id.tv_title);
        if(approvalType==6)
        {
            tv_title.setText("苗木审批");
        }
        if(approvalType==7)
        {
            tv_title.setText("土建审批");
        }
    }

    private void initMiaomuData() {
        miaomuAdapter = new MiaomuAdapter(this,R.layout.item_yanshou, listMiaomuBean);
        listView.setAdapter(miaomuAdapter);
        mSwipeRefreshHelper = new SwipeRefreshHelper(swipeRefreshLayout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshHelper.autoRefresh();
            }
        });

        mSwipeRefreshHelper.setOnSwipeRefreshListener(new SwipeRefreshHelper.OnSwipeRefreshListener() {
            @Override
            public void onfresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listMiaomuBean.clear();
                        page = 0;
                        initMiaomuProjects("refresh");
                    }
                }, 1500);
            }
        });

        mSwipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initMiaomuProjects("loadmore");
                    }
                }, 1000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ApprovalMiaomuTujianActivity.this, MiaomuDetailActivity.class);
                intent.putExtra("data",listMiaomuBean.get(i));
                startActivity(intent);
                //// TODO: 2017/12/23  
            }
        });
    }

    private void getMiaomuDataOver(String str)
    {
        miaomuAdapter.notifyDataSetChanged();
        if(str.equals("refresh")) {
            mSwipeRefreshHelper.refreshComplete();
            mSwipeRefreshHelper.setLoadMoreEnable(true);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }

    }


    private void initMiaomuProjects(final String str){
        RequestParams params = new RequestParams(Constants.GetTreeDetailListByWorkId);
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        params.addQueryStringParameter("workId",workId+"");
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
                        Toast.makeText(ApprovalMiaomuTujianActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(ApprovalMiaomuTujianActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<MiaomuBean> list=new ArrayList<MiaomuBean>();
                            Type type=new TypeToken<List<MiaomuBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            for(int i=0;i<list.size();i++)
                            {
                                listMiaomuBean.add(list.get(i));
                            }
                            page++;
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
                Toast.makeText(ApprovalMiaomuTujianActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("JAVA", "onCancelled:" + cex);

            }
            @Override
            public void onFinished() {
                Log.e("JAVA", "onFinished:" + "");
                getMiaomuDataOver(str);

            }
        });
    }




    private void initTujianData() {
        tujianAdapter = new TujianAdapter(this,R.layout.item_yanshou, listTujianBean);
        listView.setAdapter(tujianAdapter);
        mSwipeRefreshHelper = new SwipeRefreshHelper(swipeRefreshLayout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshHelper.autoRefresh();
            }
        });

        mSwipeRefreshHelper.setOnSwipeRefreshListener(new SwipeRefreshHelper.OnSwipeRefreshListener() {
            @Override
            public void onfresh() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listTujianBean.clear();
                        page = 0;
                        initTujianProjects("refresh");
                    }
                }, 1500);
            }
        });

        mSwipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initTujianProjects("loadmore");
                    }
                }, 1000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ApprovalMiaomuTujianActivity.this, MessageDetailActivity.class);
                intent.putExtra("data",listTujianBean.get(i));
                startActivity(intent);
            }
        });
    }

    private void getTujianDataOver(String str)
    {
        tujianAdapter.notifyDataSetChanged();
        if(str.equals("refresh")) {
            mSwipeRefreshHelper.refreshComplete();
            mSwipeRefreshHelper.setLoadMoreEnable(true);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }

    }


    private void initTujianProjects(final String str){
        RequestParams params = new RequestParams(Constants.GetCivilDetailListByWorkId);
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        params.addQueryStringParameter("workId",workId+"");
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
                        Toast.makeText(ApprovalMiaomuTujianActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(ApprovalMiaomuTujianActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<TujianBean> list=new ArrayList<TujianBean>();
                            Type type=new TypeToken<List<TujianBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            for(int i=0;i<list.size();i++)
                            {
                                listTujianBean.add(list.get(i));
                            }
                            page++;
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
                Toast.makeText(ApprovalMiaomuTujianActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("JAVA", "onCancelled:" + cex);

            }
            @Override
            public void onFinished() {
                Log.e("JAVA", "onFinished:" + "");
                getTujianDataOver(str);

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
