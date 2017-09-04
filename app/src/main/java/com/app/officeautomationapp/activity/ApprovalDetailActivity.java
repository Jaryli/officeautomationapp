package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ApprovalDetailAdapter;
import com.app.officeautomationapp.adapter.ApprovalNextStepAdapter;
import com.app.officeautomationapp.bean.ApprovalDetailBean;
import com.app.officeautomationapp.bean.ApprovalPostBean;
import com.app.officeautomationapp.bean.FlowHistorie;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.NextStep;
import com.app.officeautomationapp.bean.SortModel;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;
import com.app.officeautomationapp.view.SpinnerDialog3;
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
 * Created by CS-711701-00027 on 2017/8/30.
 */

public class ApprovalDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView approval_pic;
    private TextView approval_title;
    private TextView approval_type;
    private ImageView iv_approval_back;
    private WebView webView;
    private ListView list_item;
    private MyGridView mygridview;
    private LinearLayout llgridview;
    private ApprovalDetailAdapter adapter;
    private static List<FlowHistorie> listFlowHistories=new ArrayList<FlowHistorie>();
    private static List<NextStep> listNextSteps=new ArrayList<NextStep>();

    SpinnerDialog3 spinnerDialog;
    ApprovalDetailBean approvalDetailBean=new ApprovalDetailBean();
    private int nextStepSort=0;
    private int Hid;
    private int Wid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail);
        initView();
        initData();
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
        if(resultCode>0)
        {
            ArrayList<SortModel> result_value = (ArrayList<SortModel>)data.getSerializableExtra("data");
            spinnerDialog.refreshData(requestCode,result_value);
        }
    }

    private void initView()
    {
        approval_pic=(TextView)findViewById(R.id.approval_pic);
        approval_title=(TextView)findViewById(R.id.approval_title);
        approval_type=(TextView)findViewById(R.id.approval_type);
        webView=(WebView)findViewById(R.id.webView);
        list_item=(ListView)findViewById(R.id.list_item);
        mygridview=(MyGridView)findViewById(R.id.mygridview);
        llgridview=(LinearLayout)findViewById(R.id.llgridview);
        iv_approval_back=(ImageView)findViewById(R.id.iv_approval_back);
        iv_approval_back.setOnClickListener(this);
    }


    private void initData()
    {
        Intent intent=getIntent();
        Hid = intent.getSerializableExtra("hid")==null?0:(Integer) intent.getSerializableExtra("hid");
        Wid = intent.getSerializableExtra("wid")==null?0:(Integer) intent.getSerializableExtra("wid");
        RequestParams params=new RequestParams();
        if(Hid>0)
        {
            params= new RequestParams(Constants.GetWorkView);
            params.addQueryStringParameter("hid",Hid+"");

        }
        if(Wid>0)
        {
            params= new RequestParams(Constants.GetWorkInfo);
            params.addQueryStringParameter("workId",Wid+"");

        }

        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(this.getApplicationContext(),"user");
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
                        Toast.makeText(ApprovalDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==null||jsonObject.get("data").equals("")||jsonObject.get("data").toString().equals("[]"))
                        {
                            Toast.makeText(ApprovalDetailActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();

                            Type type=new TypeToken<ApprovalDetailBean>(){}.getType();
                            approvalDetailBean=gson.fromJson(jsonObject.get("data").toString(), type);

                            listFlowHistories=approvalDetailBean.getFlowHistories();
                            FlowHistorie flowHistorie=new FlowHistorie();
                            flowHistorie.setUserTrueName("我");
                            listFlowHistories.add(flowHistorie);
                            adapter = new ApprovalDetailAdapter(ApprovalDetailActivity.this,R.layout.item_approval_detail, listFlowHistories);
                            list_item.setAdapter(adapter);
                            setListViewHeightBasedOnChildren(list_item);
                            approval_title.setText(approvalDetailBean.getWorkName());

                            WebSettings webSettings = webView.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webView.loadDataWithBaseURL(null,"<style>table { width: 100%%; border-top: 1px solid #eee; border-left: 1px solid #eee; }td { border-right: 1px solid #eee; border-bottom: 1px solid #eee; padding: 8px; }</style><table>"+approvalDetailBean.getFormView()+"</table>", "text/html",  "utf-8", null);
                            if(approvalDetailBean.getNextSteps()==null)
                            {
                                llgridview.setVisibility(View.GONE);
                            }
                            else {
                                listNextSteps=approvalDetailBean.getNextSteps();
                                ApprovalNextStepAdapter myGridAdapter1=new ApprovalNextStepAdapter(ApprovalDetailActivity.this,R.layout.item_approval_nextstep,listNextSteps);
                                mygridview.setNumColumns(listNextSteps.size());
                                //为GridView设置适配器
                                mygridview.setAdapter(myGridAdapter1);
                                mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        clickBtn(listNextSteps,position);
                                    }
                                });
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
                Toast.makeText(ApprovalDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    private void clickBtn(List<NextStep> list,int position) {
//        Toast.makeText(this,list.get(position).getAFS_Name().toString(),Toast.LENGTH_SHORT).show();
        nextStepSort=list.get(position).getAFS_Sort();
        ArrayList<SortModel> defaultList1=null;
        if(list.get(position).getDefaultUserId1()!=null&&!list.get(position).getDefaultUserId1().equals("0")&&!list.get(position).getDefaultUserId1().equals(""))
        {
            defaultList1=new ArrayList<SortModel>();
            SortModel sortModel=new SortModel();
            sortModel.setId(Integer.parseInt(list.get(position).getDefaultUserId1()));
            sortModel.setName(list.get(position).getDefaultRealName1());
            defaultList1.add(sortModel);
        }
        ArrayList<SortModel> defaultList2=null;
        if(list.get(position).getDefaultUserId2()!=null&&!list.get(position).getDefaultUserId2().equals("0")&&!list.get(position).getDefaultUserId2().equals(""))
        {
            defaultList2=new ArrayList<SortModel>();
            SortModel sortModel=new SortModel();
            sortModel.setId(Integer.parseInt(list.get(position).getDefaultUserId2()));
            sortModel.setName(list.get(position).getDefaultRealName2());
            defaultList2.add(sortModel);
        }
        spinnerDialog=new SpinnerDialog3(this,R.style.DialogAnimations_SmileWindow,defaultList1,defaultList2);
        spinnerDialog.bindOnSpinerListener(new SpinnerDialog3.OnDoneClick() {
            @Override
            public void onClick(int status, String reason,ArrayList<SortModel> list1,ArrayList<SortModel> list2) {
                //提交
                postData(status,reason,list1,list2);
            }
        });
        spinnerDialog.showSpinerDialog();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        if (listFlowHistories == null) {
            return;
        }
        int totalHeight = 0;//mListItemHeight
        int a=listView.getHeight();
        for (int i = 0; i < listFlowHistories.size(); i++) {
            totalHeight += 189;//暂时这样
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_approval_back:
                this.finish();
                break;
            default:
                break;
        }
    }


    private void postData(int status, String reason,ArrayList<SortModel> list1,ArrayList<SortModel> list2)
    {
        String userId1="";
        if(list1.size()>1)
        {
            list1.remove(list1.size()-1);
            for(SortModel sortModel:list1)
            {
                userId1+=sortModel.getId()+",";
            }
            userId1=userId1.substring(0,userId1.length()-1);
        }
        String userId2="";
        if(list2.size()>1)
        {
            list2.remove(list2.size()-1);
            for(SortModel sortModel:list2)
            {
                userId2+=sortModel.getId()+",";
            }
            userId2=userId2.substring(0,userId2.length()-1);
        }

        ApprovalPostBean approvalPostBean=new ApprovalPostBean();
        approvalPostBean.setWorkId(approvalDetailBean.getWorkId());
        approvalPostBean.setMsg(reason);
        approvalPostBean.setResultCode(status);
        approvalPostBean.setUserIds1(userId1);
        approvalPostBean.setUserIds2(userId2);
        approvalPostBean.setNextStepSort(nextStepSort);
        approvalPostBean.sethId(Hid);
        Gson gson = new Gson();
        String result = gson.toJson(approvalPostBean);

        RequestParams params = new RequestParams(Constants.HandleWork);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(this.getApplicationContext(),"user");
        params.addHeader("access_token", userDto.getAccessToken());
        params.setBodyContent("'"+result+"'");
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(ApprovalDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(ApprovalDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        /*
                         * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                         */
                        setResult(1, intent);
                        ApprovalDetailActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(ApprovalDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
}
