package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.DragAdapter;
import com.app.officeautomationapp.adapter.OtherAdapter;
import com.app.officeautomationapp.bean.ChannelItem;
import com.app.officeautomationapp.bean.MessageBean;
import com.app.officeautomationapp.bean.ProjectItemBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.DragGrid;
import com.app.officeautomationapp.view.OtherGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by CS-711701-00027 on 2017/5/27.
 */

public class ProjectItemActivity extends BaseActivity  implements AdapterView.OnItemClickListener,View.OnClickListener   {

    /** 用户栏目的GRIDVIEW */
    private DragGrid userGridView;
    /** 其它栏目的GRIDVIEW */
    private OtherGridView otherGridView;
    /** 用户栏目对应的适配器，可以拖动 */
    DragAdapter userAdapter;
    /** 其它栏目对应的适配器 */
    OtherAdapter otherAdapter;
    /** 其它栏目列表 */
    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    /** 用户栏目列表 */
    ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;

    ImageView ivItemBack;
    TextView tvItemAdd;
    private UserDto userDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_item);
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        initView();
        initData();
    }

    private ArrayList<ChannelItem> getData( String str)
    {
        ArrayList<ChannelItem> list =new ArrayList<ChannelItem>();
        for(int i=0;i<10;i++)
        {
            ChannelItem channelItem=new ChannelItem();
            channelItem.setId(i);
            channelItem.setOrderId(i);
            channelItem.setSelected(0);
            channelItem.setName(str+i);
            list.add(channelItem);
        }
        return list;
    }

    /** 初始化布局*/
    private void initView() {
        userGridView = (DragGrid) findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) findViewById(R.id.otherGridView);

        ivItemBack=(ImageView)findViewById(R.id.iv_item_back);
        ivItemBack.setOnClickListener(this);
        tvItemAdd=(TextView)findViewById(R.id.tv_item_add);
        tvItemAdd.setOnClickListener(this);

    }

    /** 初始化数据*/
    private void initData() {
        RequestParams params = new RequestParams(Constants.getMyMenu);
        params.addHeader("access_token", userDto.getAccessToken());
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    //int recordCount=jsonObject.getInt("recordCount");//总条数
                    if(re!=1)
                    {
                        Toast.makeText(ProjectItemActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(ProjectItemActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
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
                                if(list.get(i).getIsIndex()==1) {
                                    ChannelItem channelItem = new ChannelItem();
                                    channelItem.setId(list.get(i).getId());
                                    channelItem.setOrderId(list.get(i).getMenuSort());
                                    channelItem.setSelected(0);
                                    channelItem.setName(list.get(i).getMenuTitle());
                                    userChannelList.add(channelItem);
                                }
                                else
                                {
                                    ChannelItem channelItem = new ChannelItem();
                                    channelItem.setId(list.get(i).getId());
                                    channelItem.setOrderId(list.get(i).getMenuSort());
                                    channelItem.setSelected(0);
                                    channelItem.setName(list.get(i).getMenuTitle());
                                    otherChannelList.add(channelItem);
                                }
                            }
                            if(userChannelList.size()>0)
                            {
                                sortList(userChannelList);
                            }
                            if(otherChannelList.size()>0)
                            {
                                sortList(otherChannelList);
                            }
                            userAdapter = new DragAdapter(ProjectItemActivity.this, userChannelList);
                            userGridView.setAdapter(userAdapter);
                            otherAdapter = new OtherAdapter(ProjectItemActivity.this, otherChannelList);
                            otherGridView.setAdapter(ProjectItemActivity.this.otherAdapter);
                            //设置GRIDVIEW的ITEM的点击监听
                            otherGridView.setOnItemClickListener(ProjectItemActivity.this);
                            userGridView.setOnItemClickListener(ProjectItemActivity.this);

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
                Toast.makeText(ProjectItemActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    private void sortList(ArrayList<ChannelItem> list)
    {
        Collections.sort(list, new Comparator<ChannelItem>(){
            /*
             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             */
            public int compare(ChannelItem o1, ChannelItem o2) {
                if(o1.getOrderId() > o2.getOrderId()){
                    return 1;
                }
                if(o1.getOrderId() == o2.getOrderId()){
                    return 0;
                }
                return -1;
            }
        });
    }


    /** GRIDVIEW对应的ITEM点击监听接口  */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if(isMove){
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                //position为 0，1 的不可以进行任何操作//position != 0 && position != 1
                if (true) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        otherAdapter.setVisible(false);
                        //添加到最后一个
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                }else{
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /** 退出时候保存选择后数据库的设置  */
//    private void saveChannel() {
//        ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).deleteAllChannel();
//        ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
//        ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
//    }

//    @Override
//    public void onBackPressed() {
//        saveChannel();
//        super.onBackPressed();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_item_back:
                this.finish();
                break;
            case R.id.tv_item_add:
                //保存一下
                try {
                    saveData();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;

        }


    }

    private void saveData() throws UnsupportedEncodingException {
        List<ChannelItem> userList=userAdapter.getChannnelLst();
        List<ChannelItem> otherList=otherAdapter.getChannnelLst();
        String userStr="";
        String otherStr="";
        if(userList.size()>0)
        {
            for(ChannelItem channelItem:userList)
            {
                userStr+=channelItem.getId()+",";
            }
            userStr=userStr.substring(0,userStr.length()-1);
        }
        if(otherList.size()>0)
        {
            for(ChannelItem channelItem:otherList)
            {
                otherStr+=channelItem.getId()+",";
            }
            otherStr=otherStr.substring(0,otherStr.length()-1);
        }
        RequestParams params = new RequestParams(Constants.updateMenuIndex+"?indexIds="+userStr+"&notIndexIds="+otherStr);
        params.addHeader("access_token", userDto.getAccessToken());
//        params.addParameter("indexIds",userStr);
//        params.addParameter("notIndexIds",otherStr);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Toast.makeText(ProjectItemActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(ProjectItemActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        //刷新主页样式
//                        setResult(RESULT_OK,(new Intent()).setAction("1"));
                        ProjectItemActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("JAVA", "onError:" + ex);
                Toast.makeText(ProjectItemActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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
