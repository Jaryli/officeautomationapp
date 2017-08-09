package com.app.officeautomationapp.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalWorkActivity;
import com.app.officeautomationapp.activity.HiddenProjectActivity;
import com.app.officeautomationapp.activity.ItemContactsActivity;
import com.app.officeautomationapp.activity.MessageActivity;
import com.app.officeautomationapp.activity.MyTaskActivity;
import com.app.officeautomationapp.activity.ProjectItemActivity;
import com.app.officeautomationapp.activity.ReceiveThingsActivity;
import com.app.officeautomationapp.activity.StartWorkActivity;
import com.app.officeautomationapp.activity.WorkQingjiaActivity;
import com.app.officeautomationapp.activity.WorkTaibanActivity;
import com.app.officeautomationapp.activity.WorkYonggongActivity;
import com.app.officeautomationapp.activity.WorkYongzhangActivity;
import com.app.officeautomationapp.adapter.MyGridAdapter;
import com.app.officeautomationapp.bean.ProjectItemBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by yu on 2017/3/18.
 */
public class WorkFragment extends Fragment  implements View.OnClickListener{

    private RollPagerView rollPagerView;

    private ViewPager viewPager;

    private Fragment[] fragments;

    private List<ImageView> mDots;//定义一个集合存储三个dot

    private Button btnHiddenProject;//按钮
    private Button btnWorkApproval;
    private Button btnReceiveThings;
    private Button btnStartWork;
    private Button btnMessage;


    private LinearLayout llMessage;
    private LinearLayout llManageItem;

    private MyGridView gv1;
    private MyGridView gv2;
    private MyGridView gv3;
    private ArrayList<ProjectItemBean> list1;
    private ArrayList<ProjectItemBean> list2;
    private ArrayList<ProjectItemBean> list3;
    private View Tview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_work,null);
        //work header图片轮播
        rollPagerViewSet(view);
        //dot
//        viewPagerSet(view);
//        viewPager.setAdapter(new MyAdpater(this.getFragmentManager()));

//        btnHiddenProject=(Button)view.findViewById(R.id.btn_hidden_project);
//        btnHiddenProject.setOnClickListener(this);
        btnWorkApproval=(Button) view.findViewById(R.id.btn_work_approval);
        btnWorkApproval.setOnClickListener(this);
//        btnReceiveThings=(Button) view.findViewById(R.id.btn_receive_things);
//        btnReceiveThings.setOnClickListener(this);
//        btnStartWork=(Button)view.findViewById(R.id.btn_start_work);
//        btnStartWork.setOnClickListener(this);
        llMessage=(LinearLayout)view.findViewById(R.id.ll_message);
        llMessage.setOnClickListener(this);
        llManageItem=(LinearLayout)view.findViewById(R.id.ll_manageItem);
        llManageItem.setOnClickListener(this);
//        btnMessage=(Button)view.findViewById(R.id.btn_message);
//        btnMessage.setOnClickListener(this);
        this.Tview=view;
