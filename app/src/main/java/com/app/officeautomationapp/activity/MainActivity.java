package com.app.officeautomationapp.activity;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.WorkerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.fragment.ContactFragment;
import com.app.officeautomationapp.fragment.MessageFragment;
import com.app.officeautomationapp.fragment.MyFragment;
import com.app.officeautomationapp.fragment.WorkFragment;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.yalantis.ucrop.ui.PictureSingeUCropActivity.SCALE;

public class MainActivity extends BaseActivity implements View.OnClickListener {

//    private Button buttonMessage;
    private Button buttonWork;
//    private Button buttonContact;
    private Button buttonMy;

    private RadioGroup radioGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installButton90to180();

        onload();
        //设置viewPager的适配器
        viewPager.setAdapter(new MyAdpater(getSupportFragmentManager()));

//        buttonMessage=(Button) findViewById(R.id.mainMessage);
//        buttonMessage.setOnClickListener(this);
        buttonWork=(Button)findViewById(R.id.mianWork);
        buttonWork.setOnClickListener(this);
//        buttonContact=(Button) findViewById(R.id.mainContact);
//        buttonContact.setOnClickListener(this);
        buttonMy=(Button)findViewById(R.id.mianMy);
        buttonMy.setOnClickListener(this);


    }

    //高斯模糊
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @WorkerThread
    private Bitmap getBlurBitmap(Context context, Bitmap inBitmap, float radius) {
        if (context == null || inBitmap == null) {
            throw new IllegalArgumentException("have not called setParams() before call execute()");
        }
        int width = Math.round(inBitmap.getWidth() * SCALE);
        int height = Math.round(inBitmap.getHeight() * SCALE);
        Bitmap in = Bitmap.createScaledBitmap(inBitmap, width, height, false);
        Bitmap out = Bitmap.createBitmap(in);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allocationIn = Allocation.createFromBitmap(rs, in);
        Allocation allocationOut = Allocation.createFromBitmap(rs, out);
        blurScript.setRadius(radius);
        blurScript.setInput(allocationIn);
        blurScript.forEach(allocationOut);
        allocationOut.copyTo(out);
        allocationIn.destroy();
        allocationOut.destroy();
        blurScript.destroy();
        rs.destroy();
        return out;
    }


    private void installButton90to180() {
        final AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable_30_150);
        final List<ButtonData> buttonDatas = new ArrayList<>();
//        int[] drawable = {R.drawable.plus, R.drawable.mark, R.drawable.settings, R.drawable.heart,R.drawable.mark};
        int[] drawable = {R.drawable.plus, R.mipmap.main_qingjia_2x, R.mipmap.main_yongzhang_2x, R.mipmap.main_diangong_2x,R.mipmap.main_taiban_2x};
        int[] color = {R.color.blue, R.color.green, R.color.orange, R.color.pink,R.color.red};
        for (int i = 0; i < 5; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 15);
            } else {
                buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            }
            buttonData.setBackgroundColorId(this, color[i]);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);
        setListener(button);
    }

    private void setListener(AllAngleExpandableButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
//                Toast.makeText(MainActivity.this,"clicked index:" + index,Toast.LENGTH_SHORT).show();
                switch (index) {
                    case 1://请假
                        Intent intent=new Intent(MainActivity.this,WorkQingjiaActivity.class);
                        startActivity(intent);
                        break;
                    case 2://用章
                        intent=new Intent(MainActivity.this,WorkYongzhangActivity.class);
                        startActivity(intent);
                        break;
                    case 3://点工
                        intent=new Intent(MainActivity.this,WorkYonggongActivity.class);
                        startActivity(intent);
                        break;
                    case 4://台班
                        intent=new Intent(MainActivity.this,WorkTaibanActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onExpand() {
//                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
//                showToast("onCollapse");
            }
        });
    }



    private ViewPager viewPager;
    private Fragment[] fragments;
    public void onload()
    {
        radioGroup= (RadioGroup) findViewById(R.id.mainMenu);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setOffscreenPageLimit(3);//缓存所有页面
//        fragments=new Fragment[4];
//        fragments[0]=new MessageFragment();
//        fragments[1]=new WorkFragment();
//        fragments[2]=new ContactFragment();
//        fragments[3]=new MyFragment();
        fragments=new Fragment[2];
        fragments[0]=new WorkFragment();
        fragments[1]=new MyFragment();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                radioGroup.check(radioGroup.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.mainMessage:
//                viewPager.setCurrentItem(0);
//                break;
            case R.id.mianWork:
                viewPager.setCurrentItem(0);
                break;
//            case R.id.mainContact:
//                viewPager.setCurrentItem(2);
//                break;
            case R.id.mianMy:
                viewPager.setCurrentItem(1);
                break;
            default:
                break;
        }

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

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();//退出应用
    }
}
