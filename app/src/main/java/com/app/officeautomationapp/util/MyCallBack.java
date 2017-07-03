package com.app.officeautomationapp.util;

/**
 * Created by CS-711701-00027 on 2017/5/24.
 */
import org.xutils.common.Callback;

public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    @Override
    public void onSuccess(ResultType result) {
        //根据需求进行请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //根据需求进行请求网络失败的逻辑处理
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
