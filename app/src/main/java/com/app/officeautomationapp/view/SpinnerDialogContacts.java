package com.app.officeautomationapp.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.officeautomationapp.R;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 */

public class SpinnerDialogContacts {

    Activity context;
    private TextView name;
    private TextView phone;
    private TextView s_phone;
    private TextView qq;

    AlertDialog alertDialog;
    int style;

    private String a_name;
    private String a_phone;
    private String a_s_phone;
    private String a_qq;


    public SpinnerDialogContacts(Activity activity) {
        this.context = activity;
    }

    public SpinnerDialogContacts(Activity activity, int style,String a_name,String a_phone,String a_s_phone,String a_qq) {

        this.context = activity;
        this.style=style;
        this.a_name=a_name;
        this.a_phone=a_phone;
        this.a_s_phone=a_s_phone;
        this.a_qq=a_qq;
    }

    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_layout_contacts, null);
        name=(TextView)v.findViewById(R.id.name);
        name.setText(a_name);
        phone=(TextView)v.findViewById(R.id.phone);
        if(a_phone.equals("")||a_phone==null)
        {
            phone.setText("手机:暂无");
        }
        else
        {
            phone.setText("手机:"+a_phone);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(a_phone);
                }
            });

        }

        s_phone=(TextView)v.findViewById(R.id.s_phone);
        if(a_s_phone.equals("")||a_s_phone==null)
        {
            s_phone.setText("短号:暂无");
        }
        else
        {
            s_phone.setText("短号:"+a_s_phone);
            s_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(a_s_phone);
                }
            });

        }

        qq=(TextView)v.findViewById(R.id.qq);
        if(a_qq.equals("")||a_qq==null)
        {
            qq.setText("QQ:暂无");
        }
        else
        {
            qq.setText("QQ:"+a_qq);
            qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(a_qq);
                    Toast.makeText(context,"已经复制!",Toast.LENGTH_SHORT).show();
                }
            });

        }

        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
//        alertDialog.setCancelable(false);

        alertDialog.show();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.7);    //宽度设置为屏幕的0.5
        alertDialog.getWindow().setAttributes(p);     //设置生效
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}