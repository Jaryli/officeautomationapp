package com.app.officeautomationapp.adapter;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.activity.ApprovalReceiveActivity;
import com.app.officeautomationapp.activity.ReceiveThingsActivity;
import com.app.officeautomationapp.bean.ReceiveThingsCheckApplyPostBean;
import com.app.officeautomationapp.bean.ReceiveThingsCheckBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.app.officeautomationapp.view.MoreTextView;
import com.app.officeautomationapp.view.SpinnerDialog2;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/2/27.
 */
public class ApprovalReceiveThingsAdapter extends ArrayAdapter<ReceiveThingsCheckBean> {

    private int resourceId;

    private Context mContext;

    private ApprovalReceiveActivity activity;

    ProgressDialog progressDialog;

    public ApprovalReceiveThingsAdapter(Context context, int resource, List<ReceiveThingsCheckBean> objects,ApprovalReceiveActivity activity) {
        super(context, resource,objects);
        this.mContext=context;
        this.activity=activity;
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReceiveThingsCheckBean receiveThingsCheckBean=getItem(position);//获得实例
        View view;
        final ViewHolder viewHolder;//实例缓存
        if(convertView==null) {//布局缓存
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder=new ViewHolder();
            viewHolder.hTitle=(MoreTextView) view.findViewById(R.id.approval_title);
            viewHolder.hBtn=(LinearLayout) view.findViewById(R.id.approval_pic);
            viewHolder.hBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext, receiveThingsCheckBean.getProjectName(), Toast.LENGTH_LONG).show();
                    final SpinnerDialog2 spinnerDialog=new SpinnerDialog2(activity,R.style.DialogAnimations_SmileWindow);
                    spinnerDialog.bindOnSpinerListener(new SpinnerDialog2.OnDoneClick() {
                        @Override
                        public void onClick(int status, String reason) {
//                            Toast.makeText(mContext, reason+status+receiveThingsCheckBean.getProjectName(), Toast.LENGTH_LONG).show();
                            ReceiveThingsCheckApplyPostBean receiveThingsCheckApplyPostBean=new ReceiveThingsCheckApplyPostBean();
                            receiveThingsCheckApplyPostBean.setId(receiveThingsCheckBean.getId());
                            receiveThingsCheckApplyPostBean.setMsg(reason);
                            receiveThingsCheckApplyPostBean.setResultCode(status);
                            post(receiveThingsCheckApplyPostBean);
                        }
                    });
                    spinnerDialog.showSpinerDialog();

                }
            });
            view.setTag(viewHolder);
        }else
        {
            view=convertView;

            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.hTitle.setText(getText(receiveThingsCheckBean));
        return view;
    }


    private void post(ReceiveThingsCheckApplyPostBean receiveThingsCheckApplyPostBean)
    {
        Gson gson = new Gson();
        String result = gson.toJson(receiveThingsCheckApplyPostBean);
        progressDialog= new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("提交中...");
        progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
        progressDialog.show();
        RequestParams params = new RequestParams(Constants.HandleResApply);
        Log.i("", "post-url:" + Constants.HandleResApply);
        UserDto userDto= (UserDto) SharedPreferencesUtile.readObject(getContext().getApplicationContext(),"user");
        params.addHeader("access_token", userDto.getAccessToken());
        params.setBodyContent("'"+result+"'");
        Log.i("JAVA", "body:" + params.getBodyContent());
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    Toast.makeText(getContext(),jsonObject.get("msg").toString(),Toast.LENGTH_SHORT).show();
                    activity.refreshData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
                Toast.makeText(getContext(),"网络或服务器异常！",Toast.LENGTH_SHORT).show();
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
                progressDialog.hide();
            }
        });
    }

    private String getText(ReceiveThingsCheckBean receiveThingsCheckBean)
    {
        StringBuffer str=new StringBuffer();
        str.append("物品名称:"+receiveThingsCheckBean.getResName());
        str.append("\n");
        str.append("分配领用:"+receiveThingsCheckBean.getTypeInfo());
        str.append("\n");
        str.append("领用时间:"+receiveThingsCheckBean.getUseDate());
        str.append("\n");
        str.append("归还时间:"+receiveThingsCheckBean.getBackDate());
        str.append("\n");
        str.append("申请时间:"+receiveThingsCheckBean.getApplyDate());
        str.append("\n");
        str.append("关联工程:"+receiveThingsCheckBean.getProjectName());
        str.append("\n");
        str.append("申请数量:"+receiveThingsCheckBean.getNumInfo());
        str.append("\n");
        str.append("审批记录:"+receiveThingsCheckBean.getRemark());

        return str.toString();
    }

    private String getPicText(String str)
    {
        if(str.indexOf("请假")>-1)
        {
            return "假";
        }
        else if(str.indexOf("用章")>-1)
        {
            return "章";
        }
        else if(str.indexOf("用工")>-1)
        {
            return "工";
        }
        else
        {
            return str.substring(0,1);
        }
    }

    /**
     * 判断是否为今天
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsToday(String day) {
        try
        {
            Calendar pre = Calendar.getInstance();
            Date predate = new Date(System.currentTimeMillis());
            pre.setTime(predate);

            Calendar cal = Calendar.getInstance();
            Date date = null;
            try {
                date = getDateFormat().parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
            cal.setTime(date);

            if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
                int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                        - pre.get(Calendar.DAY_OF_YEAR);

                if (diffDay == 0) {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }
    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();
    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    //实例缓存
    class ViewHolder{
        MoreTextView hTitle;
        LinearLayout hBtn;
    }
}