//        initGV(view);
        return view;
    }


    private void initGV(View view)
    {
        list1=new ArrayList<ProjectItemBean>();
        list2=new ArrayList<ProjectItemBean>();
        list3=new ArrayList<ProjectItemBean>();
        //获得GridView实例
        gv1 = (MyGridView)view.findViewById(R.id.mygridview1);
        gv2 = (MyGridView)view.findViewById(R.id.mygridview2);
        gv3 = (MyGridView)view.findViewById(R.id.mygridview3);
        //准备要添加的数据条目
        RequestParams params = new RequestParams(Constants.getMyMenu);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(getActivity().getApplicationContext(),"user");
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
                            List<ProjectItemBean> list=new ArrayList<ProjectItemBean>();
                            Type type=new TypeToken<List<ProjectItemBean>>(){}.getType();
                            list=gson.fromJson(jsonObject.get("data").toString(), type);
//                            MessageDto messageDto = (MessageDto) gson.fromJson(jsonObject.toString(),MessageDto.class);
                            for(int i=0;i<list.size();i++)
                            {
                                if(list.get(i).getMenuCate()==1&&list.get(i).getIsIndex()==1) {
                                    list1.add(list.get(i));
                                }
                                else if(list.get(i).getMenuCate()==2&&list.get(i).getIsIndex()==1) {
                                    list2.add(list.get(i));
                                }
                                else if(list.get(i).getMenuCate()==3&&list.get(i).getIsIndex()==1) {
                                    list3.add(list.get(i));
                                }
                            }
                            if(list1.size()>0)
                            {
                                sortList(list1);
                            }
                            if(list2.size()>0)
                            {
                                sortList(list2);
                            }
                            if(list2.size()>0)
                            {
                                sortList(list2);
                            }


                            //实例化一个适配器
                            MyGridAdapter myGridAdapter1=new MyGridAdapter(getActivity(),R.layout.grid_item,list1);
                            //为GridView设置适配器
                            gv1.setAdapter(myGridAdapter1);
                            gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    clickBtn(list1,position);
                                }
                            });

                            //实例化一个适配器
                            MyGridAdapter myGridAdapter2=new MyGridAdapter(getActivity(),R.layout.grid_item,list2);
                            //为GridView设置适配器
                            gv2.setAdapter(myGridAdapter2);
                            gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    clickBtn(list2,position);
                                }
                            });

                            //实例化一个适配器
                            MyGridAdapter myGridAdapter3=new MyGridAdapter(getActivity(),R.layout.grid_item,list3);
                            //为GridView设置适配器
                            gv3.setAdapter(myGridAdapter3);
                            gv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    clickBtn(list3,position);
                                }
                            });
                            initNum(myGridAdapter1);
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
            }
        });
    }


    private void initNum(final MyGridAdapter myGridAdapter1 )
    {
        //准备要添加的数据条目
        RequestParams params = new RequestParams(Constants.GetIndexTip);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(getActivity().getApplicationContext(),"user");
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
                        Toast.makeText(getActivity(),jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        int workCount=jsonObject.getInt("workCount");
                        int taskCount=jsonObject.getInt("taskCount");
                        btnWorkApproval.setText((workCount+taskCount)+"");
                        if(list1.size()>0)
                        {
                            for(int i=0;i<list1.size();i++)
                            {
                                if(list1.get(i).getMenuType()==2)
                                {
                                    list1.get(i).setNum(taskCount);
                                }
                                if(list1.get(i).getMenuType()==6)
                                {
                                    list1.get(i).setNum(workCount);
                                }
                                myGridAdapter1.refresh(list1);
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
            }
        });
    }


    private void sortList(ArrayList<ProjectItemBean> list)
    {
        Collections.sort(list, new Comparator<ProjectItemBean>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(ProjectItemBean o1, ProjectItemBean o2) {
                if(o1.getMenuSort() > o2.getMenuSort()){
                    return 1;
                }
                if(o1.getMenuSort() == o2.getMenuSort()){
                    return 0;
                }
                return -1;
            }
        });
    }


    //work header图片轮播 strat
    private void rollPagerViewSet(View view) {
        rollPagerView= (RollPagerView) view.findViewById(R.id.rollViewpager);//获取对应控件
        rollPagerView.setPlayDelay(3000);//*播放间隔
        rollPagerView.setAnimationDurtion(500);//透明度
        rollPagerView.setAdapter(new rollViewpagerAdapter());//配置适配器

    }


    private class rollViewpagerAdapter extends StaticPagerAdapter {

        private int[] res={R.mipmap.timg1
                ,R.mipmap.timg2,
                R.mipmap.timg3,
                R.mipmap.timg4};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView imageView=new ImageView(container.getContext());
            imageView.setImageResource(res[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return imageView;
        }

        @Override
        public int getCount() {
            return res.length;
        }
    }


//工作的列表
    private void viewPagerSet(View view)
    {
//        viewPager=(ViewPager)view.findViewById(R.id.workViewPager);
        fragments=new Fragment[3];
        fragments[0]=new ButtonGroup1Fragment();
        fragments[1]=new ButtonGroup2Fragment();
        fragments[2]=new ButtonGroup3Fragment();

        mDots = new ArrayList<ImageView>();
//        ImageView dotFirst = (ImageView) view.findViewById(R.id.dot_first);
//        ImageView dotFSecond = (ImageView) view.findViewById(R.id.dot_second);
//        ImageView dotThrid = (ImageView) view.findViewById(R.id.dot_thrid);
//        mDots.add(dotFirst);
//        mDots.add(dotFSecond);
//        mDots.add(dotThrid);
        mDots.get(0).setImageResource(R.mipmap.dot_i);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDots.get(0).setImageResource(R.mipmap.dot_n);
                mDots.get(1).setImageResource(R.mipmap.dot_n);
                mDots.get(2).setImageResource(R.mipmap.dot_n);
                mDots.get(position).setImageResource(R.mipmap.dot_i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class  MyAdpater extends FragmentPagerAdapter
    {

        public MyAdpater(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hidden_project:
                startActivity(new Intent(getActivity(), HiddenProjectActivity.class));
                break;
            case R.id.btn_work_approval:
                startActivity(new Intent(getActivity(), ApprovalWorkActivity.class));
                break;
//            case R.id.btn_receive_things:
//                startActivity(new Intent(getActivity(), ReceiveThingsActivity.class));
//                break;
//            case R.id.btn_start_work:
//                startActivity(new Intent(getActivity(), StartWorkActivity.class));
//                break;
            case R.id.ll_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
//            case R.id.btn_message:
//                startActivity(new Intent(getActivity(), MessageActivity.class));
//                break;
            case R.id.ll_manageItem:
                startActivity(new Intent(getActivity(), ProjectItemActivity.class));
                break;



            default:
                break;
        }
    }

    private void clickBtn(ArrayList<ProjectItemBean> list,int position)
    {
        int a=list.get(position).getMenuType();
        if(5==a)//("公告通知".equals(str))
        {
            startActivity(new Intent(getActivity(), MessageActivity.class));
        }
        else if(6==a)//("待审批".equals(str))
        {
            startActivity(new Intent(getActivity(), ApprovalWorkActivity.class));
        }
        else if(2==a)//("我的任务".equals(str))
        {
            startActivity(new Intent(getActivity(), MyTaskActivity.class));
        }


        else if(10==a)//("台班".equals(str))
        {
            startActivity(new Intent(getActivity(), WorkTaibanActivity.class));
        }

        else if(11==a)//("点工".equals(str))
        {
            startActivity(new Intent(getActivity(), WorkYonggongActivity.class));
        }

        else if(12==a)//("用章".equals(str))
        {
            startActivity(new Intent(getActivity(), WorkYongzhangActivity.class));
        }
        else if(13==a)//("请假".equals(str))
        {
            startActivity(new Intent(getActivity(), WorkQingjiaActivity.class));
        }

        else if(0==a)//("领用".equals(str))
        {
            startActivity(new Intent(getActivity(), ReceiveThingsActivity.class));
        }

        else if(1==a)//("隐蔽工程".equals(str))
        {
            startActivity(new Intent(getActivity(), HiddenProjectActivity.class));
        }
        else if(4==a)//("任务督办".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(22==a)//("苗木".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }

        else if(23==a)//("土建".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }

        else if(3==a)//("工作发起".equals(str))
        {
            startActivity(new Intent(getActivity(), StartWorkActivity.class));
        }


        else if(15==a)//("我的工作".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(20==a)//("点工查询".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(21==a)//("点工台班".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(22==a)//("苗木明细".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(23==a)//("土建明细".equals(str))
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
        else if(14==a)//("联系人".equals(str))
        {
            startActivity(new Intent(getActivity(), ItemContactsActivity.class));
        }
        else
        {
            Toast.makeText(getActivity(),"暂未开放此功能",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        initGV(Tview);
        super.onResume();
    }
}
