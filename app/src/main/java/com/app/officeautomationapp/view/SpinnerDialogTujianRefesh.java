package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.MiaomuDetailPostBean;
import com.app.officeautomationapp.bean.TujianDetailPostBean;
import com.app.officeautomationapp.common.Constants;
import com.app.officeautomationapp.dto.UserDto;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialogTujianRefesh {

    Activity context;
    AlertDialog alertDialog;
    int style;
    UserDto userDto;
    ProgressDialog progressDialog;
    Integer id;



    public SpinnerDialogTujianRefesh(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialogTujianRefesh(Activity activity, int style, UserDto userDto, Integer id) {
        this.context = activity;
        this.style=style;
        this.userDto=userDto;
        this.id=id;
    }

    private boolean initValidate(EditText Quantity,final EditText Price)
    {
        if(Quantity.getText().toString().equals(""))
        {
            Toast.makeText(context,"请输入数量!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Price.getText().toString().equals(""))
        {
            Toast.makeText(context,"请输入价格!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_tujianrefesh, null);

        final EditText Quantity= (EditText) v.findViewById(R.id.Quantity);
        final EditText Price= (EditText) v.findViewById(R.id.Price);
        final EditText refuseNum = (EditText) v.findViewById(R.id.refuseNum);
        final EditText refuseReason = (EditText) v.findViewById(R.id.refuseReason);
        final EditText remark = (EditText) v.findViewById(R.id.remark);

        Button btn_post= (Button) v.findViewById(R.id.btn_post);
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!initValidate(Quantity,Price))
                {
                    return;
                }
                TujianDetailPostBean tujianDetailPostBean= new TujianDetailPostBean();
                tujianDetailPostBean.setId(id);
                tujianDetailPostBean.setQuantity(Quantity.getText().toString().equals("")?0:Integer.parseInt(Quantity.getText().toString()));
                tujianDetailPostBean.setPrice(Price.getText().toString().equals("")?0:Double.parseDouble(Price.getText().toString()));
                tujianDetailPostBean.setRefuseNum(refuseNum.getText().toString().equals("")?0:Double.parseDouble(refuseNum.getText().toString()));
                tujianDetailPostBean.setRefuseReason(refuseReason.getText().toString());
                tujianDetailPostBean.setRemark(remark.getText().toString());
                Gson gson = new Gson();
                String result = gson.toJson(tujianDetailPostBean);
                progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("提交中...");
                progressDialog.setCanceledOnTouchOutside(false);//对话框不消失
                progressDialog.show();
                RequestParams params = new RequestParams(Constants.UpdateCivilDetail);
                Log.i("", "post-url:" + Constants.UpdateCivilDetail);
                params.addHeader("access_token", userDto.getAccessToken());
                params.setBodyContent("'" + result + "'");
                Log.i("JAVA", "body:" + params.getBodyContent());
                Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("JAVA", "onSuccess result:" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int re = jsonObject.getInt("result");
                            if (re != 1) {
                                Toast.makeText(context, jsonObject.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(context,"修改成功！",Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                context.finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("JAVA", "onError:" + ex);
                        Toast.makeText(context, "网络或服务器异常！", Toast.LENGTH_SHORT).show();
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
                        progressDialog.dismiss();
                    }
                });




            }
        });

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

}