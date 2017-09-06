package com.app.officeautomationapp.util;

import android.os.Environment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by CS-711701-00027 on 2017/5/24.
 */

public abstract class XDownloadUtil {

    public static final String IMAGE_SDCARD_MADER = Environment
            .getExternalStorageDirectory()
            + "/oa/img/";

    public static final String FILE_SDCARD_MADER = Environment
            .getExternalStorageDirectory()
            + "/oa/file/";
    /**
     * 下载文件
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filename, Callback.CommonCallback<T> callback) {
        String filepath= IMAGE_SDCARD_MADER+filename;
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }
}
