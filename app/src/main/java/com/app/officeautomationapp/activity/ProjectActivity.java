package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MyProjectBean;
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
 * Created by Administrator on 2017/11/17 0017.
 */

public class ProjectActivity extends BaseActivity implements  View.OnClickListener{

    private String type;//miaomu tujian

    private ImageView iv_project_back;

    private ListView listView;

    private List<MyProjectBean> listProject=new ArrayList<MyProjectBean>();
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();
    private UserDto userDto;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        type=getIntent().getStringExtra("type");
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        initView();
        initData();
    }

    private void initView(){
        iv_project_back=(ImageView)findViewById(R.id.iv_project_back);
        iv_project_back.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.list);
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.sryt_swipe_listview);
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);


    }

    private void initData(){
        adapter=new ArrayAdapter<String>(this,R.layout.items_view,listItems);
        if(listProject!=null)
        {
            listView.setAdapter(adapter);
        }

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
                        listItems.clear();
                        listProject.clear();
                        page = 0;
                        initProjects("refresh");
//                        adapter.notifyDataSetChanged();
//                        mSwipeRefreshHelper.refreshComplete();
//                        mSwipeRefreshHelper.setLoadMoreEnable(true);
                    }
                }, 1500);
            }
        });

//        mSwipeRefreshHelper.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void loadMore() {
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        initProjects("loadmore");
//                    }
//                }, 1000);
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ProjectActivity.this, ProjectTujianMiaomuActivity.class);
                intent.putExtra("data",listProject.get(i));
                intent.putExtra("type",type);
                startActivity(intent);
//                Toast.makeText(view.getContext(), listProject.get(i).getProjectName(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initProjects(final String str){
        RequestParams params = new RequestParams(Constants.getMyProject);
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
                        Toast.makeText(ProjectActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("dataList")==""||jsonObject.get("dataList")==null)
                        {
                            Toast.makeText(ProjectActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<MyProjectBean> list=new ArrayList<MyProjectBean>();
                            Type type=new TypeToken<List<MyProjectBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("dataList").toString(), type);
                            for(int i=0;i<list.size();i++)
                            {
                                listProject.add(list.get(i));
                                listItems.add(list.get(i).getProjectName());
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
                Toast.makeText(ProjectActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    private void getDataOver(String str)
    {
        adapter.notifyDataSetChanged();
        if(str.equals("refresh")) {
            mSwipeRefreshHelper.refreshComplete();
            mSwipeRefreshHelper.setLoadMoreEnable(false);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(false);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_project_back:
                this.finish();
                break;
            default:
                break;
        }
    }
}
