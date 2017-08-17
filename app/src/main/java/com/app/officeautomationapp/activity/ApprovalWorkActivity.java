package com.app.officeautomationapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.MyGridAdapter;
import com.app.officeautomationapp.bean.ProjectItemBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by CS-711701-00027 on 2017/4/14.
 */

public class ApprovalWorkActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivWorkApprovalBack;
    private ImageView btnNoApproval;
    private ImageView btnWorkStartMy;
    private ImageView btnWorkSendMe;


    private TextView bar_num;
    private int msgCount;

    private MyGridView mygridview;
    ArrayList<ProjectItemBean> mList=new ArrayList<ProjectItemBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_approval);
        bar_num = (TextView) findViewById(R.id.bar_num);

        ivWorkApprovalBack=(ImageView)findViewById(R.id.iv_work_approval_back);
        ivWorkApprovalBack.setOnClickListener(this);
        btnNoApproval=(ImageView)findViewById(R.id.btn_no_approval);
        btnNoApproval.setOnClickListener(this);
        btnWorkStartMy=(ImageView)findViewById(R.id.btn_work_start_my);
        btnWorkStartMy.setOnClickListener(this);
        btnWorkSendMe=(ImageView)findViewById(R.id.btn_work_send_me);
        btnWorkSendMe.setOnClickListener(this);

        mygridview = (MyGridView)findViewById(R.id.mygridview);
        initData();
        //实例化一个适配器
        MyGridAdapter myGridAdapter1=new MyGridAdapter(this,R.layout.grid_item,mList);
        //为GridView设置适配器
        mygridview.setAdapter(myGridAdapter1);
        mygridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickBtn(position);
            }
        });
        initNum(myGridAdapter1);
    }

    private void initNum(final MyGridAdapter myGridAdapter1 )
    {
        //准备要添加的数据条目
        RequestParams params = new RequestParams(Constants.GetTodoTip);
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
                        Toast.makeText(ApprovalWorkActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        JSONObject json=jsonObject.getJSONObject("data");
                        int TotalCount=json.getInt("TotalCount");
                        int MachineCount=json.getInt("MachineCount");
                        int JobCount=json.getInt("JobCount");
                        int SignCount=json.getInt("SignCount");
                        int LeaveCount=json.getInt("LeaveCount");
                        int UsedCount=json.getInt("UsedCount");
                        int TreeCount=json.getInt("TreeCount");
                        int CivilCount=json.getInt("CivilCount");
                        int ContractCount=json.getInt("ContractCount");
                        int FeeCount=json.getInt("FeeCount");
                        if(mList.size()>0)
                        {
                            for(int i=0;i<mList.size();i++)
                            {
                                if(mList.get(i).getId()==1)
                                {
                                    mList.get(i).setNum(MachineCount);
                                }
                                if(mList.get(i).getId()==2)
                                {
                                    mList.get(i).setNum(SignCount);
                                }
                                if(mList.get(i).getId()==3)
                                {
                                    mList.get(i).setNum(JobCount);
                                }
                                if(mList.get(i).getId()==4)
                                {
                                    mList.get(i).setNum(LeaveCount);
                                }
                                if(mList.get(i).getId()==5)
                                {
                                    mList.get(i).setNum(UsedCount);
                                }
                                if(mList.get(i).getId()==6)
                                {
                                    mList.get(i).setNum(TreeCount);
                                }
                                if(mList.get(i).getId()==7)
                                {
                                    mList.get(i).setNum(CivilCount);
                                }
                                if(mList.get(i).getId()==8)
                                {
                                    mList.get(i).setNum(ContractCount);
                                }
                                if(mList.get(i).getId()==9)
                                {
                                    mList.get(i).setNum(FeeCount);
                                }
                            }
                            myGridAdapter1.refresh(mList);
                        }
                        if(TotalCount>0)
                        {
                            bar_num.setVisibility(View.VISIBLE);
                            bar_num.setText(TotalCount+"");
                        }
                        else
                        {
                            bar_num.setVisibility(View.INVISIBLE);
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
                Toast.makeText(ApprovalWorkActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
    public void onClick(View view) {
        Intent intent=new Intent(this, ApprovalActivity.class);
        switch (view.getId()) {
            case R.id.btn_no_approval:
                intent.putExtra("url",Constants.GetMyDoingWork);
                startActivity(intent);
                break;
            case R.id.btn_work_send_me:
                intent.putExtra("url",Constants.GetMyPostWork);
                startActivity(intent);
                break;
            case R.id.btn_work_start_my:
                intent.putExtra("url",Constants.GetMyWorkFlows);
                startActivity(intent);
                break;
            case R.id.iv_work_approval_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    private void clickBtn(int position)
    {
        ProjectItemBean projectItemBean= mList.get(position);
        Intent intent=new Intent(this, ApprovalActivity.class);
        if(!"".equals(projectItemBean.getGuid()))
        {
            intent.putExtra("url",Constants.GetMyDoingWork+"?workGuid="+projectItemBean.getGuid());
            intent.putExtra("hiddenTitle","1");
            startActivity(intent);
        }
        else//特殊接口
        {
            if(projectItemBean.getId()==5)
            {
                intent=new Intent(this, ApprovalReceiveActivity.class);
                startActivity(intent);
            }
        }
    }

    private void initData()
    {
        ProjectItemBean projectItemBean=new ProjectItemBean();
        projectItemBean.setLocalPic(R.mipmap.icon_time);
        projectItemBean.setMenuTitle("台班");
        projectItemBean.setGuid("d1693633-e78a-4f7d-97fc-a3742639eaa9");
        projectItemBean.setId(1);
        mList.add(projectItemBean);
        ProjectItemBean projectItemBean2=new ProjectItemBean();
        projectItemBean2.setLocalPic(R.mipmap.icon_time);
        projectItemBean2.setMenuTitle("用章");
        projectItemBean2.setGuid("f58f8da6-a7f2-45d9-8fb1-70ec0bdc83f2");
        projectItemBean2.setId(2);
        mList.add(projectItemBean2);
        ProjectItemBean projectItemBean3=new ProjectItemBean();
        projectItemBean3.setLocalPic(R.mipmap.icon_time);
        projectItemBean3.setMenuTitle("签工");
        projectItemBean3.setGuid("9fac41f7-fd37-4959-8bc0-7c35922fd204");
        projectItemBean3.setId(3);
        mList.add(projectItemBean3);
        ProjectItemBean projectItemBean4=new ProjectItemBean();
        projectItemBean4.setLocalPic(R.mipmap.icon_time);
        projectItemBean4.setMenuTitle("请假");
        projectItemBean4.setGuid("e1e8d30f-2550-40f6-88eb-aa393124c674");
        projectItemBean4.setId(4);
        mList.add(projectItemBean4);
        ProjectItemBean projectItemBean5=new ProjectItemBean();
        projectItemBean5.setLocalPic(R.mipmap.icon_time);
        projectItemBean5.setMenuTitle("领用"); //GetUnCheckApplyResList
        projectItemBean5.setGuid("");
        projectItemBean5.setId(5);
        mList.add(projectItemBean5);
        ProjectItemBean projectItemBean6=new ProjectItemBean();
        projectItemBean6.setLocalPic(R.mipmap.icon_time);
        projectItemBean6.setMenuTitle("苗木验收");
        projectItemBean6.setGuid("5de2a402-280a-46ff-ab6f-fbab969a79a9");
        projectItemBean6.setId(6);
        mList.add(projectItemBean6);
        ProjectItemBean projectItemBean7=new ProjectItemBean();
        projectItemBean7.setLocalPic(R.mipmap.icon_time);
        projectItemBean7.setMenuTitle("土建验收");
        projectItemBean7.setGuid("b04bfff9-9b7d-4f2f-8511-f49368e43d15");
        projectItemBean7.setId(7);
        mList.add(projectItemBean7);
        ProjectItemBean projectItemBean8=new ProjectItemBean();
        projectItemBean8.setLocalPic(R.mipmap.icon_time);
        projectItemBean8.setMenuTitle("合同");
        projectItemBean8.setGuid("ec5cca2d-a335-4610-af04-a577d2955699");
        projectItemBean8.setId(8);
        mList.add(projectItemBean8);
        ProjectItemBean projectItemBean9=new ProjectItemBean();
        projectItemBean9.setLocalPic(R.mipmap.icon_time);
        projectItemBean9.setMenuTitle("费用报销");
        projectItemBean9.setGuid("632c0da6-14a2-4daa-a5df-d311be6900a3");
        projectItemBean9.setId(9);
        mList.add(projectItemBean9);

//        getMenuImageStr getMenuTitle getNum
    }

    public void setMessageCount(int count) {
        msgCount = count;
        if (count <= 0) {
            bar_num.setVisibility(View.GONE);
        } else {
            bar_num.setVisibility(View.VISIBLE);
            if (count < 100) {
                bar_num.setText(count + "");
            } else {
                bar_num.setText("99+");
            }
        }
    }
}
