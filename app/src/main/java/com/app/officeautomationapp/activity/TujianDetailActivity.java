package com.app.officeautomationapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.TujianBean;
import com.app.officeautomationapp.bean.TujianDetailBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.SpinnerDialogTujianRefesh;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by pc on 2017/12/23.
 */

public class TujianDetailActivity extends BaseActivity implements  View.OnClickListener {
    ImageView back;
    EditText civilName;
    EditText civilUnits;
    EditText extend3;
    EditText aCNumInfo;
    EditText civilSpecification;
    EditText aCPrice;
    EditText refuseNum;
    EditText refuseReason;
    EditText moneyNum;
    EditText remark;
    LinearLayout pic;
    ImageView iv_img;

    TujianBean tujianBean;
    private UserDto userDto;
    TujianDetailBean tujianDetailBean;

    TextView tv_refresh;

    private ImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tujian_detail);
        Intent intent=getIntent();
        userDto= (UserDto) SharedPreferencesUtile.readObject(getApplicationContext(),"user");
        tujianBean = (TujianBean) intent.getSerializableExtra("data");
        initView();
        initData();
    }

    private void initView()
    {
        back=(ImageView) findViewById(R.id.iv_back);
        back.setOnClickListener(this);
        civilName=(EditText)findViewById(R.id.civilName);
        civilUnits=(EditText)findViewById(R.id.civilUnits);
        extend3=(EditText)findViewById(R.id.extend3);
        civilSpecification=(EditText)findViewById(R.id.civilSpecification);
        aCNumInfo=(EditText)findViewById(R.id.aCNumInfo);
        aCPrice=(EditText)findViewById(R.id.aCPrice);
        refuseNum=(EditText)findViewById(R.id.refuseNum);
        refuseReason=(EditText)findViewById(R.id.refuseReason);
        moneyNum=(EditText)findViewById(R.id.moneyNum);
        remark=(EditText)findViewById(R.id.remark);

        pic=(LinearLayout)findViewById(R.id.pic);
        iv_img=(ImageView) findViewById(R.id.iv_img);
        tv_refresh=(TextView) findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(this);
        options= new ImageOptions.Builder().setFadeIn(true) //淡入效果
                        //ImageOptions.Builder()的一些其他属性：
//                        .setCircular(true) //设置图片显示为圆形
                        .setSquare(true) //设置图片显示为正方形
                        //setCrop(true).setSize(200,200) //设置大小
                        //.setAnimation(animation) //设置动画
//        .setFailureDrawable(gifDrawable) //设置加载失败的动画
                        .setFailureDrawableId(R.mipmap.default_image) //以资源id设置加载失败的动画
//        .setLoadingDrawable(gifDrawable) //设置加载中的动画
                        .setLoadingDrawableId(R.mipmap.default_image) //以资源id设置加载中的动画
                        //.setIgnoreGif(false) //忽略Gif图片
                        //.setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                        //.setRaduis(int raduis) //设置拐角弧度
                        //.setUseMemCache(true) //设置使用MemCache，默认true
                        .build();
//        addImg("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=602540987,3822984667&fm=58&w=56&h=56&img.PNG");
//        addImg("https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=288137094,313559397&fm=85&s=C790E8235645D91B1650A4C90300E0B3");
    }
    private void initData()
    {
        RequestParams params = new RequestParams(Constants.GetCivilSingle);
        params.addQueryStringParameter("id",tujianBean.getId().toString());
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
                        Toast.makeText(TujianDetailActivity.this,jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        if(jsonObject.get("data")==""||jsonObject.get("data")==null)
                        {
                            Toast.makeText(TujianDetailActivity.this,"没有数据",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            Gson gson = new Gson();
                            Type type=new TypeToken<TujianDetailBean>(){}.getType();
                            tujianDetailBean=gson.fromJson(jsonObject.get("data").toString(), type);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
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
                Toast.makeText(TujianDetailActivity.this,"网络或服务器异常！",Toast.LENGTH_SHORT).show();
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //更新UI
            switch (msg.what) {
                case 1:
                    if(tujianDetailBean!=null) {

                        civilName.setText(tujianDetailBean.getCivilName());
                        civilUnits.setText(tujianDetailBean.getCivilUnits());
                        extend3.setText(tujianDetailBean.getExtend3());
                        aCNumInfo.setText(tujianDetailBean.getACNumInfo()+"");
                        civilSpecification.setText(tujianDetailBean.getCivilSpecification());
                        aCPrice.setText(tujianDetailBean.getACPrice());

                        refuseNum.setText(tujianDetailBean.getRefuseNum());
                        refuseReason.setText(tujianDetailBean.getRefuseReason());
                        moneyNum.setText(tujianDetailBean.getMoneyNum()+"");
                        remark.setText(tujianDetailBean.getRemark());
                        if(tujianDetailBean.getPhotoStr()!=null&&tujianDetailBean.getPhotoStr().length>0)
                        {
                            iv_img.setVisibility(View.GONE);
                            for(int i=0;i<tujianDetailBean.getPhotoStr().length;i++)
                            {
                                addImg(tujianDetailBean.getPhotoStr()[i]);
                            }
                        }
                    }
                    break;
            }
        }
    };


    private void addImg(String url){
        ImageView newImg = new ImageView(this);
        x.image().bind(newImg, url, options);
        //设置子控件在父容器中的位置布局，wrap_content,match_parent
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 也可以自己想要的宽度，参数（int width, int height）均表示px
        // 如dp单位，首先获取屏幕的分辨率在求出密度，根据屏幕ppi=160时，1px=1dp
        //则公式为 dp * ppi / 160 = px ——> dp * dendity = px
        //如设置为48dp：1、获取屏幕的分辨率 2、求出density 3、设置
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                (int)(48 * density),
                (int)(48 * density));

        //相当于android:layout_marginLeft="8dp"
        params1.leftMargin = 8;

        //addView(View child)，默认往已有的view后面添加，后插入，不设置布局params,默认wrap_content
//        contentLlayout.addView(newImg);

        //addView(View child, LayoutParams params)，往已有的view后面添加，后插入,并设置布局
        pic.addView(newImg,params1);

        //addView(View view,int index, LayoutParams params),在某个index处插入
//        contentLlayout.addView(newImg, 0, params1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.tv_refresh:
                SpinnerDialogTujianRefesh spinnerDialog=new SpinnerDialogTujianRefesh(TujianDetailActivity.this,R.style.DialogAnimations_SmileWindow,userDto,tujianBean.getId());
                spinnerDialog.showSpinerDialog();
                break;
            default:
                break;
        }

    }
}
