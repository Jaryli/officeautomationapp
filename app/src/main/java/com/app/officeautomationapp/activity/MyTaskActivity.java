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
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.MessageAdapter;
import com.app.officeautomationapp.adapter.MytaskAdapter;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.MyTaskBean;
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
public class MyTaskActivity extends BaseActivity implements  View.OnClickListener {

    private List<MyTaskBean> listMyTask=new ArrayList<MyTaskBean>();

    private ListView listView;
    private MytaskAdapter adapter;
    private ImageView ivMytaskBack;


    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();
    private UserDto userDto;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        initView();
        initData();
    }


    private void initView() {
        listView=(ListView)findViewById(R.id.lv_mytask);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.sryt_swipe_listview_mytask);
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);
        ivMytaskBack=(ImageView) findViewById(R.id.iv_mytask_back);
        ivMytaskBack.setOnClickListener(this);
    }

    private void initData() {
        adapter = new MytaskAdapter(this,R.layout.task_item, listMyTask);
        listView.setAdapter(adapter);
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
                        listMyTask.clear();
                        page = 0;
                        initProjects("refresh");
//                        adapter.notifyDataSetChanged();
//                        mSwipeRefreshHelper.refreshComplete();
//                        mSwipeRefreshHelper.setLoadMoreEnable(true);
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
                        initProjects("loadmore");
//                        adapter.notifyDataSetChanged();
//                        mSwipeRefreshHelper.loadMoreComplete(true);
//                        Toast.makeText(view.getContext(), "加载成功", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MyTaskActivity.this, MyTaskDetailActivity.class);
                intent.putExtra("data",listMyTask.get(i));
                intent.putExtra("isMyTask",1);
                startActivity(intent);
//                Toast.makeText(view.getContext(), listMyTask.get(i).getTaskName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataOver(String str)
    {
        adapter.notifyDataSetChanged();
        if(str.equals("refresh")) {
            mSwipeRefreshHelper.refreshComplete();
            mSwipeRefreshHelper.setLoadMoreEnable(true);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }

    }


    private void initProjects(final String str){
        RequestParams params = new RequestParams(Constants.GetMyTaskList);
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    int recordCount=jsonObject.getInt("recordCount");//总条数
                    if(re!=1)
                    {
                        Toast.makeText(MyTaskActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(MyTaskActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<MyTaskBean> list=new ArrayList<MyTaskBean>();
                            Type type=new TypeToken<List<MyTaskBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for(int i=0;i<list.size();i++)
                            {
                                listMyTask.add(list.get(i));
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
                Toast.makeText(MyTaskActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("JAVA", "onCancelled:" + cex);

            }
            @Override
            public void onFinished() {
                Log.e("JAVA", "onFinished:" + "");
                getDataOver(str);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mytask_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
