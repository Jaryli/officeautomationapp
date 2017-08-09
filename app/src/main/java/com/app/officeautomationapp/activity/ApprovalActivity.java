package com.app.officeautomationapp.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.adapter.ApprovalSelectAdapter;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.fragment.ApprovalFragment;
import com.app.officeautomationapp.fragment.MessageFragment;
import com.app.officeautomationapp.view.MyGridView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by yu on 2017/4/10.
 */
public class ApprovalActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout btnApprovalSearch;
    private RelativeLayout btnApprovalSelect;
    private Button btnApprovalCancel;

    private ImageView scrollbar;
    private RadioButton tvApprovalNo;
    private RadioButton tvApprovalHas;
    private EditText etApprovalSearch;
    private ImageView ivApprovalBack;
//    private LinearLayout loader;
    private ScrollView svApprovalSelect;
    private Button btnApprovalSelectDo;
    private MyGridView gvApprovalselect;


    private int width;//屏幕的宽度
    private RelativeLayout RL_InfoTip;
    private InputMethodManager imm;
    FragmentManager manager;


    private String[] text = {"全部","请假","报销","出差","外出","采购","请假","报销","出差","外出","采购","请假","报销","出差","外出","采购","请假","报销","出差","外出","采购"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        //弹出层
        RL_InfoTip=(RelativeLayout)findViewById(R.id.info_tip);

        btnApprovalSearch=(RelativeLayout)findViewById(R.id.btn_approval_search);
        btnApprovalSearch.setOnClickListener(this);
        btnApprovalSelect=(RelativeLayout)findViewById(R.id.btn_approval_select);
        btnApprovalSelect.setOnClickListener(this);
        btnApprovalCancel=(Button)findViewById(R.id.btn_approval_cancel);
        btnApprovalCancel.setOnClickListener(this);

        scrollbar=(ImageView)findViewById(R.id.scrollbar);

        tvApprovalNo=(RadioButton)findViewById(R.id.tv_approval_noApproval);
        tvApprovalNo.setOnClickListener(this);
        tvApprovalHas=(RadioButton)findViewById(R.id.tv_approval_hasApproval);
        tvApprovalHas.setOnClickListener(this);

        etApprovalSearch=(EditText)findViewById(R.id.et_approval_search);
        //键盘搜索事件
        etApprovalSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    RL_InfoTip.setVisibility(View.GONE);//底部tip设置不可见
                    imm.hideSoftInputFromWindow(etApprovalSearch.getWindowToken(), 0);//关闭软键盘
                    //搜索动作
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                }
                return true;

            }
        });

        ivApprovalBack=(ImageView)findViewById(R.id.iv_approval_back);
        ivApprovalBack.setOnClickListener(this);

//        loader=(LinearLayout)findViewById(R.id.loader);
        svApprovalSelect=(ScrollView) findViewById(R.id.sv_approval_select);
        btnApprovalSelectDo=(Button)findViewById(R.id.btn_approval_select_do);
        btnApprovalSelectDo.setOnClickListener(this);

        gvApprovalselect=(MyGridView)findViewById(R.id.gv_approval_select);
        final ApprovalSelectAdapter approvalSelectAdapter= new ApprovalSelectAdapter(this,text,0);
        gvApprovalselect.setAdapter(approvalSelectAdapter);
        gvApprovalselect.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("*********","************:"+i);
                approvalSelectAdapter.changeSelected(ApprovalActivity.this,i);
                approvalSelectAdapter.notifyDataSetChanged();
            }
        });

                //获取屏幕的宽度
        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;//获取屏幕的宽度

        imm = (InputMethodManager) etApprovalSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);//键盘


        //加入fragment
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.ll_approval_item, ApprovalFragment.newInstance(Constants.GetMyDoingWork,"","")).commit();

    }




    @Override
    public void onClick(View v) {
        ObjectAnimator valueAnimator;
        switch (v.getId()) {
            case R.id.btn_approval_search:
                RL_InfoTip.setVisibility(View.VISIBLE);//底部tip设置可见
                etApprovalSearch.setFocusable(true);
                etApprovalSearch.setFocusableInTouchMode(true);
                etApprovalSearch.requestFocus();

                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);//软键盘
                break;

            case R.id.btn_approval_select:
                svApprovalSelect.setVisibility(View.VISIBLE);//底部设置可见
//                svApprovalSelect.scrollTo(0,0);
                svApprovalSelect.smoothScrollTo(0,0);
                btnApprovalSelectDo.setVisibility(View.VISIBLE);//底部设置可见
                break;
            case R.id.btn_approval_select_do:
                svApprovalSelect.setVisibility(View.GONE);//底部设置不可见
                btnApprovalSelectDo.setVisibility(View.GONE);//底部设不可见
                //搜索
                break;

            case R.id.btn_approval_cancel:
                etApprovalSearch.clearFocus();
                RL_InfoTip.setVisibility(View.GONE);//底部tip设置不可见
                //etApprovalSearch.setInputType(InputType.TYPE_NULL); //关闭软键盘
                imm.hideSoftInputFromWindow(etApprovalSearch.getWindowToken(), 0);//关闭软键盘
                break;
            case R.id.tv_approval_noApproval:
                valueAnimator = ObjectAnimator.ofFloat(scrollbar, "translationX", 0);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                break;
            case R.id.tv_approval_hasApproval:
                valueAnimator = ObjectAnimator.ofFloat(scrollbar, "translationX", width/2);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                break;
            case R.id.iv_approval_back:
                this.finish();
                break;
            default:
                break;

        }
    }

//    /**
//     * 键盘搜索事件
//     * 无效，需要绑定输入框，这个仅仅是回车事件
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        Toast.makeText(this,"111",Toast.LENGTH_SHORT).show();
//        if(keyCode == KeyEvent.KEYCODE_ENTER) {
//            RL_InfoTip.setVisibility(View.GONE);//底部tip设置不可见
//            imm.hideSoftInputFromWindow(etApprovalSearch.getWindowToken(), 0);//关闭软键盘
//            //搜索动作
//            Message message = new Message();
//            message.what = 1;
//            mHandler.sendMessage(message);
//        }
//        return true;
//    }


    private void loadData()
    {
        manager.beginTransaction().add(R.id.ll_approval_item, ApprovalFragment.newInstance(Constants.GetMyDoingWork,etApprovalSearch.getText().toString(),"")).commit();
        manager.executePendingTransactions();
        etApprovalSearch.setText("");
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            //更新UI
            switch (msg.what)
            {
                case 1:
                    loadData();
                    break;
            }
        };
    };


}
