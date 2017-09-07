package com.app.officeautomationapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.officeautomationapp.R;
import com.app.officeautomationapp.bean.Attach;
import com.app.officeautomationapp.util.XDownloadUtil;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by yu on 2017/9/6.
 */

public class AttachActivity extends BaseActivity implements View.OnClickListener  {
    private ListView listView;
    private ImageView iv_attach_back;
    private List<Attach> attachs=new ArrayList<Attach>();
    private List<String> attachnames=new ArrayList<String>();
    private ArrayAdapter<String> adapter=null;
    /**
     * 进度条对话框
     */
    private ProgressDialog progressDialog;
    /**
     * 可取消的任务
     */
    private Callback.Cancelable cancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);
        initData();
        initProgressDialog();
        iv_attach_back=(ImageView)findViewById(R.id.iv_attach_back);
        iv_attach_back.setOnClickListener(this);
        listView=(ListView)findViewById(R.id.lv_attach);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,attachnames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File f=new File(XDownloadUtil.FILE_SDCARD_MADER+attachs.get(position).getAttachName());
                if(!f.exists())
                {
                    downloadFile(attachs.get(position).getAttachUrl(),attachs.get(position).getAttachName());
                }
                else
                {
                    //open file
                    openFile(XDownloadUtil.FILE_SDCARD_MADER+attachs.get(position).getAttachName());
                }


            }
        });

//        getIntent().getSerializableExtra("data");
    }

    private void downloadFile(String path, final String fileName)
    {
        Log.i("tag", "下载地址："+path);
        //设置请求参数
        RequestParams params = new RequestParams(path);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(XDownloadUtil.FILE_SDCARD_MADER+fileName);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        //下面的回调都是在主线程中运行的,这里设置的带进度的回调
        cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.i("tag", "取消"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.i("tag", "onError: 失败"+Thread.currentThread().getName());
                progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                Log.i("tag", "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(File arg0) {
                Log.i("tag", "下载成功的时候执行"+Thread.currentThread().getName());
                openFile(XDownloadUtil.FILE_SDCARD_MADER+fileName);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    progressDialog.setProgress((int) (current*100/total));
                    Log.i("tag", "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                }
            }

            @Override
            public void onStarted() {
                Log.i("tag", "开始下载的时候执行"+Thread.currentThread().getName());
                progressDialog.show();
            }

            @Override
            public void onWaiting() {
                Log.i("tag", "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
            }

        });
    }

    /*初始化对话框*/
    private void initProgressDialog() {
        //创建进度条对话框
        progressDialog = new ProgressDialog(this);
        //设置标题
        progressDialog.setTitle("下载文件");
        //设置信息
        progressDialog.setMessage("玩命下载中...");
        //设置显示的格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //不消失
        progressDialog.setCancelable(false);
        //设置按钮
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "暂停",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击取消正在下载的操作
                cancelable.cancel();
            }});
    }


    private void initData()
    {
        attachs=(List<Attach>)getIntent().getSerializableExtra("attachs");
//        Attach attach=new Attach();
//        attach.setAttachName("fujian1.jpg");
//        attach.setAttachType(".jpg");
//        attach.setAttachUrl("http://img1.50tu.com/meinv/xinggan/2013-11-16/e65e7cd83f37eed87067299266152807.jpg");
//        Attach attach2=new Attach();
//        attach2.setAttachName("fujian2.mp4");
//        attach2.setAttachType(".mp4");
//        attach2.setAttachUrl("http://mp4.22mtv.com:9090/mp48/19161-%E6%9D%A8%E4%B8%9E%E7%90%B3-%E5%BF%AB%E8%BD%AC[68mtv.com].mp4");
//        attachs.add(attach);
//        attachs.add(attach2);

        if(attachs.size()>0) {
            for (Attach aattach : attachs) {
                attachnames.add(aattach.getAttachName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_attach_back:
                this.finish();
                break;
            default:
                break;
        }
    }

    /**
     * 打开一个文件
     *
     * @param filePath
     *            文件的绝对路径
     */
    private void openFile(final String filePath)
    {
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        try
        {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String temp = ext.substring(1);
            String mime = mimeTypeMap.getMimeTypeFromExtension(temp);

            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            File file = new File(filePath);
            intent.setDataAndType(Uri.fromFile(file), mime);
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "无法打开后缀名为." + ext + "的文件！",
                    Toast.LENGTH_LONG).show();
        }
    }
}
