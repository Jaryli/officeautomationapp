package com.app.officeautomationapp.common;

import android.content.Context;
import android.util.Log;
import com.app.officeautomationapp.bean.UserInfoBean;
import com.app.officeautomationapp.dto.UserDto;
import com.app.officeautomationapp.util.SharedPreferencesUtile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

/**
 * Created by yu on 2017/9/10.
 */

public abstract class InitCommon {

    public static void initUserInfo(final Context context,UserDto userDto)
    {
        RequestParams params = new RequestParams(Constants.GetUserInfo);
        Log.i("initUserInfo", "post-url:" + Constants.GetUserInfo);
        params.addHeader("access_token", userDto.getAccessToken());

        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("JAVA", "onSuccess result:" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int re=jsonObject.getInt("result");
                    if(re!=1)
                    {
                        Log.i("initUserInfo", "error:" + jsonObject.get("msg").toString());
                        return;
                    }
                    else
                    {
                        Gson gson = new Gson();
                        UserInfoBean userInfoBean=new UserInfoBean();
                        Type type=new TypeToken<UserInfoBean>(){}.getType();
                        userInfoBean=gson.fromJson(jsonObject.get("data").toString(), type);
                        SharedPreferencesUtile.saveObject(context,"userInfo",userInfoBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("JAVA", "onError:" + ex);
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("JAVA", "onCancelled:" + cex);
            }
            @Override
            public void onFinished() {
                Log.i("JAVA", "onFinished:" + "");
            }
        });
    }
}
