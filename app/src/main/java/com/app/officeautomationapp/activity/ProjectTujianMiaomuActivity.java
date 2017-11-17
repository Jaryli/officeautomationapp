package com.app.officeautomationapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ProjectMiaomuTujianAdapter;
import com.app.officeautomationapp.bean.MyProjectBean;
import com.app.officeautomationapp.bean.MyTaskBean;
import com.app.officeautomationapp.bean.ProjectMiaomuTujianBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.ProjectDialog;
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

public class ProjectTujianMiaomuActivity extends BaseActivity implements  View.OnClickListener{

    private String type;//miaomu tujian

    private ImageView iv_project_back;

    private ListView listView;


    ProjectMiaomuTujianAdapter adapter;



    private MyProjectBean myProjectBean;
    private TextView tv_title;
    private EditText et_project_search;
    private Button btn_project_cancel;
    private InputMethodManager imm;

    private List<ProjectMiaomuTujianBean> listProjectMiaomuTujianBean=new ArrayList<ProjectMiaomuTujianBean>();

    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
//    private Handler mHandler = new Handler();
    private UserDto userDto;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_miaomutujian);
        Intent intent=getIntent();
        myProjectBean = (MyProjectBean) intent.getSerializableExtra("data");
        type=intent.getStringExtra("type");
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
        tv_title=(TextView)findViewById(R.id.tv_title);
        tv_title.setText(type.equals("tujian")?"土建":"苗木");
        et_project_search=(EditText)findViewById(R.id.et_project_search);
        imm = (InputMethodManager) et_project_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//键盘
        //键盘搜索事件
        et_project_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                imm.hideSoftInputFromWindow(et_project_search.getWindowToken(), 0);//关闭软键盘
                //搜索动作
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
                return true;
            }
        });
        btn_project_cancel=(Button)findViewById(R.id.btn_project_cancel);
        btn_project_cancel.setOnClickListener(this);
    }

    private void initData(){
        adapter=new ProjectMiaomuTujianAdapter(this,R.layout.item_project,listProjectMiaomuTujianBean);

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
                        listProjectMiaomuTujianBean.clear();
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
                    }
                }, 1000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ProjectDialog projectDialog=new ProjectDialog(ProjectTujianMiaomuActivity.this,listProjectMiaomuTujianBean.get(i),type,R.style.DialogAnimations_SmileWindow);
                projectDialog.showSpinerDialog();
//                Toast.makeText(view.getContext(), listProjectMiaomuTujianBean.get(i).getProjectName(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initProjects(final String str){
        String url=Constants.GetTreeMainList;
        if(type.equals("tujian"))
        {
            url=Constants.GetCivilMainList;
        }
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        params.addQueryStringParameter("projectName",myProjectBean.getProjectName());
        if(!et_project_search.getText().equals(""))
        {
            params.addQueryStringParameter("orderCode",et_project_search.getText().toString());
        }
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
                        Toast.makeText(ProjectTujianMiaomuActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(ProjectTujianMiaomuActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<ProjectMiaomuTujianBean> list=new ArrayList<ProjectMiaomuTujianBean>();
                            Type type=new TypeToken<List<ProjectMiaomuTujianBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
                            for(int i=0;i<list.size();i++)
                            {
                                listProjectMiaomuTujianBean.add(list.get(i));
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
                Toast.makeText(ProjectTujianMiaomuActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
            mSwipeRefreshHelper.setLoadMoreEnable(true);
        }
        else
        {
            mSwipeRefreshHelper.loadMoreComplete(true);
        }
    }


    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            //更新UI
            switch (msg.what)
            {
                case 1:
                    listProjectMiaomuTujianBean.clear();
                    page = 0;
                    swipeRefreshLayout.setRefreshing(true);
                    initProjects("refresh");

                    break;
                case 2:
                    et_project_search.setText("");
                    listProjectMiaomuTujianBean.clear();
                    page = 0;
                    swipeRefreshLayout.setRefreshing(true);
                    initProjects("refresh");
                    break;
            }
        };
    };




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_project_back:
                this.finish();
                break;
            case R.id.btn_project_cancel:
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
                break;
            default:
                break;
        }
    }
}
