package com.app.officeautomationapp.activity;

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
import com.app.officeautomationapp.adapter.ApprovalReceiveThingsAdapter;
import com.app.officeautomationapp.adapter.MyApprovalReceiveThingsAdapter;
import com.app.officeautomationapp.bean.MyApprovalReceiveBean;
import com.app.officeautomationapp.bean.ReceiveThingsCheckBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
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
 * Created by yu on 2017/8/16.
 */

public class MyApprovalReceiveActivity extends BaseActivity implements View.OnClickListener  {
    private ListView listView;
    private ImageView iv_approval_back;
    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();
    static int page = 0;
    private static List<MyApprovalReceiveBean> listMyApprovalReceiveBean=new ArrayList<MyApprovalReceiveBean>();
    MyApprovalReceiveThingsAdapter approvalReceiveThingsAdapter;
    private UserDto userDto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_receive);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_approval);
        iv_approval_back = (ImageView) findViewById(R.id.iv_approval_back);
        iv_approval_back.setOnClickListener(this);
//        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sryt_swipe_listview_approval);
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);

    }


    private void initData() {
        approvalReceiveThingsAdapter = new MyApprovalReceiveThingsAdapter(this,R.layout.item_my_approval_receive, listMyApprovalReceiveBean,userDto);
        listView.setAdapter(approvalReceiveThingsAdapter);
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
                        listMyApprovalReceiveBean.clear();
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
//                Intent intent=new Intent(getActivity(), MessageDetailActivity.class);
//                intent.putExtra("data",listApproval.get(i));
//                startActivity(intent);
            }
        });
    }


    private void initProjects(final String str){
        RequestParams params = new RequestParams(Constants.GetMyResApplies);
        params.addHeader("access_token", userDto.getAccessToken());
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(MyApprovalReceiveActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==null||jsonObject.get("data").equals("")||jsonObject.get("data").toString().equals("[]"))
                        {
                            Toast.makeText(MyApprovalReceiveActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<MyApprovalReceiveBean> list=new ArrayList<MyApprovalReceiveBean>();
                            Type type=new TypeToken<List<MyApprovalReceiveBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            for(int i=0;i<list.size();i++)
                            {
                                listMyApprovalReceiveBean.add(list.get(i));
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
                Toast.makeText(MyApprovalReceiveActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
        approvalReceiveThingsAdapter.notifyDataSetChanged();
        if(str.equals("refresh")) {
            mSwipeRefreshHelper.refreshComplete();
            mSwipeRefreshHelper.setLoadMoreEnable(false);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }

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
