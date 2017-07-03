package com.app.officeautomationapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.MessageDetailActivity;
import com.app.officeautomationapp.adapter.MessageAdapter;
import com.app.officeautomationapp.bean.MessageBean;
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
public class MessageFragment extends Fragment {

    private List<MessageBean> listMessage=new ArrayList<MessageBean>();

    private ListView listView;
    private MessageAdapter adapter;

    SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshHelper mSwipeRefreshHelper;
    private Handler mHandler = new Handler();

    int page = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message,null);
        initView(view);
        initData(view);
        return view;
    }

    private void initView(View view) {
        listView=(ListView) view.findViewById(R.id.lv_message);
//        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.sryt_swipe_listview_message);
        swipeRefreshLayout.setColorSchemeColors(Color.GRAY);
    }

    private void initData(final View view) {
        adapter = new MessageAdapter(view.getContext(),R.layout.message_item, listMessage);
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
                        listMessage.clear();
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
//                Toast.makeText(view.getContext(), listMessage.get(i).getNT_Title(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), MessageDetailActivity.class);
                intent.putExtra("data",listMessage.get(i));
                listMessage.get(i).setIsRead(1);
                adapter.notifyDataSetChanged();
                startActivity(intent);
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
        RequestParams params = new RequestParams(Constants.getMessage);
        params.addQueryStringParameter("pageIndex",(page+1)+"");
        params.addQueryStringParameter("pageSize","10");
        UserDto userDto= (UserDto)SharedPreferencesUtile.readObject(getContext().getApplicationContext(),"user");
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
                        Toast.makeText(getActivity(),jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            List<MessageBean> list=new ArrayList<MessageBean>();
                            Type type=new TypeToken<List<MessageBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for(int i=0;i<list.size();i++)
                            {
                                listMessage.add(list.get(i));
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
                Toast.makeText(getActivity(),"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

}
