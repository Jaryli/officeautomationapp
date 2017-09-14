package com.app.officeautomationapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.HiddenProjectAdapter;
import com.app.officeautomationapp.bean.HiddenProjectBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyListView;
import com.app.officeautomationapp.view.MySwipeRefreshLayout;
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

import static com.chanven.lib.cptr.utils.PtrLocalDisplay.dp2px;


/**
 * Created by yu on 2017/2/27.
 */
public class HiddenProjectActivity extends BaseActivity implements View.OnClickListener {//AbsListView.OnScrollListener

    private ImageView tvBack;
    private TextView tvAdd;

    private List<HiddenProjectBean> hiddenProjectBeenList=new ArrayList<HiddenProjectBean>();
    private MyListView listView;
    private HiddenProjectAdapter adapter;

//    PtrClassicFrameLayout ptrClassicFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();
    private Context mContext;
    private final int codeNum=5;
    private UserDto userDto;
    int page = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_project);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        mContext=this;
        initView();
        initData();

        tvBack=(ImageView)findViewById(R.id.iv_hidden_back);
        tvBack.setOnClickListener(this);
        tvAdd=(TextView)findViewById(R.id.tv_hidden_add);
        tvAdd.setOnClickListener(this);
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
        if(resultCode==codeNum)
        {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshHelper.autoRefresh();
                }
            });
        }
    }

    private void initView() {
        listView=(MyListView) findViewById(R.id.lv_hidden_project);
//        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        swipeRefreshLayout=(SwipeRefreshLayout) this.findViewById(R.id.sryt_swipe_listview);
       swipeRefreshLayout.setColorSchemeColors(Color.GRAY);
    }

    private void initData() {
        adapter = new HiddenProjectAdapter(this,R.layout.hidden_project_item, hiddenProjectBeenList);
        listView.setAdapter(adapter);
        listView.setSwipeRefreshLayout(swipeRefreshLayout);//解决左右和下拉冲突问题

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
                        hiddenProjectBeenList.clear();
                        page = 0;
                        initProjects("refresh");
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
//                Toast.makeText(view.getContext(), listMessage.get(i).getNT_Title(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HiddenProjectActivity.this, HiddenProjectAddActivity.class);
                intent.putExtra("method","update");
                intent.putExtra("data",hiddenProjectBeenList.get(i));
                startActivityForResult(intent,codeNum);
            }
        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                hiddenProjectBeenList.remove(position);
//                adapter.notifyDataSetChanged();
//                return true;
//            }
//        });

        listView.setRemoveListener(new MyListView.RemoveListener(){
            @Override
            public void removeItem(MyListView.RemoveDirection direction, final int position) {
                AlertDialog.Builder builder=new AlertDialog.Builder(HiddenProjectActivity.this);
                builder.setTitle("隐蔽工程");//设置标题
                builder.setIcon(R.mipmap.i_delete);//设置图标
                builder.setMessage("确定删除这项吗？");//设置内容
                AlertDialog dialog;
                switch (direction) {
                    case RIGHT:
                        /*添加对话框中确定按钮和点击事件*/
                        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                removeData(position);
                                hiddenProjectBeenList.remove(position);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        /*添加对话框中取消按钮和点击事件*/
                        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });
                        dialog=builder.create();//获取dialog
                        dialog.show();//显示对话框


                        break;
                    case LEFT:
                        /*添加对话框中确定按钮和点击事件*/
                        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                removeData(position);
                                hiddenProjectBeenList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        /*添加对话框中取消按钮和点击事件*/
                        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            }
                        });
                        dialog = builder.create();
                        dialog.show();//显示对话框
                        break;

                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hidden_back:
                this.finish();
                break;
            case R.id.tv_hidden_add:
                Intent intent=new Intent(this,HiddenProjectAddActivity.class);
                intent.putExtra("method","add");
                startActivityForResult(intent,codeNum);
                break;
            default:
                break;

        }
    }

    private void initProjects(final String str){
        RequestParams params = new RequestParams(Constants.getHiddenProject);
        params.addQueryStringParameter("projectName","");
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
                        Toast.makeText(HiddenProjectActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(HiddenProjectActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<HiddenProjectBean> list=new ArrayList<HiddenProjectBean>();
                            Type type=new TypeToken<List<HiddenProjectBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for(int i=0;i<list.size();i++)
                            {
                                hiddenProjectBeenList.add(list.get(i));
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
                Toast.makeText(HiddenProjectActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    private void removeData(int position)
    {
        RequestParams params = new RequestParams(Constants.deleteHiddenProject+"/"+hiddenProjectBeenList.get(position).getId());
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(HiddenProjectActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(HiddenProjectActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HiddenProjectActivity.this,"删除失败，系统异常",Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(HiddenProjectActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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



}
